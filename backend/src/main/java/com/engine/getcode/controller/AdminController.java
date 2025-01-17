package com.engine.getcode.controller;

import com.engine.getcode.dto.*;
import com.engine.getcode.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by HaiND on 2/18/2020 12:19 AM.
 */
@RestController
@RequestMapping("/api")
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO) {
        LOGGER.info("POST api/login");
        LOGGER.info("POST with body = {}", loginDTO);

        try {
            if (adminService.login(loginDTO)) {
                return ResponseEntity.status(HttpStatus.OK).body("Login successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Login failed!");
            }
        } catch (Exception e) {
            LOGGER.debug("ERROR: Login", e);
            throw e;
        }
    }

    @PostMapping("/count")
    public ResponseEntity<Long> getCount(@Valid @RequestBody AdminDataListRequestDTO adminDataListRequestDTO) {
        LOGGER.info("GET api/count");

        try {
            Long count = adminService.getCount(adminDataListRequestDTO);

            return ResponseEntity.ok(count);
        } catch (Exception e) {
            LOGGER.debug("ERROR: Get count", e);
            throw e;
        }
    }

    @PostMapping("/list/keyword-data")
    public ResponseEntity<List<KeywordDataDTO>> getKeywordDataList(@Valid @RequestBody AdminDataListRequestDTO adminDataListRequestDTO) {
        LOGGER.info("POST api/list/keyword-data");
        LOGGER.info("POST with body = {}", adminDataListRequestDTO);

        try {
            List<KeywordDataDTO> keywordDataDTOList = adminService.getKeywordDataList(adminDataListRequestDTO);

            return ResponseEntity.ok(keywordDataDTOList);
        } catch (Exception e) {
            LOGGER.debug("ERROR: Get keyword data list", e);
            throw e;
        }
    }

    @PostMapping("/list/search-data")
    public ResponseEntity<List<SearchDataDTO>> getSearchDataList(@Valid @RequestBody AdminDataListRequestDTO adminDataListRequestDTO) {
        LOGGER.info("POST api/list/search-data");
        LOGGER.info("POST with body = {}", adminDataListRequestDTO);

        try {
            List<SearchDataDTO> searchDataDTOList = adminService.getSearchDataList(adminDataListRequestDTO);

            return ResponseEntity.ok(searchDataDTOList);
        } catch (Exception e) {
            LOGGER.debug("ERROR: Get search data list", e);
            throw e;
        }
    }

    @PostMapping("/keyword-list")
    public ResponseEntity<List<String>> getKeywordList(@Valid @RequestBody AdminDataListRequestDTO adminDataListRequestDTO) {
        LOGGER.info("POST api/data-list");
        LOGGER.info("POST with body = {}", adminDataListRequestDTO);

        try {
            List<String> keywordList = adminService.getKeywordList(adminDataListRequestDTO);

            return ResponseEntity.ok(keywordList);
        } catch (Exception e) {
            LOGGER.debug("ERROR: Get keyword list", e);
            throw e;
        }
    }

    @GetMapping("/get-latest-order/{keyword}")
    public ResponseEntity<BigInteger> getLatestOrder(@PathVariable(value = "keyword") String keyword) {
        LOGGER.info("GET api/get-latest-order/{}", keyword);

        try {
            BigInteger latestOrder = adminService.getLatestOrder(keyword);

            LOGGER.info("Get latest order successfully with keyword = {}: {}", keyword, latestOrder);

            return ResponseEntity.ok(latestOrder);
        } catch (Exception e) {
            LOGGER.debug("ERROR: Get latest order", e);
            throw e;
        }
    }

    @PostMapping("/create-update/keyword")
    public ResponseEntity<String> createOrUpdateKeyword(@Valid @RequestBody AdminCreateRequestDTO adminCreateRequestDTO) {
        LOGGER.info("POST api/create-update/keyword");
        LOGGER.info("POST with body = {}", adminCreateRequestDTO);

        try {
            adminService.createOrUpdateKeyword(adminCreateRequestDTO);

            String msg = adminCreateRequestDTO.getId() == null ? "created" : "updated";
            LOGGER.info("Keyword data have been created/updated with {}", adminCreateRequestDTO);

            return ResponseEntity.ok("Keyword data have been " + msg);
        } catch (Exception e) {
            LOGGER.debug("ERROR: Create keyword data", e);
            throw e;
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody AdminCreateRequestDTO adminCreateRequestDTO) {
        LOGGER.info("POST api/create");
        LOGGER.info("POST with body = {}", adminCreateRequestDTO);

        try {
            adminService.createSearchData(adminCreateRequestDTO);

            LOGGER.info("{} search data have been created", adminCreateRequestDTO.getSearchDataDTOList().size());

            return ResponseEntity.ok(adminCreateRequestDTO.getSearchDataDTOList().size() + " search data have been created");
        } catch (Exception e) {
            LOGGER.debug("ERROR: Create", e);
            throw e;
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@Valid @RequestBody List<SearchDataDTO> searchDataDTOList) {
        LOGGER.info("PUT /update");
        LOGGER.info("PUT with body = {}", searchDataDTOList);

        try {
            adminService.updateSearchData(searchDataDTOList);

            LOGGER.info("Search data have been updated with {}", searchDataDTOList);

            return ResponseEntity.ok("Search data have been updated");
        } catch (Exception e) {
            LOGGER.debug("ERROR: Update", e);
            throw e;
        }
    }

    @PostMapping("/delete/keyword")
    public ResponseEntity<String> deleteKeywordData(@Valid @RequestBody List<BigInteger> idList) {
        LOGGER.info("POST api/delete/keyword");
        LOGGER.info("POST with body = {}", idList);

        try {
            adminService.deleteKeywordData(idList);

            LOGGER.info("Delete keyword data successfully with idList = {}", idList);

            return ResponseEntity.ok("Delete keyword data successfully with idList = " + idList);
        } catch (Exception e) {
            LOGGER.debug("ERROR: Delete keyword", e);
            throw e;
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@Valid @RequestBody List<BigInteger> idList) {
        LOGGER.info("POST api/delete");
        LOGGER.info("POST with body = {}", idList);

        try {
            adminService.deleteSearchData(idList);

            LOGGER.info("Delete search data successfully with idList = {}", idList);

            return ResponseEntity.ok("Delete search data successfully with idList = " + idList);
        } catch (Exception e) {
            LOGGER.debug("ERROR: Delete", e);
            throw e;
        }
    }
}
