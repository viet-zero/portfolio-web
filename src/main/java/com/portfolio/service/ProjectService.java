package com.portfolio.service;

import com.portfolio.entity.Project;

import java.util.List;

public interface ProjectService {

    void addProject(Project project);

    Project findProjectById(Long id);

    List<Project> getAllProjects();

    boolean removeProjectById(Long id);

    void showProjects();
}