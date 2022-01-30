package com.skienbear.urlshortener.domain;

import org.springframework.data.annotation.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;


@Entity
@Table(name = "uniqueTable")
public class Unique {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String shorturl;

    private Long userid;


    public Unique() {
    }

    public Unique(String short_url, Long user) {
        this.userid = user;
        this.shorturl = short_url;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShort_url() {
        return shorturl;
    }

    public void setShort_url(String short_url) {
        this.shorturl = short_url;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
