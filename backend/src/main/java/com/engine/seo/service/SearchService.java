package com.engine.seo.service;

import com.engine.seo.dto.SearchDataDTO;
import com.engine.seo.dto.SearchRequestDTO;
import com.engine.seo.dto.SearchResultDataDTO;
import com.engine.seo.dto.SuggestionDTO;
import com.engine.seo.model.SearchData;
import com.engine.seo.repository.SearchDataRepository;
import com.engine.seo.utils.Utils;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    public SearchResultDataDTO search(SearchRequestDTO searchRequestDTO) throws ExecutionException, InterruptedException {
        LOGGER.debug("search:in(searchRequestDTO = {})", searchRequestDTO);

        // init
        SearchResultDataDTO searchResultDataDTO = new SearchResultDataDTO();

        // get request
        String keyword = searchRequestDTO.getKeyword();
        Integer currentPage = searchRequestDTO.getCurrentPage();

        // get count of results
        long byKeywordCount = searchDataRepository.countByKeyword(keyword);
        long relateCount = searchDataRepository.countRelateData(keyword);

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
        long endTime = System.nanoTime();

        // generate suggestion list
        List<SuggestionDTO> suggestionDTOList = generateSuggestionList(keyword);

        // set result output data
        searchResultDataDTO.setCount(byKeywordCount + relateCount);
        searchResultDataDTO.setCountFake(getCountFake(byKeywordCount + relateCount));
        searchResultDataDTO.setTotalTime((endTime - startTime) / 1000000);
        searchResultDataDTO.setSearchDataList(searchDataDTOList);
        searchResultDataDTO.setSuggestionList(suggestionDTOList);
        searchResultDataDTO.setCurrentPage(currentPage);
        searchResultDataDTO.setNumberResultsPerPage(numberResultsPerPage);

        LOGGER.debug("search:out(searchResultDataDTO.size = {})", searchResultDataDTO);

        return searchResultDataDTO;
    }

    private List<SearchData> findSearchData(String keyword, Integer currentPage) {
        int page = currentPage == null ? 0 : currentPage - 1;
        Pageable pageable = PageRequest.of(page, numberResultsPerPage, Sort.by("sortkey").ascending());

        return searchDataRepository.findByKeyword(keyword, pageable);
    }

    private List<SearchData> findRelateSearchData(String keywordSearch, Integer currentPage, int byKeywordCount) {

        List <SearchData> relateSearchDataList = new ArrayList<>();
        currentPage = currentPage == null ? 1: currentPage;

        int firstResult, maxResults;
        int countInLastPage = byKeywordCount % numberResultsPerPage;
        int maxPage = byKeywordCount / numberResultsPerPage;
        maxPage = countInLastPage != 0 ? maxPage + 1 : maxPage;

        if (countInLastPage != 0) {
            firstResult = 0;
            maxResults = numberResultsPerPage - countInLastPage;
        } else {
            firstResult = numberResultsPerPage * (currentPage - maxPage - 1);
            maxResults = numberResultsPerPage + firstResult;
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
        } catch (IOException e) {
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
}
