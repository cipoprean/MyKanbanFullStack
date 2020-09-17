package com.ciprian.myKanban.services;

import com.ciprian.myKanban.exceptions.ProjectIdException;
import com.ciprian.myKanban.models.Backlog;
import com.ciprian.myKanban.models.Project;
import com.ciprian.myKanban.repositories.BacklogRepository;
import com.ciprian.myKanban.repositories.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    private final BacklogRepository backlogRepository;

    public ProjectServiceImpl(final ProjectRepository projectRepository, BacklogRepository backlogRepository) {
        this.projectRepository = projectRepository;
        this.backlogRepository = backlogRepository;
    }

    @Override
    public Project saveOrUpdateProject(Project project) {

        try {
            project.setProjectIdentifier(getProjIdenifier(project.getProjectIdentifier()));

            if(project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(getProjIdenifier(project.getProjectIdentifier()));
            }

            if(project.getId() != null) {
                Backlog backlog = backlogRepository.findByProjectIdentifier(getProjIdenifier(project.getProjectIdentifier()))
                        .orElseThrow(() -> new ServerErrorException("Cannot find specified backlog"));
                project.setBacklog(backlog);
            }

            return projectRepository.save(project);
        }catch (Exception e) {
            throw new ProjectIdException("Project id '" + getProjIdenifier(project.getProjectIdentifier()) + "'already exists");
        }


    }

    private String getProjIdenifier(String projectIdentifier) {
        return projectIdentifier.toUpperCase();
    }

    @Override
    public Project findProjectByIdentifier(String projectId) {
        Project project =  projectRepository.findByProjectIdentifier(getProjIdenifier(projectId))
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
