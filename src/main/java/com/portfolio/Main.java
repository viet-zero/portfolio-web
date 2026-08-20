package com.portfolio;

import com.portfolio.repository.MemoryProjectRepository;
import com.portfolio.repository.ProjectRepository;
import com.portfolio.service.ProjectService;
import com.portfolio.service.ProjectServiceImpl;
import com.portfolio.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        ProjectRepository repository =
        new MemoryProjectRepository();

    ProjectService projectService =
        new ProjectServiceImpl(repository);

    ConsoleUI ui = new ConsoleUI(projectService);

ui.start();
    }
}