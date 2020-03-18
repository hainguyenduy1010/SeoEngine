package com.engine.getcode.repository;

import com.engine.getcode.model.KeywordData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * Created by HaiND on 3/15/2020 12:21 PM.
 */
public interface KeywordDataRepository extends JpaRepository<KeywordData, BigInteger> {


    @Query("SELECT COUNT(data) FROM KeywordData data WHERE data.keyword LIKE %?1%")
    long countLikeKeyword(String filter);

    KeywordData findByKeyword(String keyword);

    @Query("SELECT data FROM KeywordData data WHERE data.keyword LIKE %?1%")
    List<KeywordData> findByLikeKeyword(String keyword, Pageable pageable);

    @Query("SELECT data.keyword FROM KeywordData data WHERE data.keyword LIKE %?1%")
    List<String> findKeywordList(String filter, Pageable pageable);

    @Query("SELECT data.keyword FROM KeywordData data WHERE data.id IN ?1")
    Set<String> findKeywordListByIdList(List<BigInteger> ids);
}
