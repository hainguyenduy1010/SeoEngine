package com.engine.seo.controller;

import com.engine.seo.dto.AdminRequestDTO;
import com.engine.seo.dto.LoginDTO;
import com.engine.seo.dto.SearchDataDTO;
import com.engine.seo.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/count")
    public ResponseEntity<Long> getDataList() {
        LOGGER.info("GET api/count");

        try {
            Long count = adminService.getCount();

            return ResponseEntity.ok(count);
        } catch (Exception e) {
            LOGGER.debug("ERROR: Login", e);
            throw e;
        }
    }

    @PostMapping("/data-list")
    public ResponseEntity<List<SearchDataDTO>> getDataList(@Valid @RequestBody AdminRequestDTO adminRequestDTO) {
        LOGGER.info("POST api/data-list");
        LOGGER.info("POST with body = {}", adminRequestDTO);

        try {
            List<SearchDataDTO> searchDataDTOList = adminService.getDataList(adminRequestDTO);

            return ResponseEntity.ok(searchDataDTOList);
        } catch (Exception e) {
            LOGGER.debug("ERROR: Login", e);
            throw e;
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@Valid @RequestBody List<Integer> idList) {
        LOGGER.info("POST api/delete");
        LOGGER.info("POST with body = {}", idList);

        try {
            adminService.deleteSearchData(idList);

            LOGGER.info("Delete algorithm successfully with idList = {}", idList);

            return ResponseEntity.ok("Delete algorithm successfully with idList = " + idList);
        } catch (Exception e) {
            LOGGER.debug("ERROR: Login", e);
            throw e;
        }
    }
}
