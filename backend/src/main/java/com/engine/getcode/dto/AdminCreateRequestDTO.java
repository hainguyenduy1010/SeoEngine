package com.engine.getcode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by HaiND on 3/8/2020 6:02 PM.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminCreateRequestDTO implements Serializable {

    private static final long serialVersionUID = -5967000672979386790L;

    @JsonProperty("id")
    private BigInteger id;

    @JsonProperty("keyword")
    private String keyword;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("data_list")
    private List<SearchDataDTO> searchDataDTOList;

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
                .append("id = ").append(id).append("; ")
                .append("keyword = ").append(keyword).append("; ")
                .append("title = ").append(title).append("; ")
                .append("description = ").append(description).append("; ")
                .append("searchDataDTOList = ").append(searchDataDTOList).append("]");

        return sb.toString();
    }
}
