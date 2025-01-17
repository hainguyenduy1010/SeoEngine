package com.engine.getcode.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by HaiND on 2/11/2020 10:35 PM.
 */
@Entity
@Table(name = "search_data")
public class SearchData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "keyword", nullable = false)
    private String keyword;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "\"order\"", nullable = false)
    private BigInteger order;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigInteger getOrder() {
        return order;
    }

    public void setOrder(BigInteger order) {
        this.order = order;
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

        sb.append("SearchData = [")
                .append("id = ").append(id).append("; ")
                .append("keyword = ").append(keyword).append("; ")
                .append("url = ").append(url).append("; ")
                .append("order = ").append(order).append("; ")
                .append("createDate = ").append(createDate).append("; ")
                .append("updateDate = ").append(updateDate).append("]");

        return sb.toString();
    }
}
