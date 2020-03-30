package com.engine.getcode.repository.custom;

import java.util.List;

/**
 * Created by HaiND on 3/21/2020 11:22 PM.
 */
public interface SearchedKeywordRepositoryCustom {

    List<String> findRelateKeyword(String keyword, int count);
}
