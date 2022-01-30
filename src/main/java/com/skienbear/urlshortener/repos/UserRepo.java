package com.skienbear.urlshortener.repos;

import com.skienbear.urlshortener.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
