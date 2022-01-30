package com.skienbear.urlshortener.repos;

import com.skienbear.urlshortener.domain.Unique;
import com.skienbear.urlshortener.domain.Urls;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UniqueRepo extends CrudRepository<Unique, Long>  {
    List<Unique> findByShorturl(String short_url);
    List<Unique> findByUserid(Long userid);
}
