package com.engine.getcode.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by HaiND on 3/15/2020 11:51 AM.
 */
@Entity
@Table(name = "keyword_data")
public class KeywordData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "keyword", nullable = false)
    private String keyword;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "createDate", nullable = false)
    private Date createDate;

    @Column(name = "updateDate", nullable = false)
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
