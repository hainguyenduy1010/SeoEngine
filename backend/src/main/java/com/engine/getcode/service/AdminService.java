package com.engine.getcode.service;

import com.engine.getcode.dto.*;
import com.engine.getcode.model.KeywordData;
import com.engine.getcode.model.SearchData;
import com.engine.getcode.repository.KeywordDataRepository;
import com.engine.getcode.repository.SearchDataRepository;
import com.engine.getcode.utils.Utils;
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
import java.util.*;

/**
 * Created by HaiND on 2/18/2020 12:20 AM.
 */
@Service
public class AdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

    private static final String TYPE_KEYWORD_LIST_KD = "keyword-list-kd";

    private static final String TYPE_KEYWORD_LIST_SD = "keyword-list-sd";

    private static final String TYPE_COUNT_KEYWORD_ALL = "count-keyword-all";

    private static final String TYPE_COUNT_KEYWORD_LIKE_DATA = "count-keyword-like-keyword";

    private static final String TYPE_COUNT_SEARCH_DATA_ALL = "count-search-data-all";

    private static final String TYPE_COUNT_SEARCH_DATA_LIKE_ULR = "count-search-data-like-url";

    @Value("${admin.login.user}")
    private String userMaster;

    @Value("${admin.login.password}")
    private String passwordMaster;

    @Autowired
    SearchDataRepository searchDataRepository;

    @Autowired
    KeywordDataRepository keywordDataRepository;

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

        switch (adminDataListRequestDTO.getType()) {
            case TYPE_COUNT_KEYWORD_ALL:
                count = keywordDataRepository.count();
                break;
            case TYPE_COUNT_KEYWORD_LIKE_DATA:
                count = keywordDataRepository.countLikeKeyword(adminDataListRequestDTO.getFilter());
                break;
            case TYPE_COUNT_SEARCH_DATA_ALL:
                count = searchDataRepository.countByKeyword(adminDataListRequestDTO.getKeyword());
                break;
            case TYPE_COUNT_SEARCH_DATA_LIKE_ULR:
                count = searchDataRepository.countLikeUrl(adminDataListRequestDTO.getKeyword(), adminDataListRequestDTO.getFilter());
                break;
            default:
                count = 0;
                break;
        }

        LOGGER.debug("Count: {}", count);

        return count;
    }

    public List<KeywordDataDTO> getKeywordDataList(AdminDataListRequestDTO adminDataListRequestDTO) {
        List<KeywordDataDTO> keywordDataDTOList = new ArrayList<>();

        int currentPage = adminDataListRequestDTO.getCurrentPage();
        int perPage = adminDataListRequestDTO.getPerPage();
        String sortBy = adminDataListRequestDTO.getSortBy();
        boolean isSortDesc = adminDataListRequestDTO.isSortDesc();
        String filter = adminDataListRequestDTO.getFilter();

        List<KeywordData> keywordDataList = findKeywordDataList(currentPage, perPage, sortBy, isSortDesc, filter);
        // loop through searchDataDTO then generate search data detail
        keywordDataList.forEach(data -> keywordDataDTOList.add(modelMapper.map(data, KeywordDataDTO.class)));

        return keywordDataDTOList;
    }

    public List<SearchDataDTO> getSearchDataList(AdminDataListRequestDTO adminDataListRequestDTO) {
        List<SearchDataDTO> searchDataDTOList = new ArrayList<>();

        String keyword = adminDataListRequestDTO.getKeyword();
        int currentPage = adminDataListRequestDTO.getCurrentPage();
        int perPage = adminDataListRequestDTO.getPerPage();
        String sortBy = adminDataListRequestDTO.getSortBy();
        boolean isSortDesc = adminDataListRequestDTO.isSortDesc();
        String filter = adminDataListRequestDTO.getFilter();

        List<SearchData> searchDataList = findSearchDataList(keyword, currentPage, perPage, sortBy, isSortDesc, filter);
        // loop through searchDataDTO then generate search data detail
        searchDataList.forEach(data -> searchDataDTOList.add(modelMapper.map(data, SearchDataDTO.class)));

        return searchDataDTOList;
    }

    public List<String> getKeywordList(AdminDataListRequestDTO adminDataListRequestDTO) {
        List<String> keywordList = null;

        String filter = adminDataListRequestDTO.getFilter();

        Pageable pageable = PageRequest.of(0, 10);
        if (TYPE_KEYWORD_LIST_KD.equals(adminDataListRequestDTO.getType())) {
            keywordList = keywordDataRepository.findKeywordList(filter, pageable);
        } else if (TYPE_KEYWORD_LIST_SD.equals(adminDataListRequestDTO.getType())) {
            keywordList = searchDataRepository.findUrlList(filter, pageable);
        }

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

    public void createOrUpdateKeyword(AdminCreateRequestDTO adminCreateRequestDTO) {

        KeywordData keywordData = new KeywordData();
        if (adminCreateRequestDTO.getId() == null) {
            KeywordData keywordDataExist = keywordDataRepository.findByKeyword(adminCreateRequestDTO.getKeyword());
            if (keywordDataExist == null) {
                keywordData.setCreateDate(new Date());
            } else {
                keywordData = keywordDataExist;
            }
        } else {
            Optional<KeywordData> optional = keywordDataRepository.findById(adminCreateRequestDTO.getId());
            if (optional.isPresent()) {
                keywordData = optional.get();
            } else {
                keywordData.setCreateDate(new Date());
            }
        }

        keywordData.setKeyword(adminCreateRequestDTO.getKeyword());
        keywordData.setDescription(adminCreateRequestDTO.getDescription());
        keywordData.setTitle(adminCreateRequestDTO.getTitle());
        keywordData.setUpdateDate(new Date());

        keywordDataRepository.save(keywordData);
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

            searchData = new SearchData();
            BeanUtils.copyProperties(searchDataDTO, searchData);
            searchDataList.add(searchData);

            searchDataRepository.updateOrderIncrement(keyword, searchDataDTO.getOrder().add(BigInteger.valueOf(-1)));
        }

        searchDataRepository.saveAll(searchDataList);
    }

    public void updateSearchData(List<SearchDataDTO> searchDataDTOList) {
        SearchData searchData = new SearchData();
        List<SearchData> searchDataList = new ArrayList<>();
        for (SearchDataDTO searchDataDTO : searchDataDTOList) { //TODO
            SearchData oldSearchData = searchDataRepository.findById(searchDataDTO.getId()).get();
            if (searchDataDTO.getKeyword().equals(oldSearchData.getKeyword())) {
                if (searchDataDTO.getOrder().compareTo(oldSearchData.getOrder()) > 0) {
                    searchDataRepository.updateOrderDecrementRange(searchDataDTO.getKeyword(), oldSearchData.getOrder(), searchDataDTO.getOrder());
                } else if (searchDataDTO.getOrder().compareTo(oldSearchData.getOrder()) < 0) {
                    searchDataRepository.updateOrderIncrementRange(searchDataDTO.getKeyword(), oldSearchData.getOrder(), searchDataDTO.getOrder());
                }

                BeanUtils.copyProperties(searchDataDTO, searchData);
                searchData.setUpdateDate(new Date());
                searchDataList.add(searchData);
                searchDataRepository.saveAll(searchDataList);
            } else {
                searchDataRepository.updateOrderDecrement(oldSearchData.getKeyword(), searchDataDTO.getOrder());
                searchDataRepository.updateOrderIncrement(searchDataDTO.getKeyword(), searchDataDTO.getOrder().add(BigInteger.valueOf(-1)));

                BeanUtils.copyProperties(searchDataDTO, searchData);
                searchDataRepository.save(searchData);
            }
        }

    }

    public void deleteKeywordData(List<BigInteger> idList) {
        Set<String> keywordList = keywordDataRepository.findKeywordListByIdList(idList);

        for (BigInteger id : idList) {
            keywordDataRepository.deleteById(id);
            LOGGER.debug("Delete: {}", id);
        }

        searchDataRepository.deleteBatchByKeywordList(keywordList);
    }

    public void deleteSearchData(List<BigInteger> idList) {
        for (BigInteger id : idList) {
            updateOrder(id, false);
            searchDataRepository.deleteById(id);
            LOGGER.debug("Delete: {}", id);
        }
    }

    private List<KeywordData> findKeywordDataList(int currentPage, int perPage, String sortBy, boolean isSortDesc, String filter) {

        sortBy = Utils.normalizeColumnName(sortBy);

        Pageable pageable = PageRequest.of(currentPage - 1, perPage, Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy));

        if (StringUtils.isEmpty(filter)) {
            return keywordDataRepository.findAll(pageable).getContent();
        } else {
            return keywordDataRepository.findByLikeKeyword(filter, pageable);
        }
    }

    private List<SearchData> findSearchDataList(String keyword, int currentPage, int perPage, String sortBy, boolean isSortDesc, String filter) {

        sortBy = Utils.normalizeColumnName(sortBy);

        Pageable pageable = PageRequest.of(currentPage - 1, perPage, Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy, "order"));

        if (StringUtils.isEmpty(filter)) {
            return searchDataRepository.findByKeyword(keyword, pageable);
        } else {
            return searchDataRepository.findByLikeUrl(keyword, filter, pageable);
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
