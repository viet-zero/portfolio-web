package com.portfolio.repository;
import com.portfolio.entity.Project;
import java.util.List;
public interface ProjectRepository {
    void save(Project project);
    Project findById(long id);
    List<Project> findAll();
    boolean deleteById(long id);
}
