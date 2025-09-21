package org.yam.springbootlibrarycrud.model;

import jakarta.persistence.*;
import lombok.*;

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
    private String price;
    private Long qte;
    @Enumerated(EnumType.STRING)
    private StatusBook statusBook;
    //1to* OrderItem liste

}
