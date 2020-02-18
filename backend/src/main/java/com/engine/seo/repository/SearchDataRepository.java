package com.engine.seo.repository;

import com.engine.seo.model.SearchData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by HaiND on 2/11/2020 11:24 PM.
 */
@Repository
public interface SearchDataRepository extends JpaRepository<SearchData, Integer> {

    @Query(value = "SELECT data FROM SearchData data WHERE data.keyword = ?1 ORDER BY data.sortkey ASC")
    List<SearchData> findByKeywordOrderBySortkeyAsc(String keyword);
}
