package com.portfolio.service;

import com.portfolio.entity.Project;
import java.util.ArrayList;
import java.util.List;
public class ProjectService {
    private List<Project> projects = new ArrayList<>();

    public void addProject(Project project){
        projects.add(project);
    }
    public List<Project> getAllProjects(){
        return projects;
    }
    
}
