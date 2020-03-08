package com.engine.seo.service;

import com.engine.seo.dto.AdminCreateRequestDTO;
import com.engine.seo.dto.AdminDataListRequestDTO;
import com.engine.seo.dto.LoginDTO;
import com.engine.seo.dto.SearchDataDTO;
import com.engine.seo.model.SearchData;
import com.engine.seo.repository.SearchDataRepository;
import com.engine.seo.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Long getCount(AdminDataListRequestDTO adminDataListRequestDTO) {
        long count;

        if (StringUtils.isEmpty(adminDataListRequestDTO.getFilter())) {
            count = searchDataRepository.count();
        } else {
            count = searchDataRepository.countLikeKeyword(adminDataListRequestDTO.getFilter());
        }

        LOGGER.debug("Count: {}", count);

        return count;
    }

    public List<SearchDataDTO> getDataList(AdminDataListRequestDTO adminDataListRequestDTO) {
        List<SearchDataDTO> searchDataDTOList = new ArrayList<>();

        int currentPage = adminDataListRequestDTO.getCurrentPage();
        int perPage = adminDataListRequestDTO.getPerPage();
        String sortBy = adminDataListRequestDTO.getSortBy();
        boolean isSortDesc = adminDataListRequestDTO.isSortDesc();
        String filter = adminDataListRequestDTO.getFilter();

        List<SearchData> searchDataList = findSearchDataList(currentPage, perPage, sortBy, isSortDesc, filter);
        // loop through searchDataDTO then generate search data detail
        searchDataList.forEach(data -> searchDataDTOList.add(modelMapper.map(data, SearchDataDTO.class)));

        return searchDataDTOList;
    }

    public List<String> getKeywordList(AdminDataListRequestDTO adminDataListRequestDTO) {
        List<String> keywordList;

        String filter = adminDataListRequestDTO.getFilter();

        Pageable pageable = PageRequest.of(0, 10);
        keywordList = searchDataRepository.findKeywordList(filter, pageable);

        return  keywordList;
    }

    public BigInteger getLatestOrder(String keyword) {
        Pageable pageable = PageRequest.of(0, 1, Sort.by("order").descending());
        List<SearchData> searchDataList = searchDataRepository.findByKeyword(keyword, pageable);

        if (searchDataList.isEmpty()) {
            return BigInteger.valueOf(0);
        }

        return searchDataList.get(0).getOrder();
    }

    public void createSearchData(AdminCreateRequestDTO adminCreateRequestDTO) {
        String keyword = adminCreateRequestDTO.getKeyword();
        List<SearchDataDTO> searchDataDTOList = adminCreateRequestDTO.getSearchDataDTOList();

        SearchData searchData;
        List<SearchData> searchDataList = new ArrayList<>();
        for (SearchDataDTO searchDataDTO : searchDataDTOList) {
            searchDataDTO.setKeyword(keyword);
            searchDataDTO.setCreateDate(new Date());
            searchDataDTO.setUpdateDate(new Date());

            searchDataRepository.updateOrderIncrement(keyword, searchDataDTO.getOrder().add(BigInteger.valueOf(-1)));

            searchData = new SearchData();
            BeanUtils.copyProperties(searchDataDTO, searchData);
            searchDataList.add(searchData);
        }

        searchDataRepository.saveAll(searchDataList);
    }

    public void deleteSearchData(List<BigInteger> idList) {
        for (BigInteger id : idList) {
            updateOrder(id, false);
            searchDataRepository.deleteById(id);
            LOGGER.debug("Delete: {}", id);
        }
    }

    private List<SearchData> findSearchDataList(int currentPage, int perPage, String sortBy, boolean isSortDesc, String filter) {

        sortBy = Utils.normalizeColumnName(sortBy);

        Pageable pageable = PageRequest.of(currentPage - 1, perPage, Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy, "order"));

        if (StringUtils.isEmpty(filter)) {
            return searchDataRepository.findAll(pageable).getContent();
        } else {
            return searchDataRepository.findByLikeKeyword(filter, pageable);
        }
    }

    private void updateOrder(BigInteger id, boolean isIncrement) {

        Optional<SearchData> optionalSearchData = searchDataRepository.findById(id);

        if (optionalSearchData.isPresent()) {

            SearchData searchData = optionalSearchData.get();
            String keyword = searchData.getKeyword();
            BigInteger order = searchData.getOrder();

            if (isIncrement) {
                searchDataRepository.updateOrderIncrement(keyword, order);
            } else {
                searchDataRepository.updateOrderDecrement(keyword, order);
            }
        }
    }
}
