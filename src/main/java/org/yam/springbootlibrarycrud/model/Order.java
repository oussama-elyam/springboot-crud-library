package org.yam.springbootlibrarycrud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app-order") // Change table name to avoid conflicts@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String priceTotal;
    private String qteTotal;
    //1to* OrderItem liste
    // many to 1 user

}