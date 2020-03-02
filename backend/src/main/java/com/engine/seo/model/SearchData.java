package com.engine.seo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by HaiND on 2/11/2020 10:35 PM.
 */
@Entity
@Table(name = "search_data")
public class SearchData {

    @Id
    private BigInteger id;

    @Column
    private String keyword;

    @Column
    private String url;

    @Column
    private BigInteger sortkey;

    @Column
    private Date createDate;

    @Column
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

    public BigInteger getSortkey() {
        return sortkey;
    }

    public void setSortkey(BigInteger sortkey) {
        this.sortkey = sortkey;
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
                .append("sortkey = ").append(sortkey).append("; ")
                .append("createDate = ").append(createDate).append("; ")
                .append("updateDate = ").append(updateDate).append("]");

        return sb.toString();
    }
}
