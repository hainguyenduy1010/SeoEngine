package com.engine.seo.controller;

import com.engine.seo.dto.SearchResultDataDTO;
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
    public ResponseEntity<SearchResultDataDTO> search(@Valid @RequestBody String keyword) {
        LOGGER.info("POST api/search");
        LOGGER.info("POST with body = {}", keyword);

        try {
            SearchResultDataDTO searchResultDataDTO = searchService.search(keyword);

            return ResponseEntity.ok(searchResultDataDTO);
        } catch (Exception e) {
            LOGGER.debug("ERROR: Search", e);
            throw e;
        }
    }
}
