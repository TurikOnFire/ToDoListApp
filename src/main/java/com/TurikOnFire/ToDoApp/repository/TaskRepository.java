package com.TurikOnFire.ToDoApp.repository;

import com.TurikOnFire.ToDoApp.entity.Category;
import com.TurikOnFire.ToDoApp.entity.Task;
import com.TurikOnFire.ToDoApp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(UserEntity user);
    List<Task> findByUserAndCompleted(UserEntity user, boolean completed);
    List<Task> findByUserAndCategory(UserEntity user, Category category);
    // Другие методы по необходимости
}