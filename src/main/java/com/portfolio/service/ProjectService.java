package com.portfolio.service;

import com.portfolio.entity.Project;
import java.util.ArrayList;
import java.util.List;
public class ProjectService {
    private List<Project> projects = new ArrayList<>();

    public boolean addProject(Project project){

        if (project.getId() <= 0) {
            return false;
        }

        if (project.getTitle() == null ||
            project.getTitle().isBlank()) {
            return false;
        }

        if (project.getDescription() == null ||
            project.getDescription().isBlank()) {
            return false;
        }

        if (findProjectById(project.getId()) != null) {
            return false;
        }

        projects.add(project);
        return true;
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
    public boolean removeProjectById(Long Id){
        Project projectToRemove = findProjectById(Id);
        if(projectToRemove != null){
            projects.remove(projectToRemove);
            return true;
        }
        return false;
    }
    public boolean updateProject(Long Id,
                                 String nTitle,
                                 String nDdesscripTion,
                                 String nGithubUrl,
                                 String nDemoUrl,
                                 String nImageUrl){
        Project projectUp = findProjectById(Id);
        if(projectUp != null){
            projectUp.setTitle(nTitle);
            projectUp.setDescription(nDdesscripTion);
            projectUp.setGithubUrl(nGithubUrl);
            projectUp.setDemoUrl(nDemoUrl);
            projectUp.setImageUrl(nImageUrl);
            return true;
        }                        
        return false;
    }
    
}
