package com.TurikOnFire.ToDoApp.repository;

import com.TurikOnFire.ToDoApp.entity.Category;
import com.TurikOnFire.ToDoApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}