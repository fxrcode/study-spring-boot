package io.tony.springstudy.jobqueuefuture.repository;

import io.tony.springstudy.jobqueuefuture.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
