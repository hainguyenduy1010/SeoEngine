package com.engine.getcode.repository;

import com.engine.getcode.model.SearchedKeyword;
import com.engine.getcode.repository.custom.SearchedKeywordRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by HaiND on 3/21/2020 11:06 PM.
 */
@Repository
public interface SearchedKeywordRepository extends JpaRepository<SearchedKeyword, BigInteger>, SearchedKeywordRepositoryCustom {

    SearchedKeyword findByKeyword(String keyword);

    @Query("SELECT DISTINCT data.keyword FROM SearchedKeyword data WHERE data.keyword <> ?1 AND data.keyword LIKE %?1%")
    List<String> findListLikeKeyword(String keyword, Pageable pageable);
}
