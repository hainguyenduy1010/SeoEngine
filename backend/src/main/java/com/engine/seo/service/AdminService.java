package com.engine.seo.service;

import com.engine.seo.dto.AdminRequestDTO;
import com.engine.seo.dto.LoginDTO;
import com.engine.seo.dto.SearchDataDTO;
import com.engine.seo.model.SearchData;
import com.engine.seo.repository.SearchDataRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public Long getCount() {
        Long count = searchDataRepository.count();

        LOGGER.debug("Count: {}", count);

        return count;
    }

    public List<SearchDataDTO> getDataList(AdminRequestDTO adminRequestDTO) {
        List<SearchDataDTO> searchDataDTOList = new ArrayList<>();

        int currentPage = adminRequestDTO.getCurrentPage();
        int perPage = adminRequestDTO.getPerPage();
        String filter = adminRequestDTO.getFilter();

        List<SearchData> searchDataList = findSearchDataList(currentPage, perPage, filter);
        // loop through searchDataDTO then generate search data detail
        searchDataList.forEach(data -> searchDataDTOList.add(modelMapper.map(data, SearchDataDTO.class)));

        return searchDataDTOList;
    }

    public void deleteSearchData(List<Integer> idList) {

        for (Integer id : idList) {
//            searchDataRepository.deleteById(id);
            LOGGER.debug("Delete: {}", id);
        }
    }

    private List<SearchData> findSearchDataList(int currentPage, int perPage, String filter) {

        Pageable pageable = PageRequest.of(currentPage - 1, perPage, Sort.by("keyword", "sortkey").ascending());

        if (StringUtils.isEmpty(filter)) {
            return searchDataRepository.findAll(pageable).getContent();
        } else {
            return searchDataRepository.findByKeyword(filter, pageable);
        }
    }
}
