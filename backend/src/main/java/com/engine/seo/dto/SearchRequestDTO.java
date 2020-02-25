package com.engine.seo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * Created by HaiND on 2/25/2020 10:05 PM.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchRequestDTO implements Serializable {

    private static final long serialVersionUID = -2848114928285706134L;

    @JsonProperty("keyword")
    private String keyword;

    @JsonProperty("current_page")
    private Integer currentPage;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("SearchRequestDTO = [")
                .append("keyword = ").append(keyword).append("; ")
                .append("currentPage = ").append(currentPage).append("]");

        return sb.toString();
    }
}
