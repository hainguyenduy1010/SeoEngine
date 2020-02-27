package com.engine.seo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HaiND on 2/17/2020 8:43 PM.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResultDataDTO implements Serializable {

    private static final long serialVersionUID = 2588869100925735203L;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("SearchResultDataDTO = [")
                .append("count = ").append(count).append("; ")
                .append("countFake = ").append(countFake).append("; ")
                .append("totalTime = ").append(totalTime).append("; ")
                .append("searchDataDTOList = ").append(searchDataList).append("; ")
                .append("suggestionDTOList = ").append(suggestionList).append("; ")
                .append("currentPage = ").append(currentPage).append("; ")
                .append("numberResultsPerPage = ").append(numberResultsPerPage).append("]");

        return sb.toString();
    }
}
