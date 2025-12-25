package org.yam.springbootlibrarycrud.service;

import org.springframework.data.domain.Page;
import org.yam.springbootlibrarycrud.entitie.Actor;

public interface ActorService {
    Actor createActor(Actor body);

    Page<Actor> getActors(int page, int size);

    Actor updateActor(Actor body, Long id);

    void deleteActor(Long id);
}
