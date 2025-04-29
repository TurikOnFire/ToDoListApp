package com.TurikOnFire.ToDoApp.repository;

import com.TurikOnFire.ToDoApp.entity.Category;
import com.TurikOnFire.ToDoApp.entity.Task;
import com.TurikOnFire.ToDoApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserAndCompleted(User user, boolean completed);
    List<Task> findByUserAndCategory(User user, Category category);
    // Другие методы по необходимости
}