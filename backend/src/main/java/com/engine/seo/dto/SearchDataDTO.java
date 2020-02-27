package com.engine.seo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by HaiND on 2/11/2020 11:19 PM.
 */
public class SearchDataDTO implements Serializable {

    private static final long serialVersionUID = -2100165345239717454L;

    @JsonProperty("id")
    private BigInteger id;

    @JsonProperty("keyword")
    private String keyword;

    @JsonProperty("url")
    private String url;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("sortkey")
    private BigInteger sortkey;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigInteger getSortkey() {
        return sortkey;
    }

    public void setSortkey(BigInteger sortkey) {
        this.sortkey = sortkey;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("SearchDataDTO = [")
                .append("id = ").append(id).append("; ")
                .append("keyword = ").append(keyword).append("; ")
                .append("url = ").append(keyword).append("; ")
                .append("title = ").append(title).append("; ")
                .append("description = ").append(description).append("; ")
                .append("sortkey = ").append(sortkey).append("]");

        return sb.toString();
    }
}
