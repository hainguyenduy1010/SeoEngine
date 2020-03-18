package com.engine.getcode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by HaiND on 3/15/2020 2:15 PM.
 */
public class KeywordDataDTO implements Serializable {

    private static final long serialVersionUID = -7140374118955404622L;

    @JsonProperty("id")
    private BigInteger id;

    @JsonProperty("keyword")
    private String keyword;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("create_date")
    private Date createDate;

    @JsonProperty("update_date")
    private Date updateDate;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("KeywordData = [")
                .append("id = ").append(id).append("; ")
                .append("keyword = ").append(keyword).append("; ")
                .append("title = ").append(title).append("; ")
                .append("description = ").append(description).append("; ")
                .append("createDate = ").append(createDate).append("; ")
                .append("updateDate = ").append(updateDate).append("]");

        return sb.toString();
    }
}
