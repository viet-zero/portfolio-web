package com.portfolio.ui;

import com.portfolio.entity.Project;
import com.portfolio.service.ProjectServiceImpl;
import com.portfolio.service.ProjectService;
import java.util.Scanner;
import java.util.InputMismatchException;

public class ConsoleUI {
    
    private final ProjectService projectService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(){
        projectService = new ProjectServiceImpl();
    }
    public void showMenu(){
        System.out.println();
        System.out.println("========== PORTFOLIO ==========");
        System.out.println("1. Add Project");
        System.out.println("2. Show Projects");
        System.out.println("3. Find Project");
        System.out.println("4. Update Project");
        System.out.println("5. Delete Project");
        System.out.println("0. Exit");
        System.out.println("===============================");
        System.out.print("Choose: ");
    }
    private Long inputLong(String message) {

        while (true) {

            try {

                System.out.print(message);

                Long value = scanner.nextLong();
                scanner.nextLine();

                return value;

            } catch (InputMismatchException e) {

                System.out.println("Vui lòng nhập số!");

                scanner.nextLine();

            }

        }

    }
    private String inputString(String message) {
    System.out.print(message);
    return scanner.nextLine();
    }
    public void start(){
        while(true){
            showMenu();
            
            Long choice = inputLong("");
            switch(choice.intValue()){
                case 1:
                    addProject();
                    break;

                case 2:
                    projectService.showProjects();
                    break;

                case 0:
                    System.out.println("Goodbye!");
                    return;

            default:
                System.out.println("Lua chon khong hop le!");
            }
        }
    }
    public void addProject(){
        System.out.println("\n========== Add Project ==========");
        
        Long id = inputLong("ID: ");

        String title = inputString("Title: ");

        String description = inputString("Description: ");

        String githubUrl = inputString("Github URL: ");

        String demoUrl = inputString("Demo URL: ");

        String imageUrl = inputString("Image URL: ");

        Project project = new Project(id, title, description, githubUrl, demoUrl, imageUrl);

        try {

            projectService.addProject(project);

            System.out.println("Thêm Project thành công!");

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());

        }

    }
}
