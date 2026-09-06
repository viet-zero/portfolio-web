package com.portfolio.controller;

import com.portfolio.entity.Project;
import com.portfolio.service.ProjectService;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController 
@RequestMapping ("/api/projects")
public class ProjectController {
   private final ProjectService projectService;
   
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public void addProject(@RequestBody Project project) {
        projectService.addProject(project);
    }

    @GetMapping("/{id}")
    public Project findProjectById(@PathVariable Long id) {
        return projectService.findProjectById(id);
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @DeleteMapping("/{id}")
    public boolean removeProjectById(@PathVariable Long id) {
        return projectService.removeProjectById(id);
    } 

    @GetMapping("/{id}")
    public Project updateProject(@PathVariable Long id, @RequestBody Project project) {

        return projectService.updateProject(id, project);
    }
    
}
