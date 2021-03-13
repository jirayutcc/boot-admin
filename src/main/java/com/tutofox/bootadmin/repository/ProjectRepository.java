package com.tutofox.bootadmin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutofox.bootadmin.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
	
	Project findByProjectName(String projectName);
}
