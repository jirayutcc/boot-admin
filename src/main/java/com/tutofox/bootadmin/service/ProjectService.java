package com.tutofox.bootadmin.service;

import com.tutofox.bootadmin.model.Project;
import com.tutofox.bootadmin.repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project findProjectName(String projectName) {
        return projectRepository.findByProjectName(projectName);
    }

    public Page<Project> listAll(int pageNum) {
        int pageSize = 10;
        
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        
        return projectRepository.findAll(pageable);
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

}
