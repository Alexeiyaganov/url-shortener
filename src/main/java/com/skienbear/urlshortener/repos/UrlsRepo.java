package com.skienbear.urlshortener.repos;

import com.skienbear.urlshortener.domain.Urls;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UrlsRepo extends CrudRepository<Urls, Long> {

    List<Urls> findByShorturl(String short_url);
    List<Urls> findByLongurl(String long_url);

}