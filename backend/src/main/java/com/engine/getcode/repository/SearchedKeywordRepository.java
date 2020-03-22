package com.engine.getcode.repository;

import com.engine.getcode.model.SearchedKeyword;
import com.engine.getcode.repository.custom.SearchedKeywordRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * Created by HaiND on 3/21/2020 11:06 PM.
 */
@Repository
public interface SearchedKeywordRepository extends JpaRepository<SearchedKeyword, BigInteger>, SearchedKeywordRepositoryCustom {

    SearchedKeyword findByKeyword(String keyword);
}
