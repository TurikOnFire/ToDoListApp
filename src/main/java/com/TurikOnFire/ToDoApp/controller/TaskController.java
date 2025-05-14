package com.TurikOnFire.ToDoApp.controller;

import com.TurikOnFire.ToDoApp.entity.Task;
import com.TurikOnFire.ToDoApp.repository.CategoryRepository;
import com.TurikOnFire.ToDoApp.repository.TaskRepository;
import com.TurikOnFire.ToDoApp.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public TaskController(TaskRepository taskRepository, CategoryRepository categoryRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }


    @GetMapping
    public String listTasks(Model model,
                            @AuthenticationPrincipal UserDetails userDetails) {
//        User user = userService.findByUsername(userDetails.getUsername());
//        model.addAttribute("tasks", taskRepository.findByUser(user));
//        model.addAttribute("categories", categoryRepository.findByUser(user));
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

}