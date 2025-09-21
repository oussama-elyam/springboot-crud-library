package org.yam.springbootlibrarycrud.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yam.springbootlibrarycrud.common.exception.ResourceConflictException;
import org.yam.springbootlibrarycrud.model.Actor;
import org.yam.springbootlibrarycrud.repository.ActorRepository;
import org.yam.springbootlibrarycrud.service.ActorService;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Actor createActor(Actor body) {
        if (actorRepository.existsByMail(body.getMail())) {
            throw new ResourceConflictException(
                    "Actor mail must be unique. The mail '" + body.getMail() + "' already exists."
            );
        }

        Actor newActor = Actor.builder()
                .mail(body.getMail())
                .password(body.getPassword())
                .actorRole(body.getActorRole())
                .build();

        return actorRepository.save(newActor);
    }

    @Override
    public Page<Actor> getActors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return actorRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Actor updateActor(Actor body, Long id) {

        Actor existingActor = actorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actor with ID " + id + " not found"));

        modelMapper.map(body, existingActor);
        return existingActor; // no need to call save(), entity is managed
    }

    @Transactional
    @Override
    public void deleteActor(Long id) {
        Actor existingActor = actorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actor with ID " + id + " not found"));
        actorRepository.delete(existingActor);
    }
}
