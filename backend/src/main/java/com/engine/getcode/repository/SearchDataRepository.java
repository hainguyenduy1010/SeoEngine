package com.engine.getcode.repository;

import com.engine.getcode.model.SearchData;
import com.engine.getcode.repository.custom.SearchDataRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * Created by HaiND on 2/11/2020 11:24 PM.
 */
@Repository
public interface SearchDataRepository extends JpaRepository<SearchData, BigInteger>, SearchDataRepositoryCustom {

    int countByKeyword(String keyword);

    @Query("SELECT COUNT(data) FROM SearchData data WHERE data.keyword = ?1 AND data.url LIKE %?2%")
    long countLikeUrl(String keyword, String filter);

    @Query("SELECT data FROM SearchData data WHERE data.keyword LIKE %?1%")
    List<SearchData> findByLikeKeyword(String keyword, Pageable pageable);

    @Query("SELECT data FROM SearchData data WHERE data.keyword = ?1 AND data.url LIKE %?2%")
    List<SearchData> findByLikeUrl(String keyword, String urlFilter, Pageable pageable);

    @Query("SELECT data FROM SearchData data WHERE data.keyword = ?1")
    List<SearchData> findByKeyword(String keyword, Pageable pageable);

    @Query("SELECT DISTINCT data.url FROM SearchData data WHERE data.url LIKE %?1%")
    List<String> findUrlList(String filter, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE SearchData data SET data.order = data.order + 1 WHERE data.keyword = ?1 AND data.order < ?2 AND data.order >= ?3")
    void updateOrderIncrementRange(String keyword, BigInteger oldOrder, BigInteger newOrder);

    @Modifying
    @Transactional
    @Query("UPDATE SearchData data SET data.order = data.order - 1 WHERE data.keyword = ?1 AND data.order > ?2 AND data.order <= ?3")
    void updateOrderDecrementRange(String keyword, BigInteger oldOrder, BigInteger newOrder);

    @Modifying
    @Transactional
    @Query("UPDATE SearchData data SET data.order = data.order + 1 WHERE data.keyword = ?1 AND data.order > ?2")
    void updateOrderIncrement(String keyword, BigInteger order);

    @Modifying
    @Transactional
    @Query("UPDATE SearchData data SET data.order = data.order - 1 WHERE data.keyword = ?1 AND data.order > ?2")
    void updateOrderDecrement(String keyword, BigInteger order);

    @Modifying
    @Transactional
    @Query("DELETE from SearchData data WHERE data.keyword IN ?1")
    void deleteBatchByKeywordList(Set<String> keywords);
}
