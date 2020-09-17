package com.ciprian.myKanban.repositories;

import com.ciprian.myKanban.models.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {

    Optional<Backlog> findByProjectIdentifier(String projIdenifier);
}
