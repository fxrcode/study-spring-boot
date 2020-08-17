package io.tony.springstudy.jobqueuefuture.repository;

import io.tony.springstudy.jobqueuefuture.model.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotebookRepository extends JpaRepository<Notebook, String> {
    @Query("SELECT n FROM Notebook n WHERE n.status = ?1")
    List<Notebook> findAllByStatus(String status);
}
