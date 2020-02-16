package com.engine.seo.service;

import com.engine.seo.dto.SearchDataDTO;
import com.engine.seo.model.SearchData;
import com.engine.seo.repository.SearchDataRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HaiND on 2/16/2020 4:11 PM.
 */
@Service
public class SearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchService.class);

    @Autowired
    SearchDataRepository searchDataRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<SearchDataDTO> search(String keyword) {
        LOGGER.debug("search:in(keyword = {})", keyword);

        List<SearchDataDTO> searchDataDTOList = new ArrayList<>();

        List<SearchData> searchDataList = searchDataRepository.findByKeyword(keyword);

        SearchDataDTO searchDataDTO;
        for (SearchData searchData : searchDataList) {
            searchDataDTO =  modelMapper.map(searchData, SearchDataDTO.class);
            searchDataDTOList.add(searchDataDTO);
        }

        LOGGER.debug("search:out(searchDataDTOList.size = {})", searchDataDTOList.size());

        return searchDataDTOList;
    }
}
