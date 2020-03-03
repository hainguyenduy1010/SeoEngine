package com.engine.seo.repository;

import com.engine.seo.model.SearchData;
import com.engine.seo.repository.custom.SearchDataRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by HaiND on 2/11/2020 11:24 PM.
 */
@Repository
public interface SearchDataRepository extends JpaRepository<SearchData, BigInteger>, SearchDataRepositoryCustom {

    long countByKeyword(String keyword);

    @Query("SELECT COUNT(data) FROM SearchData data WHERE data.keyword LIKE %?1%")
    long countLikeKeyword(String filter);

    @Query("SELECT data FROM SearchData data WHERE data.keyword LIKE %?1%")
    List<SearchData> findByKeyword(String keyword, Pageable pageable);

    @Query("SELECT DISTINCT data.keyword FROM SearchData data WHERE data.keyword LIKE %?1%")
    List<String> findKeywordList(String filter, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE SearchData data SET data.order = data.order + 1 WHERE data.keyword = ?1 AND data.order > ?2")
    void updateOrderIncrement(String keyword, BigInteger order);

    @Modifying
    @Transactional
    @Query("UPDATE SearchData data SET data.order = data.order - 1 WHERE data.keyword = ?1 AND data.order > ?2")
    void updateOrderDecrement(String keyword, BigInteger order);
}
