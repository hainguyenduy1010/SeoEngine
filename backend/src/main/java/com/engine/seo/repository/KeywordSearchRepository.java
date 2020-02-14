package com.engine.seo.repository;

import com.engine.seo.model.KeywordSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by HaiND on 2/11/2020 11:24 PM.
 */
public interface KeywordSearchRepository extends JpaRepository<KeywordSearch, Integer> {
}
