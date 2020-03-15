package com.engine.getcode.service;

import com.engine.getcode.dto.SearchDataDTO;
import com.engine.getcode.dto.SearchRequestDTO;
import com.engine.getcode.dto.SearchResultDataDTO;
import com.engine.getcode.dto.SuggestionDTO;
import com.engine.getcode.model.SearchData;
import com.engine.getcode.repository.SearchDataRepository;
import com.engine.getcode.utils.Utils;
import org.apache.commons.lang3.StringUtils;
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

    private static final String OFFSET = "offset";

    private static final String LIMIT = "limit";

    private static final String PAGE = "page";

    @Autowired
    private SearchDataRepository searchDataRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${search.result.result_per_page}")
    private int numberResultsPerPage;

    @Value("${search.result.count.random.enable}")
    private boolean isCountRandom;

    @Value("${search.result.count.random.min}")
    private int countRandomMin;

    @Value("${search.result.count.random.max}")
    private int countRandomMax;

    @Value("${search.result.count.max-page}")
    private int maxPage;

    @Value("${search.result.external_source_url}")
    private String externalSourceUrl;

    public SearchResultDataDTO search(SearchRequestDTO searchRequestDTO) throws ExecutionException, InterruptedException {
        LOGGER.debug("search:in(searchRequestDTO = {})", searchRequestDTO);

        // init
        SearchResultDataDTO searchResultDataDTO = new SearchResultDataDTO();

        // get request
        String keyword = searchRequestDTO.getKeyword();
        if (StringUtils.isEmpty(keyword)) return searchResultDataDTO;
        Integer currentPage = searchRequestDTO.getCurrentPage() == null ? 1 : searchRequestDTO.getCurrentPage();

        // get count of results
        long byKeywordCount = searchDataRepository.countByKeyword(keyword);
        long relateCount = searchDataRepository.countRelateData(keyword);
        long count = byKeywordCount + relateCount;

        long startTime = System.nanoTime();
        // get SearchData list from DB by keyword
        List<SearchData> searchDataList = findSearchData(keyword, currentPage);
        // generate SearchDataDTO list
        List<SearchDataDTO> searchDataDTOList = generateSearchDataDTOList(searchDataList);

        // get SearchData list from DB by keyword
        List<SearchData> relateSearchDataList = findRelateSearchData(keyword, currentPage, (int) byKeywordCount);
        // generate SearchDataDTO list
        List<SearchDataDTO> relateSearchDataDTOList = generateSearchDataDTOList(relateSearchDataList);
        searchDataDTOList.addAll(relateSearchDataDTOList);

        // get SearchData from external search engine
        List<Map<String, Object>> paramList = getExternalRequestParams(currentPage, (int) count);
        List<SearchDataDTO> externalSearchDataDTOList = getExternalSource(keyword, paramList);
        searchDataDTOList.addAll(externalSearchDataDTOList);

        long endTime = System.nanoTime();

        // generate suggestion list
        List<SuggestionDTO> suggestionDTOList = generateSuggestionList(keyword);

        // set result output data
//        searchResultDataDTO.setCount(count);
        searchResultDataDTO.setCount(maxPage * numberResultsPerPage);
        searchResultDataDTO.setCountFake(getCountFake(count));
        searchResultDataDTO.setTotalTime((endTime - startTime) / 1000000);
        searchResultDataDTO.setSearchDataList(searchDataDTOList);
        searchResultDataDTO.setSuggestionList(suggestionDTOList);
        searchResultDataDTO.setCurrentPage(currentPage);
        searchResultDataDTO.setNumberResultsPerPage(numberResultsPerPage);

        LOGGER.debug("search:out(searchResultDataDTO.size = {})", searchResultDataDTO);

        return searchResultDataDTO;
    }

    private List<SearchData> findSearchData(String keyword, Integer currentPage) {

        Pageable pageable = PageRequest.of(currentPage - 1, numberResultsPerPage, Sort.by("order").ascending());

        return searchDataRepository.findByLikeKeyword(keyword, pageable);
    }

    private List<SearchData> findRelateSearchData(String keywordSearch, Integer currentPage, int byKeywordCount) {

        List <SearchData> relateSearchDataList = new ArrayList<>();

        int firstResult;
        int maxResults;
        int countInLastPage = byKeywordCount % numberResultsPerPage;
        int maxPage = byKeywordCount / numberResultsPerPage;
        maxPage = countInLastPage != 0 ? maxPage + 1 : maxPage;

        if (countInLastPage != 0 && currentPage == maxPage) {
            firstResult = 0;
            maxResults = numberResultsPerPage - countInLastPage;
        } else if (countInLastPage != 0 && currentPage > maxPage) {
            firstResult = (numberResultsPerPage - countInLastPage) + (numberResultsPerPage * (currentPage - maxPage - 1));
            maxResults = numberResultsPerPage;
        } else {
            firstResult = numberResultsPerPage * (currentPage - maxPage - 1);
            maxResults = numberResultsPerPage;
        }

        if (currentPage > maxPage || (currentPage == maxPage && countInLastPage != 0)) {
            relateSearchDataList = searchDataRepository.findRelateData(keywordSearch, firstResult, maxResults);
        }

        return relateSearchDataList;
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
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
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

        List<String> relateKeywordList = searchDataRepository.findRelateKeyword(keywordSearch);
        for (String relateKeyword : relateKeywordList) {
            suggestionDTO = new SuggestionDTO();

            relateKeywordBold = Utils.boldTextByKeyword(keywordSearch, relateKeyword);
            suggestionDTO.setSuggestionKeyword(relateKeywordBold);
            suggestionDTO.setPath("/search?k=" + relateKeyword);

            suggestionDTOList.add(suggestionDTO);
        }

        return suggestionDTOList;
    }

    private List<Map<String, Object>> getExternalRequestParams(int currentPage, int count) {

        List<Map<String, Object>> mapList = new LinkedList<>();

        int extraPages = currentPage - count / numberResultsPerPage;
        int countInLastPage = count % numberResultsPerPage;
        int countExtraInLastPage = numberResultsPerPage - countInLastPage;

        int page;
        int offset;
        int limit;
        if (countInLastPage != 0 || count == 0) {
            if (extraPages == 1) {
                page = 1;
                offset = 0;
                limit = countExtraInLastPage;
                mapList.add(setParamMap(page, offset, limit));
            } else if (extraPages > 1) {
                page = extraPages - 1;
                offset = countExtraInLastPage;
                limit = offset + countInLastPage;
                mapList.add(setParamMap(page, offset, limit));

                page = extraPages;
                offset = 0;
                limit = countExtraInLastPage;
                mapList.add(setParamMap(page, offset, limit));
            }
        }

        return mapList;
    }

    private Map<String, Object> setParamMap(int page, int offset, int limit) {
        Map<String, Object> map = new HashMap<>();

        map.put(PAGE, page);
        map.put(OFFSET, offset);
        map.put(LIMIT, limit);

        return map;
    }

    private List<SearchDataDTO> getExternalSource(String keyword, List<Map<String, Object>> paramList) {
        List<SearchDataDTO> externalSearchDataList = new ArrayList<>();

        if (!paramList.isEmpty()) {

            SearchDataDTO externalSearchData;
            for (Map<String, Object> param : paramList) {

                String page = String.valueOf(param.get(PAGE));
                int offset = (int) param.get(OFFSET);
                int limit = (int) param.get(LIMIT);
                String url = externalSourceUrl.concat(keyword).concat("&p=").concat(page);
                try {
                    Connection connection = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                            .ignoreHttpErrors(true)
                            .followRedirects(true)
                            .timeout(10000);

                    Connection.Response response = connection.execute();

                    if (response.statusCode() == 200) {
                        Document document = connection.get();

                        // get url title
                        Elements resultList = document.select(".search-result");

                        for (int i = offset; i < limit; i++) {
                            Element result = resultList.get(i);
                            externalSearchData = new SearchDataDTO();

                            String resultTitle = result.getElementsByTag("h3").first().html();
                            String resultUrl = result.getElementsByTag("a").first().attr("href");
                            String resultDescription = result.getElementsByTag("p").first().html();

                            externalSearchData.setTitle(resultTitle);
                            externalSearchData.setUrl(resultUrl);
                            externalSearchData.setDescription(resultDescription);

                            externalSearchDataList.add(externalSearchData);
                        }
                    } else {
                        LOGGER.error("ERROR: Cannot get external source with HTTP status = {}, URL = {}", response.statusCode(), url);
                    }
                } catch (Exception e) {
                    LOGGER.error("ERROR: Get external source with  HTTP URL = {}", url, e);
                }
            }
        }

        return externalSearchDataList;
    }
}
