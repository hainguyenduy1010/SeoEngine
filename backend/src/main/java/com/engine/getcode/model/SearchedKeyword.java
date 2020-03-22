package com.engine.getcode.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by HaiND on 3/21/2020 11:03 PM.
 */
@Entity
@Table(name = "searched_keyword")
public class SearchedKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "keyword", nullable = false)
    private String keyword;

    @Column(name = "init_date", nullable = false)
    private Date initDate;

    @Column(name = "search_count", nullable = false)
    private BigInteger searchCount;

    @Column(name = "last_date", nullable = false)
    private Date lastDate;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public BigInteger getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(BigInteger searchCount) {
        this.searchCount = searchCount;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("SearchData = [")
                .append("id = ").append(id).append("; ")
                .append("keyword = ").append(keyword).append("; ")
                .append("searchCount = ").append(searchCount).append("; ")
                .append("initDate = ").append(initDate).append("; ")
                .append("lastDate = ").append(lastDate).append("]");

        return sb.toString();
    }
}
