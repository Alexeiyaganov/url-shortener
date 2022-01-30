package com.skienbear.urlshortener.domain;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "urls")
public class Urls {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String shorturl;
    private String longurl;

    //id юзера, который создал короткую ссылку
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    private Integer redirects;
    //счетчик уникальных посещений
    private Integer uniqueuser;


    public Urls() {
    }

    public Urls(String shorturl, String long_url, User user, LocalDateTime createdAt) {
        this.author = user;
        this.shorturl = shorturl;
        this.longurl = long_url;
        this.createdAt = createdAt;
        this.redirects = 0;
        this.uniqueuser = 0;


    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShorturl() {
        return shorturl;
    }

    public void setShort_url(String short_url) {
        this.shorturl = short_url;
    }

    public String getLong_url() {
        return longurl;
    }

    public void setLong_url(String long_url) {
        this.longurl = long_url;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getRedirects() {
        return redirects;
    }

    public void setRedirects(Integer redirects) {
        this.redirects = redirects;
    }

    public Integer getUnique() {
        return uniqueuser;
    }

    public void setUnique(Integer unique) {
        this.uniqueuser = unique;
    }
}
