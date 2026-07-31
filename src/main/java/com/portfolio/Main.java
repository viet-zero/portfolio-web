package com.portfolio;

import com.portfolio.entity.Project;
import com.portfolio.service.ProjectService;

public class Main {
    public static void main(String[] args) {
        ProjectService projectService = new ProjectService();
        Project p1 = new Project(1L,
                "Portfolio",
                "My personal website",
                "https://github.com/...",
                "",
                "");
        projectService.addProject(p1);
        projectService.addProject(new Project(
            2L,
            "Coffee Shop",
            "Quan ly quan ca phe",
            "github",
            "",
            ""
    ));
    Project project = projectService.findProjectById(99L);

    if (project != null) {
        System.out.println(project);
    } else {
        System.out.println("Khong tim thay!");
    }
        projectService.showProjects();
    }
}
