package com.engine.seo.controller;

import com.engine.seo.dto.SearchDataDTO;
import com.engine.seo.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by HaiND on 2/16/2020 4:11 PM.
 */
@RestController
@RequestMapping("/api")
public class SearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;

    @PostMapping("/search")
    public ResponseEntity<List<SearchDataDTO>> search(@Valid @RequestBody String keyword) {
        LOGGER.info("POST api/search");
        LOGGER.info("POST with body = {}", keyword);

        try {
            List<SearchDataDTO> searchDataList = searchService.search(keyword);

            return ResponseEntity.ok(searchDataList);
        } catch (Exception e) {
            LOGGER.debug("ERROR: Search", e);
            throw e;
        }
    }
}
