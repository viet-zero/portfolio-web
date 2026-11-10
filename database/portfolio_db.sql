-- Database schema for the portfolio project
-- MySQL 8+
-- Run in MySQL Workbench, or: mysql -u root -p < portfolio_db.sql
-- This SQL includes tables for the current project and planned multi-user profiles.
-- The Java entities/repositories must also be implemented before the app can use the planned tables.

CREATE DATABASE IF NOT EXISTS portfolio_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE portfolio_db;

-- Accounts: store a password hash, never a plain-text password.
CREATE TABLE IF NOT EXISTS users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  full_name VARCHAR(150) NOT NULL,
  email VARCHAR(254) NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  role VARCHAR(30) NOT NULL DEFAULT 'USER',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_users_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Portfolio projects. owner_user_id is nullable for compatibility with existing Project code;
-- new multi-user features should set it and the Java Project entity should map it.
CREATE TABLE IF NOT EXISTS projects (
  id BIGINT NOT NULL AUTO_INCREMENT,
  owner_user_id BIGINT NULL,
  title VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  github_url VARCHAR(255) NULL,
  demo_url VARCHAR(255) NULL,
  image_url VARCHAR(255) NULL,
  PRIMARY KEY (id),
  KEY idx_projects_owner_user_id (owner_user_id),
  CONSTRAINT fk_projects_owner FOREIGN KEY (owner_user_id)
    REFERENCES users (id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS skills (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  name VARCHAR(100) NOT NULL,
  proficiency VARCHAR(50) NULL,
  PRIMARY KEY (id),
  KEY idx_skills_user_id (user_id),
  CONSTRAINT fk_skills_user FOREIGN KEY (user_id)
    REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS certificates (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  name VARCHAR(200) NOT NULL,
  issuer VARCHAR(200) NULL,
  issued_at DATE NULL,
  credential_url VARCHAR(500) NULL,
  PRIMARY KEY (id),
  KEY idx_certificates_user_id (user_id),
  CONSTRAINT fk_certificates_user FOREIGN KEY (user_id)
    REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS awards (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  name VARCHAR(200) NOT NULL,
  issuer VARCHAR(200) NULL,
  awarded_at DATE NULL,
  description TEXT NULL,
  PRIMARY KEY (id),
  KEY idx_awards_user_id (user_id),
  CONSTRAINT fk_awards_user FOREIGN KEY (user_id)
    REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- If this becomes a Fiverr-style marketplace, plan separate tables for services/gigs,
-- orders, messages, reviews, and uploaded-file metadata. Add them after those workflows
-- and their Java entities are defined; do not store uploaded file contents in these tables.
