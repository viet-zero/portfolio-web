package com.portfolio.service;

import com.portfolio.entity.Project;
import com.portfolio.repository.ProjectRepository;
import com.portfolio.validation.ProjectValidator;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void addProject(Project project) {

        ProjectValidator.validate(project);

        projectRepository.save(project);
    }

    @Override
    public Project findProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public void showProjects() {

        List<Project> projects = projectRepository.findAll();

        if (projects.isEmpty()) {
            System.out.println("Danh sách dự án đang trống.");
            return;
        }

        System.out.println("Danh sách dự án:");

        for (Project project : projects) {
            System.out.println(project);
        }
    }

    @Override
    public boolean removeProjectById(Long id) {

        if (!projectRepository.existsById(id)) {
            return false;
        }

        projectRepository.deleteById(id);

        return true;
    }
}