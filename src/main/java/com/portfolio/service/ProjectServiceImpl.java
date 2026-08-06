package com.portfolio.service;

import com.portfolio.entity.Project;
import java.util.ArrayList;
import java.util.List;
import com.portfolio.validation.ProjectValidator;

public class ProjectServiceImpl implements ProjectService  {
    private List<Project> projects = new ArrayList<>();
    @Override
    public void addProject(Project project) {

        ProjectValidator.isValid(project);

        if (findProjectById(project.getId()) != null) {
            throw new IllegalArgumentException("ID đã tồn tại.");
        }

        projects.add(project);
    }
    public List<Project> getAllProjects(){
        return projects;
    }
    @Override
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
    @Override
    public Project findProjectById(Long id){
        for(Project project : projects){
            if(project.getId().equals(id)){
                return project;
            }
        }
        return null;
    }
    @Override
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
