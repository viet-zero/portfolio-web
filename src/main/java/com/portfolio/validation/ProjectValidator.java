package com.portfolio.validation;

import com.portfolio.entity.Project;

public class ProjectValidator {

    public static void isValid(Project project){

        if(project.getId() <= 0){
            throw new IllegalArgumentException("ID phải lớn hơn 0.");
        }

        if(project.getTitle() == null ||
           project.getTitle().isBlank()){
            throw new IllegalArgumentException("Title không được để trống.");
        }

        if(project.getDescription() == null ||
           project.getDescription().isBlank()){
            throw new IllegalArgumentException("Description không được để trống.");
        }

    }

}
