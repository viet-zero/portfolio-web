package com.portfolio.service;

import com.portfolio.entity.Project;

public interface ProjectService {

    void addProject(Project project);

    Project findProjectById(Long id);

    boolean removeProjectById(Long id);

    void showProjects();
}