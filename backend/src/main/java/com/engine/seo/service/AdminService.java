package com.engine.seo.service;

import com.engine.seo.dto.SearchDataDTO;
import com.engine.seo.repository.SearchDataRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by HaiND on 2/18/2020 12:20 AM.
 */
@Service
public class AdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    SearchDataRepository searchDataRepository;

    @Autowired
    ModelMapper modelMapper;

    public SearchDataDTO createSearchData(SearchDataDTO searchDataDTO) {

        return searchDataDTO;
    }
}
