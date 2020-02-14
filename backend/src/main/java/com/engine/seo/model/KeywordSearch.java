package com.engine.seo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by HaiND on 2/11/2020 10:35 PM.
 */
@Entity
@Table(name = "keyword_seo")
public class KeywordSearch {

    @Id
    private int id;

    @Column
    private String keyword;

    @Column
    private String url;
}
