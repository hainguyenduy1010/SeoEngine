package com.engine.seo.controller;

import com.engine.seo.dto.LoginDTO;
import com.engine.seo.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
}
