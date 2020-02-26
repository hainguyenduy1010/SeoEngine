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
    private int count;

    @JsonProperty("count_fake")
    private int countFake;

    @JsonProperty("total_time")
    private long totalTime;

    @JsonProperty("search_data_list")
    private List<SearchDataDTO> searchDataList;

    @JsonProperty("suggestion_list")
    private List<SuggestionDTO> suggestionList;

    @JsonProperty("current_page")
    private Integer currentPage;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCountFake() {
        return countFake;
    }

    public void setCountFake(int countFake) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("SearchResultDataDTO = [")
                .append("count = ").append(count).append("; ")
                .append("countFake = ").append(countFake).append("; ")
                .append("totalTime = ").append(totalTime).append("; ")
                .append("searchDataDTOList.size = ").append(searchDataList.size()).append("; ")
                .append("suggestionDTOList = ").append(suggestionList).append("; ")
                .append("currentPage = ").append(currentPage).append("]");

        return sb.toString();
    }
}
