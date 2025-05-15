package com.TurikOnFire.ToDoApp.controller;

import com.TurikOnFire.ToDoApp.config.CustomUserDetails;
import com.TurikOnFire.ToDoApp.entity.Task;
import com.TurikOnFire.ToDoApp.entity.UserEntity;
import com.TurikOnFire.ToDoApp.repository.CategoryRepository;
import com.TurikOnFire.ToDoApp.repository.TaskRepository;
import com.TurikOnFire.ToDoApp.repository.UserRepository;
import com.TurikOnFire.ToDoApp.service.CustomUserDetailsService;
import com.TurikOnFire.ToDoApp.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final CustomUserDetailsService userService;
    private final UserRepository userRepository;
    private final TaskService taskService;

    public TaskController(TaskRepository taskRepository, CategoryRepository categoryRepository, CustomUserDetailsService userService, UserRepository userRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.taskService = taskService;
    }


    @GetMapping
    public String listTasks(Model model,
                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        UserEntity user = userRepository.findById(userDetails.getUserId()).get();
        model.addAttribute("tasks", taskRepository.findByUser(user));
        return "tasks/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new Task());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task,
                             @AuthenticationPrincipal UserDetails userDetails) {

        return "redirect:/tasks";
    }

    @PostMapping("/{id}/complete")
    public String markTaskAsCompleted(
            @PathVariable Long id) {


        taskService.markTaskAsCompleted(id);
        return "redirect:/tasks";
    }

}