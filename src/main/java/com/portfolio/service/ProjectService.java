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
   public void showProjects(){
        if (projects.isEmpty()) {
            System.out.println("Danh sách dự án đang trống.");
            return;
        }
        System.out.println("Danh sách dự án:");
        for(Project project : projects){
            System.out.println(project);
        }
    }
    public Project findProjectById(Long id){
        for(Project project : projects){
            if(project.getId().equals(id)){
                return project;
            }
        }
        return null;
    }
}
