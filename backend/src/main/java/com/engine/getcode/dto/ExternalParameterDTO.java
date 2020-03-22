package com.engine.getcode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by HaiND on 3/22/2020 7:50 PM.
 */
public class ExternalParameterDTO implements Serializable {

    private static final long serialVersionUID = -4903017117013309135L;

    @JsonProperty("url")
    private String url;

    @JsonProperty("key")
    private String key;

    @JsonProperty("cx")
    private String cx;

    @JsonProperty("start")
    private int start;

    @JsonProperty("limit")
    private int limit;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
