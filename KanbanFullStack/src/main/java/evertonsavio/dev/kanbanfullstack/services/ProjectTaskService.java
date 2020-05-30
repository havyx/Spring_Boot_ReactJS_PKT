package evertonsavio.dev.kanbanfullstack.services;

import evertonsavio.dev.kanbanfullstack.domain.Backlog;
import evertonsavio.dev.kanbanfullstack.domain.ProjectTask;
import evertonsavio.dev.kanbanfullstack.exceptions.ProjectIdException;
import evertonsavio.dev.kanbanfullstack.exceptions.ProjectNotFoundException;
import evertonsavio.dev.kanbanfullstack.repositories.BacklogRepository;
import evertonsavio.dev.kanbanfullstack.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {


    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;


    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        try {
        //PTs to be added to a specific project, project != null, BL exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        //set the bl to pt
        projectTask.setBacklog(backlog);
        //we want our project sequence to be like this: IDPRO-1  IDPRO-2  ...100 101
        Integer BacklogSequence = backlog.getPTSequence();
        // Update the BL SEQUENCE
        BacklogSequence++;

        backlog.setPTSequence(BacklogSequence);

        //Add Sequence to Project Task
        projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        //INITIAL priority when priority null

        //INITIAL status when status is null
        if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
            projectTask.setStatus("TO_DO");
        }

        if (projectTask.getPriority() == null) { //In the future we need projectTask.getPriority()== 0 to handle the form
            projectTask.setPriority(3);
        }

        return projectTaskRepository.save(projectTask);
    }catch (Exception e){
            throw new ProjectNotFoundException("Project not found");
        }
    }

    public Iterable<ProjectTask> findBacklogById(String id) {
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }
}