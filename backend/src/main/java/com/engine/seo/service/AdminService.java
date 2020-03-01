package com.engine.seo.service;

import com.engine.seo.dto.LoginDTO;
import com.engine.seo.dto.SearchDataDTO;
import com.engine.seo.repository.SearchDataRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by HaiND on 2/18/2020 12:20 AM.
 */
@Service
public class AdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

    @Value("${admin.login.user}")
    private String userMaster;

    @Value("${admin.login.password}")
    private String passwordMaster;

    @Autowired
    SearchDataRepository searchDataRepository;

    @Autowired
    ModelMapper modelMapper;

    public boolean login(LoginDTO loginDTO) {
        String user = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        if (userMaster.equals(user) && passwordMaster.equals(password)) {
            LOGGER.debug("Login successfully!");
            return true;
        } else {
            LOGGER.debug("Login failed!");
            return false;
        }
    }
}
