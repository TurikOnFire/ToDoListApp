package com.TurikOnFire.ToDoApp.repository;

import com.TurikOnFire.ToDoApp.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    List<Authority> findByUsername(String username);
}