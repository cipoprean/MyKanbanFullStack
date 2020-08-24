package com.ciprian.myKanban.repositories;

import com.ciprian.myKanban.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    Optional<Project> findByProjectIdentifier(String projectIdentifier);

    @Override
    Iterable<Project> findAll();
}
