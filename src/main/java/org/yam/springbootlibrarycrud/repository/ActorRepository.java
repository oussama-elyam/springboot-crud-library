package org.yam.springbootlibrarycrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yam.springbootlibrarycrud.entitie.Actor;

@Repository

public interface ActorRepository extends JpaRepository<Actor, Long> {
    boolean existsByMail(String name);
}
