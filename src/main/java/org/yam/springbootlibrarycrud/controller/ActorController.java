package org.yam.springbootlibrarycrud.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yam.springbootlibrarycrud.dto.ActorDto;
import org.yam.springbootlibrarycrud.model.Actor;
import org.yam.springbootlibrarycrud.service.ActorService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/actor")
@RestController
public class ActorController {

    private final ActorService actorService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ActorDto> createActor(@Valid @RequestBody ActorDto body) {

        Actor newActor = modelMapper.map(body, Actor.class);
        Actor savedActor = actorService.createActor(newActor);
        ActorDto response = modelMapper.map(savedActor, ActorDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/paggination")
    public ResponseEntity<List<Actor>> getActors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Actor> Actors = actorService.getActors(page, size);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Actors.getContent());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Long id) {
        actorService.deleteActor(id);
        return ResponseEntity.status(HttpStatus.OK).body("Actor deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateActor(@RequestBody ActorDto body, @PathVariable("id") Long id) {

        Actor existingActor = modelMapper.map(body, Actor.class);
        existingActor.setId(id); // Ensure the id remains unchanged
        Actor updatedActor = actorService.updateActor(existingActor, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedActor);
    }
}
