package com.engine.seo.repository.custom;

import com.engine.seo.model.SearchData;

import java.util.List;

/**
 * Created by HaiND on 2/22/2020 11:52 AM.
 */
public interface SearchDataRepositoryCustom {

    List<String> findRelateByKeyword(String keyword);
}
