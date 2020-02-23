package com.engine.seo.dto;

import java.io.Serializable;

/**
 * Created by HaiND on 2/21/2020 10:18 PM.
 */
public class SuggestionDTO implements Serializable {

    private static final long serialVersionUID = -8094245237167357240L;

    private String keyword;

    private String url;

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
}
