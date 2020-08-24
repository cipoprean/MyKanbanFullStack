package com.ciprian.myKanban.services;

import com.ciprian.myKanban.exceptions.ProjectIdException;
import com.ciprian.myKanban.models.Project;
import com.ciprian.myKanban.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project saveOrUpdateProject(Project project) {

        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch (Exception e) {
            throw new ProjectIdException("Project id '" + project.getProjectIdentifier().toUpperCase() + "'already exists");
        }


    }

    @Override
    public Project findProjectByIdentifier(String projectId) {
        Project project =  projectRepository.findByProjectIdentifier(projectId.toUpperCase())
                .orElseThrow(() -> new ProjectIdException("Project with id '" + projectId + "' does not exist"));

        return project;
    }

    @Override
    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId)
                .orElseThrow(() -> new ProjectIdException("Project with id '" + projectId + "' does not exist"));

        projectRepository.delete(project);
    }


}
