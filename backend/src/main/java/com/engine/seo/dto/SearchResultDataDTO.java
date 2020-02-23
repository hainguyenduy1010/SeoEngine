package com.engine.seo.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HaiND on 2/17/2020 8:43 PM.
 */
public class SearchResultDataDTO implements Serializable {

    private static final long serialVersionUID = 2588869100925735203L;

    private int count;

    private long totalTime;

    private List<SearchDataDTO> searchDataList;

    private List<SuggestionDTO> suggestionList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("SearchResultDataDTO = [")
                .append("count = ").append(count).append("; ")
                .append("totalTime = ").append(totalTime).append("; ")
                .append("searchDataDTOList.size = ").append(searchDataList.size()).append("; ")
                .append("suggestionDTOList").append(suggestionList).append("]");

        return sb.toString();
    }
}
