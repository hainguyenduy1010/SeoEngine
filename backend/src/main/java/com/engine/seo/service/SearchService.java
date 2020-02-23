package com.engine.seo.service;

import com.engine.seo.dto.SearchDataDTO;
import com.engine.seo.dto.SearchResultDataDTO;
import com.engine.seo.dto.SuggestionDTO;
import com.engine.seo.model.SearchData;
import com.engine.seo.repository.SearchDataRepository;
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
    SearchDataRepository searchDataRepository;

    @Autowired
    ModelMapper modelMapper;

    @Value("${search.result.count.random.enable}")
    private boolean isCountRandom;

    @Value("${search.result.count.random.min}")
    private int countRandomMin;

    @Value("${search.result.count.random.max}")
    private int countRandomMax;

    public SearchResultDataDTO search(String keyword) throws ExecutionException, InterruptedException {
        LOGGER.debug("search:in(keyword = {})", keyword);

        // init
        SearchResultDataDTO searchResultDataDTO = new SearchResultDataDTO();

        long startTime = System.nanoTime();
        // get SearchData list from DB by keyword
        List<SearchData> searchDataList = searchDataRepository.findByKeywordOrderBySortkeyAsc(keyword);
        // generate SearchDataDTO list
        List<SearchDataDTO> searchDataDTOList = generateSearchDataDTOList(searchDataList);
        long endTime = System.nanoTime();

        // get count of results
        int count;
        if (isCountRandom) {
            count = randomCount();
        } else {
            count = searchDataDTOList.size();
        }

        // generate suggestion list
        List<SuggestionDTO> suggestionDTOList = generateSuggestionList(keyword);

        // set result output data
        searchResultDataDTO.setCount(count);
        searchResultDataDTO.setTotalTime((endTime - startTime) / 1000000);
        searchResultDataDTO.setSearchDataList(searchDataDTOList);
        searchResultDataDTO.setSuggestionList(suggestionDTOList);

        LOGGER.debug("search:out(searchResultDataDTO.size = {})", searchResultDataDTO);

        return searchResultDataDTO;
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
        System.out.println("================START================");
        String url = searchDataDTO.getUrl();

        String title = StringUtils.EMPTY;
        String description = StringUtils.EMPTY;

        try {
            Connection connection = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .ignoreHttpErrors(true)
                    .followRedirects(true)
                    .timeout(20000);

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

            }
        } catch (IOException e) {
            LOGGER.error("ERROR: Get URL information with  HTTP URL = {}", url, e);
        }

        searchDataDTO.setTitle(title);
        searchDataDTO.setDescription(description);
        System.out.println("================END================");

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

        List<String> relateKeywordList = searchDataRepository.findRelateByKeyword(keywordSearch);
        for (String relateKeyword : relateKeywordList) {
            suggestionDTO = new SuggestionDTO();
            suggestionDTO.setKeyword(relateKeyword);
            suggestionDTOList.add(suggestionDTO);
        }

        return suggestionDTOList;
    }
}
