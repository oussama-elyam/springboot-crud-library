package org.yam.springbootlibrarycrud.entitie;

import jakarta.persistence.*;
import lombok.*;
import org.yam.springbootlibrarycrud.entitie.enums.StatusBook;

@Entity(name = "Book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long price;
    private Integer qte;
    @Enumerated(EnumType.STRING)
    private StatusBook statusBook;

}
