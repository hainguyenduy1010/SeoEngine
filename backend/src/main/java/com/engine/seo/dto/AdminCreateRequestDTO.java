package com.engine.seo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HaiND on 3/8/2020 6:02 PM.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminCreateRequestDTO implements Serializable {

    private static final long serialVersionUID = -5967000672979386790L;

    @JsonProperty("keyword")
    private String keyword;

    @JsonProperty("data_list")
    private List<SearchDataDTO> searchDataDTOList;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<SearchDataDTO> getSearchDataDTOList() {
        return searchDataDTOList;
    }

    public void setSearchDataDTOList(List<SearchDataDTO> searchDataDTOList) {
        this.searchDataDTOList = searchDataDTOList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("AdminCreateRequestDTO = [")
                .append("keyword = ").append(keyword).append("; ")
                .append("searchDataDTOList = ").append(searchDataDTOList).append("]");

        return sb.toString();
    }
}
