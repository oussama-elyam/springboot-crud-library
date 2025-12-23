package org.yam.springbootlibrarycrud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.yam.springbootlibrarycrud.model.enums.ActorRole;

@Entity
@Table(name = "Actor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mail;
    private String password;
    @Enumerated(EnumType.STRING)
    private ActorRole actorRole;
    //1 to many order

}
