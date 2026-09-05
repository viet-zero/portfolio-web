package com.portfolio.repository;

import com.portfolio.config.DatabaseConnection;
import com.portfolio.entity.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MySQLProjectRepository
        implements ProjectRepository {

    @Override
    public List<Project> findAll() {

        List<Project> projects = new ArrayList<>();

        String sql = "SELECT * FROM projects";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet resultSet =
                    statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Project project = new Project(
                    resultSet.getLong("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getString("github_url"),
                    resultSet.getString("demo_url"),
                    resultSet.getString("image_url")
                );

                projects.add(project);
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Không thể lấy danh sách Project.",
                    e
            );
        }

        return projects;
    }
}