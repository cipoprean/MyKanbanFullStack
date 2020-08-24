package com.ciprian.myKanban.services;

import com.ciprian.myKanban.models.Project;

import java.util.List;

public interface ProjectService {

    Project saveOrUpdateProject(Project project);
    Project findProjectByIdentifier(String projectId);
    Iterable<Project> findAllProjects();
    void deleteProjectByIdentifier(String projectId);
}
