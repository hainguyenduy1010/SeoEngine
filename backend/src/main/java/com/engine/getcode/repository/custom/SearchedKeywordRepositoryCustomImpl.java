package com.engine.getcode.repository.custom;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by HaiND on 3/21/2020 11:23 PM.
 */
public class SearchedKeywordRepositoryCustomImpl implements SearchedKeywordRepositoryCustom {

    private static final String SPACE_STR = " ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> findRelateKeyword(String keyword, int count) {

        String queryStr = createQuery(keyword);

        TypedQuery<String> query = entityManager.createQuery(queryStr, String.class);
        query.setMaxResults(count);

        return query.getResultList();
    }

    private String createQuery(String keyword) {
        String[] singleKeywordList = StringUtils.split(keyword, SPACE_STR);

        String query;
        StringBuilder querySb = new StringBuilder();

        querySb.append("SELECT DISTINCT data.keyword FROM SearchedKeyword data WHERE keyword <> '").append(keyword)
                .append("' AND keyword NOT LIKE '%").append(keyword).append("%' AND (");

        for (String singleKeyword : singleKeywordList) {
            querySb.append("LOCATE('").append(singleKeyword).append("', data.keyword) > 0");
            querySb.append(" OR ");
        }

        query = querySb.toString();
        query = StringUtils.removeEnd(query, " OR ");

        return query.concat(") ORDER BY data.searchCount DESC");
    }
}
