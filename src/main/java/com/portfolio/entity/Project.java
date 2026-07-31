package com.portfolio.entity;
public class Project{
    
    private Long id;
    private String title;
    private String description;
    private String githubUrl;
    private String demoUrl;
    private String imageUrl;
    
    public Project() {
    }
    public Project(Long id,
                String title,
                String description,
                String githubUrl,
                String demoUrl,
                String imageUrl) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.githubUrl = githubUrl;
        this.demoUrl = demoUrl;
        this.imageUrl = imageUrl;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getGithubUrl() {
        return githubUrl;
    }
    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }
    public String getDemoUrl() {
        return demoUrl;
    }
    public void setDemoUrl(String demoUrl) {
        this.demoUrl = demoUrl;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}