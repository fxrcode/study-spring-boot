package com.fxrcode.doubancomments.movies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewDAO, Long> {
    ReviewDAO findById(long id);
}
