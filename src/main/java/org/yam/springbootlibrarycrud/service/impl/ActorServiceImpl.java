package org.yam.springbootlibrarycrud.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.yam.springbootlibrarycrud.common.exception.ResourceConflictException;
import org.yam.springbootlibrarycrud.model.Actor;
import org.yam.springbootlibrarycrud.repository.ActorRepository;
import org.yam.springbootlibrarycrud.service.ActorService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Actor createActor(Actor body) {
        if (actorRepository.existsByMail(body.getMail())) {
            throw new ResourceConflictException("Actor mail must be unique. The mail '" + body.getMail() + "' already exists.");
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

    @Override
    @Transactional
    public Actor updateActor(Actor body, Long id) {

        Actor existingActor = actorRepository.findById(id).orElse(null);
        if (existingActor == null) {
            throw new EntityNotFoundException("Actor with ID " + id + " not found");
        }

        modelMapper.map(body, existingActor);

        return actorRepository.save(existingActor);
    }

    @Override
    public void deleteActor(Long id) {
        Optional<Actor> existingActor = actorRepository.findById(id);
        if (existingActor.isEmpty()) {
            throw new EntityNotFoundException("Actor with ID " + id + " not found");
        }
        actorRepository.deleteById(id);
    }
}
