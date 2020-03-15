package com.engine.getcode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by HaiND on 3/1/2020 5:37 PM.
 */
public class AdminDataListRequestDTO implements Serializable {

    private static final long serialVersionUID = 9057945831341126944L;

    @JsonProperty("keyword")
    private String keyword;

    @JsonProperty("current_page")
    private int currentPage;

    @JsonProperty("per_page")
    private int perPage;

    @JsonProperty("sort_by")
    private String sortBy;

    @JsonProperty("is_sort_desc")
    private boolean isSortDesc;

    @JsonProperty("filter")
    private String filter;

    @JsonProperty("type")
    private String type;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public boolean isSortDesc() {
        return isSortDesc;
    }

    public void setSortDesc(boolean sortDesc) {
        isSortDesc = sortDesc;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("AdminDataListRequestDTO = [")
                .append("keyword = ").append(currentPage).append("; ")
                .append("currentPage = ").append(currentPage).append("; ")
                .append("perPage = ").append(perPage).append("; ")
                .append("sortBy = ").append(sortBy).append("; ")
                .append("isSortDesc = ").append(isSortDesc).append("; ")
                .append("filter = ").append(filter).append("; ")
                .append("type = ").append(type).append("]");

        return sb.toString();
    }
}
