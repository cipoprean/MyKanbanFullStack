package com.ciprian.myKanban.services;

import com.ciprian.myKanban.models.Backlog;
import com.ciprian.myKanban.models.ProjectTask;
import com.ciprian.myKanban.repositories.BacklogRepository;
import com.ciprian.myKanban.repositories.ProjectTaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {

    private final BacklogRepository backlogRepository;
    private final ProjectTaskRepository projectTaskRepository;

    public ProjectTaskServiceImpl(final BacklogRepository backlogRepository, final ProjectTaskRepository projectTaskRepository) {
        this.backlogRepository = backlogRepository;
        this.projectTaskRepository = projectTaskRepository;
    }

    @Override
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier)
                .orElseThrow(() -> new ServerErrorException("Cannot find specified backlog"));

        projectTask.setBacklog(backlog);
        Integer backlogSequence = backlog.getPTSequence();
        backlogSequence++;
        backlog.setPTSequence(backlogSequence);
        projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        if(projectTask.getPriority() == null) {
            projectTask.setPriority(3);
        }

        if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }
}
