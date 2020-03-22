package com.engine.getcode.repository.custom;

import com.engine.getcode.model.SearchData;

import java.util.List;

/**
 * Created by HaiND on 2/22/2020 11:52 AM.
 */
public interface SearchDataRepositoryCustom {

    long countRelateData(String keyword);

    List<SearchData> findRelateData(String keyword, int firstResult, int maxResults);
}
