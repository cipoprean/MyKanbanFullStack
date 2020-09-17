package com.ciprian.myKanban.services;

import com.ciprian.myKanban.models.ProjectTask;

public interface ProjectTaskService {

    ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask);
}
