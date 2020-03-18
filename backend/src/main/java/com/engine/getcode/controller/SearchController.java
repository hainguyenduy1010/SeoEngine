package com.engine.getcode.controller;

import com.engine.getcode.dto.SearchRequestDTO;
import com.engine.getcode.dto.SearchResultDataDTO;
import com.engine.getcode.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

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
    public ResponseEntity<SearchResultDataDTO> search(@Valid @RequestBody SearchRequestDTO searchRequestDTO) throws ExecutionException, InterruptedException {
        LOGGER.info("POST api/search");
        LOGGER.info("POST with body = {}", searchRequestDTO);

        try {
            SearchResultDataDTO searchResultDataDTO = searchService.search(searchRequestDTO);

            return ResponseEntity.ok(searchResultDataDTO);
        } catch (Exception e) {
            LOGGER.debug("ERROR: Search", e);
            throw e;
        }
    }
}
