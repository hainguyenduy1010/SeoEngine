package com.engine.seo.repository.custom;

import com.engine.seo.model.SearchData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by HaiND on 2/22/2020 11:46 AM.
 */
public class SearchDataRepositoryCustomImpl implements SearchDataRepositoryCustom {

    private static final String SPACE_STR = " ";

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${search.result.number_of_suggestion}")
    private int numberOfSuggestion;

    @Override
    public long countRelateData(String keyword) {

        String[] singleKeywordList = StringUtils.split(keyword, SPACE_STR);
        String queryStr = createQuery(keyword, singleKeywordList, "data.keyword");

        TypedQuery<String> query = entityManager.createQuery(queryStr, String.class);

        return query.getResultList().size();
    }

    @Override
    public List<SearchData> findRelateData(String keyword, int firstResult, int maxResults) {

        String[] singleKeywordList = StringUtils.split(keyword, SPACE_STR);
        String queryStr = createQuery(keyword, singleKeywordList, "data");

        TypedQuery<SearchData> query = entityManager.createQuery(queryStr, SearchData.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);

        return query.getResultList();
    }

    @Override
    public List<String> findRelateKeyword(String keyword) {

        String[] singleKeywordList = StringUtils.split(keyword, SPACE_STR);
        String queryStr = createQuery(keyword, singleKeywordList, "DISTINCT data.keyword");

        TypedQuery<String> query = entityManager.createQuery(queryStr, String.class);
        query.setMaxResults(numberOfSuggestion);

        return query.getResultList();
    }

    private String createQuery(String keyword, String[] singleKeywordList, String requestData) {

        String query;
        StringBuilder querySb = new StringBuilder();

        querySb.append("SELECT ").append(requestData).append(" FROM SearchData data WHERE keyword <> '").append(keyword).append("' AND (");

        for (String singleKeyword : singleKeywordList) {
            querySb.append("LOCATE('").append(singleKeyword).append("', data.keyword) > 0");
            querySb.append(" OR ");
        }

        query = querySb.toString();
        query = StringUtils.removeEnd(query, " OR ");

        return query.concat(") ORDER BY data.order");
    }
}
