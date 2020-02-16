package com.engine.seo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by HaiND on 2/11/2020 10:35 PM.
 */
@Entity
@Table(name = "search_data")
public class SearchData {

    @Id
    private int id;

    @Column
    private String keyword;

    @Column
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("SearchData = [")
                .append("id = ").append(id).append("; ")
                .append("keyword = ").append(keyword).append("; ")
                .append("url = ").append(url).append("]");

        return sb.toString();
    }
}
