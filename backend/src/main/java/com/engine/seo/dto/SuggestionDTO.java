package com.engine.seo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by HaiND on 2/21/2020 10:18 PM.
 */
public class SuggestionDTO implements Serializable {

    private static final long serialVersionUID = -8094245237167357240L;

    @JsonProperty("suggestion_keyword")
    private String suggestionKeyword;

    @JsonProperty("path")
    private String path;

    public String getSuggestionKeyword() {
        return suggestionKeyword;
    }

    public void setSuggestionKeyword(String suggestionKeyword) {
        this.suggestionKeyword = suggestionKeyword;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("SuggestionDTO = [")
                .append("suggestionKeyword = ").append(suggestionKeyword).append("; ")
                .append("path = ").append(path).append("]");

        return sb.toString();
    }
}
