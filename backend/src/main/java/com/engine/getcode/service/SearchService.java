package com.engine.getcode.service;

import com.engine.getcode.dto.*;
import com.engine.getcode.model.KeywordData;
import com.engine.getcode.model.SearchData;
import com.engine.getcode.model.SearchedKeyword;
import com.engine.getcode.repository.KeywordDataRepository;
import com.engine.getcode.repository.SearchDataRepository;
import com.engine.getcode.repository.SearchedKeywordRepository;
import com.engine.getcode.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by HaiND on 2/16/2020 4:11 PM.
 */
@Service
public class SearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchService.class);

    private static final String CONNECT = "content";

    @Autowired
    KeywordDataRepository keywordDataRepository;

    @Autowired
    private SearchDataRepository searchDataRepository;

    @Autowired
    private SearchedKeywordRepository searchedKeywordRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${search.result.result_per_page}")
    private int numberResultsPerPage;

    @Value("${search.result.max_page}")
    private int maxPage;

    @Value("${search.result.number_of_suggestion}")
    private int numberOfSuggestion;

    @Value("${search.result.count.random.enable}")
    private boolean isCountRandom;

    @Value("${search.result.count.random.min}")
    private int countRandomMin;

    @Value("${search.result.count.random.max}")
    private int countRandomMax;

    @Value("${search.result.default_title}")
    private String defaultTitle;

    @Value("${search.result.default_description}")
    private String defaultDescription;

    @Value("${search.result.external_source_url}")
    private String externalSourceUrl;

    @Value("${search.result.external_google_url}")
    private String googleUrl;

    @Value("${search.result.external_key}")
    private String externalKey;

    @Value("${search.result.external_cx}")
    private String externalCx;

    @Value("${search.user_agent}")
    private String userAgent;

    private int count;

    public SearchResultDataDTO search(SearchRequestDTO searchRequestDTO) throws ExecutionException, InterruptedException {
        LOGGER.debug("search:in(searchRequestDTO = {})", searchRequestDTO);

        // init
        SearchResultDataDTO searchResultDataDTO = new SearchResultDataDTO();

        // get request
        String keyword = searchRequestDTO.getKeyword();
        if (StringUtils.isEmpty(keyword)) return searchResultDataDTO;
        Integer currentPage = searchRequestDTO.getCurrentPage() == null ? 1 : searchRequestDTO.getCurrentPage();

        // get count of results
        count = searchDataRepository.countByKeyword(keyword);

        long startTime = System.nanoTime();

        KeywordData keywordData = keywordDataRepository.findByKeyword(keyword);

        // get SearchData list from DB by keyword
        List<SearchData> searchDataList = findSearchData(keyword, currentPage);
        // generate SearchDataDTO list
        List<SearchDataDTO> searchDataDTOList = generateSearchDataDTOList(searchDataList);

        // get SearchData from external search engine
//        List<Map<String, Object>> paramList = getExternalRequestParams(currentPage, (int) count);
        ExternalParameterDTO externalParameterDTO = getExternalRequestParams(currentPage, count);
        List<SearchDataDTO> externalSearchDataDTOList = getExternalResults(keyword, externalParameterDTO);
        searchDataDTOList.addAll(externalSearchDataDTOList);
        if (externalSearchDataDTOList.isEmpty()) {
            externalParameterDTO = getGoogleRequestParams(currentPage, count);
        }

        long endTime = System.nanoTime();

        // generate suggestion list
        List<SuggestionDTO> suggestionDTOList = generateSuggestionList(keyword);

        // set result output data
        String title = null;
        String description = null;
        if (keywordData != null) {
            title = keywordData.getTitle();
            description = keywordData.getDescription();
        }
        LocalDate localDate = LocalDate.now();
        String date = localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + localDate.getYear();
        if (StringUtils.isEmpty(title)) {
            title = MessageFormat.format(defaultTitle, keyword, date);
        }
        if (StringUtils.isEmpty(description)) {
            description = MessageFormat.format(defaultDescription, keyword, date);
        }

        // save searched keyword
        saveSearchedKeyword(keyword);

        searchResultDataDTO.setCount(setResponseCount());
        searchResultDataDTO.setCountFake(getCountFake(count));
        searchResultDataDTO.setTotalTime((endTime - startTime) / 1000000);
        searchResultDataDTO.setSearchDataList(searchDataDTOList);
        searchResultDataDTO.setSuggestionList(suggestionDTOList);
        searchResultDataDTO.setCurrentPage(currentPage);
        searchResultDataDTO.setNumberResultsPerPage(numberResultsPerPage);
        searchResultDataDTO.setTitle(title);
        searchResultDataDTO.setDescription(description);
        searchResultDataDTO.setExternalParam(externalParameterDTO);

        LOGGER.debug("search:out(searchResultDataDTO.size = {})", searchResultDataDTO);

        return searchResultDataDTO;
    }

    private List<SearchData> findSearchData(String keyword, Integer currentPage) {

        Pageable pageable = PageRequest.of(currentPage - 1, numberResultsPerPage, Sort.by("order").ascending());
        return searchDataRepository.findByKeyword(keyword, pageable);
    }

    private long getCountFake(long count) {

        if (isCountRandom) {
            return randomCount();
        }

        return count;
    }

    private List<SearchDataDTO> generateSearchDataDTOList(List<SearchData> searchDataList) throws ExecutionException, InterruptedException {

        // init
        List<SearchDataDTO> searchDataDTOList = new ArrayList<>();
        List<CompletableFuture<SearchDataDTO>> completableFutureList = new ArrayList<>();

        // loop through searchDataDTO then generate search data detail
        searchDataList.forEach(data -> searchDataDTOList.add(modelMapper.map(data, SearchDataDTO.class)));

        // loop through searchDataDTOList
        for (SearchDataDTO dto : searchDataDTOList) {
            // async get url information
            CompletableFuture<SearchDataDTO> completableFuture = CompletableFuture.supplyAsync(() -> getUrlInformation(dto));
            completableFutureList.add(completableFuture);
        }

        // return a new CompletableFuture that is completed when all of the given CompletableFutures complete
        CompletableFuture<Void> allFuture = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]));

        // callback to get value when all future completed
        CompletableFuture<List<SearchDataDTO>> allCompletableFuture = allFuture.thenApply(f ->
                completableFutureList.stream().map(CompletableFuture::join).collect(Collectors.toList())
        );

        // convert from allCompletableFuture to searchDataDTOList and return
        return allCompletableFuture.get();
    }

    private SearchDataDTO getUrlInformation(SearchDataDTO searchDataDTO) {
        LOGGER.debug("getUrlInformation:in(url = {})", searchDataDTO.getUrl());

        String url = searchDataDTO.getUrl();

        String title = StringUtils.EMPTY;
        String description = StringUtils.EMPTY;
        boolean isSuccess = true;

        try {
            Connection connection = Jsoup.connect(url)
                    .userAgent(userAgent)
                    .ignoreHttpErrors(true)
                    .followRedirects(true)
                    .timeout(10000);

            Connection.Response response = connection.execute();

            if (response.statusCode() == 200) {
                Document document = connection.get();

                // get url title
                Elements titleElements = document.select("meta[property=og:title]");
                if (titleElements != null && !titleElements.isEmpty()) {
                    title = titleElements.attr(CONNECT);
                } else {
                    title = document.title();
                }

                // get url description
                description = getPageDescription(document);
            } else {
                LOGGER.error("ERROR: Cannot get URL information with HTTP status = {}, URL = {}", response.statusCode(), url);
                isSuccess = false;
            }
        } catch (Exception e) {
            LOGGER.error("ERROR: Get URL information with  HTTP URL = {}", url, e);
            isSuccess = false;
        }

        if (isSuccess) {
            title = Utils.boldTextByKeyword(searchDataDTO.getKeyword(), title);
            description = Utils.boldTextByKeyword(searchDataDTO.getKeyword(), description);
            searchDataDTO.setTitle(title);
            searchDataDTO.setDescription(description);

            LOGGER.debug("getUrlInformation:out(url = {})", searchDataDTO.getUrl());
        } else {
            searchDataDTO = null;
        }

        return searchDataDTO;
    }

    private String getPageDescription(Document document) {
        String description = StringUtils.EMPTY;

        // get description by name
        Elements elements = document.select("meta[name=description]");
        for (Element element : elements) {
            description = element.attr(CONNECT);
            if (description != null) return description;
        }

        // get description by property
        elements = document.select("meta[property=description]");
        for (Element element : elements) {
            description = element.attr(CONNECT);
            if (description != null) return description;
        }

        return description;
    }

    private int randomCount() {
        Random random = new Random();
        return random.nextInt(countRandomMax - countRandomMin) + countRandomMin;
    }

    private List<SuggestionDTO> generateSuggestionList(String keywordSearch) {
        List<SuggestionDTO> suggestionDTOList = new ArrayList<>();
        SuggestionDTO suggestionDTO;
        String relateKeywordBold;

        Pageable pageable = PageRequest.of(0, numberOfSuggestion, Sort.by("searchCount").descending());
        List<String> relateKeywordList = searchedKeywordRepository.findListLikeKeyword(keywordSearch, pageable);

        relateKeywordList.remove(keywordSearch);
        if (relateKeywordList.size() < numberOfSuggestion) {
            int restCount = numberOfSuggestion - relateKeywordList.size();
            relateKeywordList.addAll(searchedKeywordRepository.findRelateKeyword(keywordSearch, restCount));
        }

        for (String relateKeyword : relateKeywordList) {
            suggestionDTO = new SuggestionDTO();

            relateKeywordBold = Utils.boldTextByKeyword(keywordSearch, relateKeyword);
            suggestionDTO.setSuggestionKeyword(relateKeywordBold);
            suggestionDTO.setPath("/search?k=" + relateKeyword);

            suggestionDTOList.add(suggestionDTO);
        }

        return suggestionDTOList;
    }

    private ExternalParameterDTO getGoogleRequestParams(int currentPage, int count) {
        ExternalParameterDTO externalParameterDTO = new ExternalParameterDTO();

        int extraPages = currentPage - count / numberResultsPerPage;
        int countInLastPage = count % numberResultsPerPage;
        int countExtraInLastPage = numberResultsPerPage - countInLastPage;

        int start = 0;
        int limit = 0;
        if (countInLastPage != 0 || count == 0) {
            if (extraPages == 1) {
                limit = countExtraInLastPage;
            } else if (extraPages > 1) {
                start = (extraPages - 2) * numberResultsPerPage + countExtraInLastPage;
                limit = numberResultsPerPage;
            }
        }

        externalParameterDTO.setgUrl(googleUrl);
        externalParameterDTO.setKey(externalKey);
        externalParameterDTO.setCx(externalCx);
        externalParameterDTO.setStart(start);
        externalParameterDTO.setLimit(limit);

        return externalParameterDTO;
    }

    private List<SearchDataDTO> getExternalSource(String keyword, ExternalParameterDTO param) {
        List<SearchDataDTO> externalSearchDataList = new ArrayList<>();

        if (param != null) {

            SearchDataDTO externalSearchData;

            int start = param.getStart();
            int limit = param.getLimit();
            String url = "https://www.google.com/search?q=".concat(keyword).concat("&oq=").concat(keyword).concat("&start=" + start);
            LOGGER.debug("Get google results with URL = {}", url);
            try {
                Connection connection = Jsoup.connect(url)
                        .userAgent(userAgent)
                        .ignoreHttpErrors(true)
                        .followRedirects(true)
                        .timeout(20000);

                Connection.Response response = connection.execute();

                if (response.statusCode() == 200) {
                    Document document = connection.get();

                    // get url title
                    Elements resultList = document.select(".rc");

                    for (int i = 0; i < limit && i < resultList.size(); i++) {
                        Element result = resultList.get(i);
                        externalSearchData = new SearchDataDTO();

                        String resultTitle = result.getElementsByTag("h3").first().html();
                        String resultUrl = result.getElementsByTag("a").first().attr("href");
                        String resultDescription = result.getElementsByClass("st").first().html();

                        externalSearchData.setTitle(resultTitle);
                        externalSearchData.setUrl(resultUrl);
                        externalSearchData.setDescription(resultDescription);

                        externalSearchDataList.add(externalSearchData);
                    }

                    if (!resultList.isEmpty()) {
                        if (limit - 10 > 0) {
                            param.setStart(start + 10);
                            param.setLimit(limit - 10);
                            externalSearchDataList.addAll(getExternalSource(keyword, param));
                        }
                    }
                } else {
                    LOGGER.error("ERROR: Cannot get external source with HTTP status = {}, URL = {}", response.statusCode(), url);
                }
            } catch (Exception e) {
                LOGGER.error("ERROR: Get external source with  HTTP URL = {}", url, e);
            }
        }

        return externalSearchDataList;
    }

    private ExternalParameterDTO getExternalRequestParams(int currentPage, int count) {

        ExternalParameterDTO param = new ExternalParameterDTO();

        int extraPages = currentPage - count / numberResultsPerPage;
        int countInLastPage = count % numberResultsPerPage;
        int countExtraInLastPage = numberResultsPerPage - countInLastPage;

        int page = 1;
        int start = 0;
        int limit = 0;
        if (countInLastPage != 0 || count == 0 || extraPages > 0) {
            if (extraPages == 1) {
                limit = countExtraInLastPage;
            } else if (extraPages > 1) {
                int tmp = (extraPages - 2) * numberResultsPerPage + countExtraInLastPage;
                page = tmp / 10 + 1;
                start = tmp % 10;
                limit = numberResultsPerPage;
            }
        }
        param.setPage(page);
        param.setStart(start);
        param.setLimit(limit);

        return param;
    }

    private List<SearchDataDTO> getExternalResults(String keyword, ExternalParameterDTO param) {
        List<SearchDataDTO> externalSearchDataList = new ArrayList<>();

        if (param.getLimit() > 0) {

            int page = param.getPage();
            String url = externalSourceUrl.concat(keyword).concat("&page=").concat(String.valueOf(page));
            LOGGER.debug("Get external results with URL = {}", url);

            try {
                Connection connection = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                        .ignoreHttpErrors(true)
                        .followRedirects(true)
                        .timeout(20000);

                Connection.Response response = connection.execute();

                if (response.statusCode() == 200) {
                    Document document = connection.get();

                    String scriptResponse = document.getElementById("composerResponse").data();
                    String jsonResponse = scriptResponse.substring(scriptResponse.indexOf('=') + 1, scriptResponse.length() - 1);
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("search")) {
                        JSONObject search = jsonObject.getJSONObject("search");
                        count = count + search.getInt("real_size");

                        JSONArray searchResults = search.getJSONArray("search_results");

                        SearchDataDTO externalSearchData;
                        String resultTitle;
                        String resultUrl;
                        String resultDescription;

                        int start = param.getStart();
                        int limit = param.getLimit() - start > 10 ? start + 10 : param.getLimit();

                        int elementCount = 0;
                        for (int i = start; i < searchResults.length(); i++) {
                            JSONObject result = (JSONObject) searchResults.get(i);
                            String type = result.getString("type");
                            if ("search".equals(type)) {
                                resultTitle = result.getString("title");
                                resultUrl = result.getString("url");
                                resultDescription = result.getString("content");
                            } else if ("news".equals(type)) {
                                JSONObject article = (JSONObject) result.getJSONArray("articles").get(0);
                                resultTitle = article.getString("title");
                                resultUrl = article.getString("url");
                                resultDescription = article.getString("summary");
                            } else {
                                continue;
                            }

                            externalSearchData = new SearchDataDTO();
                            externalSearchData.setTitle(resultTitle);
                            externalSearchData.setUrl(resultUrl);
                            externalSearchData.setDescription(resultDescription);

                            externalSearchDataList.add(externalSearchData);

                            if (elementCount == limit) break;
                        }

                        if (param.getLimit() - start > 10) {
                            param.setPage(page + 1);
                            param.setStart(0);
                            param.setLimit(param.getLimit() - externalSearchDataList.size());
                        }
                    }
                } else {
                    LOGGER.error("ERROR: Cannot get external source with HTTP status = {}, URL = {}", response.statusCode(), url);
                }
            } catch (Exception e) {
                LOGGER.error("ERROR: Get external source with  HTTP URL = {}", url, e);
            }
        }

        return externalSearchDataList;
    }

    private int setResponseCount() {

        if (count > maxPage * numberResultsPerPage) {
            return maxPage * numberResultsPerPage;
        } else {
            return count;
        }
    }

    private void saveSearchedKeyword(String keyword) {

        SearchedKeyword searchedKeyword = searchedKeywordRepository.findByKeyword(keyword);

        if (searchedKeyword == null) {
            searchedKeyword = new SearchedKeyword();
            searchedKeyword.setSearchCount(BigInteger.ONE);
            searchedKeyword.setKeyword(keyword);
            searchedKeyword.setInitDate(new Date());
        } else {
            searchedKeyword.setSearchCount(searchedKeyword.getSearchCount().add(BigInteger.ONE));
        }

        searchedKeyword.setLastDate(new Date());
        searchedKeywordRepository.save(searchedKeyword);
    }
}
