package com.engine.seo.service;

import com.engine.seo.dto.SearchDataDTO;
import com.engine.seo.dto.SearchResultDataDTO;
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

/**
 * Created by HaiND on 2/16/2020 4:11 PM.
 */
@Service
public class SearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchService.class);

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

    public SearchResultDataDTO search(String keyword) {
        LOGGER.debug("search:in(keyword = {})", keyword);

        // init
        SearchResultDataDTO searchResultDataDTO = new SearchResultDataDTO();

        long startTime = System.nanoTime();
        // get SearchData list from DB by keyword
        List<SearchData> searchDataList = searchDataRepository.findByKeyword(keyword);
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

        // set result output data
        searchResultDataDTO.setCount(count);
        searchResultDataDTO.setTotalTime((endTime - startTime) / 1000000);
        searchResultDataDTO.setSearchDataList(searchDataDTOList);

        LOGGER.debug("search:out(searchResultDataDTO.size = {})", searchResultDataDTO);

        return searchResultDataDTO;
    }

    private List<SearchDataDTO> generateSearchDataDTOList(List<SearchData> searchDataList) {
        List<SearchDataDTO> searchDataDTOList = new ArrayList<>();

        SearchDataDTO searchDataDTO;
        // loop through searchDataDTO then generate search data detail
        for (SearchData searchData : searchDataList) {
            searchDataDTO = modelMapper.map(searchData, SearchDataDTO.class);
            handleUrlInformation(searchDataDTO);
            searchDataDTOList.add(searchDataDTO);
        }

        return searchDataDTOList;
    }

    private void handleUrlInformation(SearchDataDTO searchDataDTO) {
        String url = searchDataDTO.getUrl();

        String title = StringUtils.EMPTY;
        String description = StringUtils.EMPTY;

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
                if (titleElements != null && titleElements.size() != 0) {
                    title = titleElements.attr("content");
                } else {
                    title = document.title();
                }

                // get url description
                description = getPageDescription(document);
            } else if (response.statusCode() == 403) {
                System.out.println("403");
            }
        } catch (IOException e) {
            LOGGER.error("ERROR: Get URL information", e);
        }

        searchDataDTO.setTitle(title);
        searchDataDTO.setDescription(description);
    }

    private String getPageDescription(Document document) {
        String description = StringUtils.EMPTY;

        // get description by name
        Elements elements = document.select("meta[name=description]");
        for (Element element : elements) {
            description = element.attr("content");
            if (description != null) return description;
        }

        // get description by property
        elements = document.select("meta[property=description]");
        for (Element element : elements) {
            description = element.attr("content");
            if (description != null) return description;
        }

        return description;
    }

    private int randomCount() {
        Random random = new Random();
        return random.nextInt(countRandomMax - countRandomMin) + countRandomMin;
    }
}
