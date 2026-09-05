package com.portfolio.repository;
import java.util.ArrayList;
import java.util.List;
import com.portfolio.entity.Project;
public class MemoryProjectRepository implements ProjectRepository {
    private final List<Project> projects = new ArrayList<>();

    @Override
    public void save(Project project){
        projects.add(project);
    }
    @Override
    public Project findById(long id){
        for(Project x : projects ){
            if(x.getId().equals(id) ){
                return x;
            }
        }
        return null;
    }
    @Override
    public List<Project> findAll(){
        return projects;
    }
    @Override
    public  deleteById(long id){
        Project xProject =findById(id);
        if(xProject!=null) {
            projects.remove(xProject);
        }
    }
}
