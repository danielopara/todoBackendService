package com.example.todo.repository;

import com.example.todo.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByEmail(String email);

    @Query("SELECT MAX(t.taskId) FROM Todo t  WHERE t.email = :email")
    Long findMaxTaskId(String email);
}
