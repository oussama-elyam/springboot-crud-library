package org.yam.springbootlibrarycrud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Order_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
    public class OrderItem  {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private Integer quantity;
    private Long bookPrice;    // snapshot
    private Double discount;   // snapshot
    private Long priceTotal;   // calculated
}
