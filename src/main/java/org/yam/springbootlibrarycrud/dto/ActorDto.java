package org.yam.springbootlibrarycrud.dto;

import lombok.Data;
import org.yam.springbootlibrarycrud.entitie.enums.ActorRole;

@Data
public class ActorDto {
    private String mail;
    private String password;
    private Enum<ActorRole> actorRole;
}