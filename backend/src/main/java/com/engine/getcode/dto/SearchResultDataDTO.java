package com.engine.getcode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.modelmapper.internal.Pair;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HaiND on 2/17/2020 8:43 PM.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResultDataDTO implements Serializable {

    private static final long serialVersionUID = 2588869100925735203L;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("count")
    private long count;

    @JsonProperty("count_fake")
    private long countFake;

    @JsonProperty("total_time")
    private long totalTime;

    @JsonProperty("search_data_list")
    private List<SearchDataDTO> searchDataList;

    @JsonProperty("suggestion_list")
    private List<SuggestionDTO> suggestionList;

    @JsonProperty("current_page")
    private Integer currentPage;

    @JsonProperty("number_results_per_page")
    private int numberResultsPerPage;

    @JsonProperty("external_param")
    private ExternalParameterDTO externalParam;

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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getCountFake() {
        return countFake;
    }

    public void setCountFake(long countFake) {
        this.countFake = countFake;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public List<SearchDataDTO> getSearchDataList() {
        return searchDataList;
    }

    public void setSearchDataList(List<SearchDataDTO> searchDataList) {
        this.searchDataList = searchDataList;
    }

    public List<SuggestionDTO> getSuggestionList() {
        return suggestionList;
    }

    public void setSuggestionList(List<SuggestionDTO> suggestionList) {
        this.suggestionList = suggestionList;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public int getNumberResultsPerPage() {
        return numberResultsPerPage;
    }

    public void setNumberResultsPerPage(int numberResultsPerPage) {
        this.numberResultsPerPage = numberResultsPerPage;
    }

    public ExternalParameterDTO getExternalParam() {
        return externalParam;
    }

    public void setExternalParam(ExternalParameterDTO externalParam) {
        this.externalParam = externalParam;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("SearchResultDataDTO = [")
                .append("title = ").append(title).append("; ")
                .append("description = ").append(description).append("; ")
                .append("count = ").append(count).append("; ")
                .append("countFake = ").append(countFake).append("; ")
                .append("totalTime = ").append(totalTime).append("; ")
                .append("searchDataDTOList = ").append(searchDataList).append("; ")
                .append("suggestionDTOList = ").append(suggestionList).append("; ")
                .append("currentPage = ").append(currentPage).append("; ")
                .append("numberResultsPerPage = ").append(numberResultsPerPage).append("; ")
                .append("externalParam = ").append(externalParam).append("]");

        return sb.toString();
    }
}
