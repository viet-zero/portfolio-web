package com.portfolio.service;

import com.portfolio.entity.Project;
import java.util.ArrayList;
import java.util.List;
import com.portfolio.validation.ProjectValidator;
import com.portfolio.repository.ProjectRepository;;

public class ProjectServiceImpl implements ProjectService  {
    private final ProjectRepository projectRepository;
    
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    @Override
    public void addProject(Project project) {

        ProjectValidator.isValid(project);

        if (findProjectById(project.getId()) != null) {
            throw new IllegalArgumentException("ID đã tồn tại.");
        }

        projectRepository.save(project);
    }
    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }
    @Override
    public void showProjects(){
        List<Project> projects = projectRepository.findAll();
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
    public Project findProjectById(Long id) {
        return projectRepository.findById(id);
    }
    @Override
    public boolean removeProjectById(Long id) {
        return projectRepository.deleteById(id);
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
