package com.engine.seo.dto;

import java.io.Serializable;

/**
 * Created by HaiND on 2/11/2020 11:19 PM.
 */
public class SearchDataDTO implements Serializable {

    private static final long serialVersionUID = -2100165345239717454L;

    private int id;

    private String keyword;

    private String url;

    private String title;

    private String description;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("SearchDataDTO = [")
                .append("id = ").append(id).append("; ")
                .append("keyword = ").append(keyword).append("; ")
                .append("url = ").append(keyword).append("; ")
                .append("title = ").append(title).append("; ")
                .append("summary = ").append(description).append("]");

        return sb.toString();
    }
}
