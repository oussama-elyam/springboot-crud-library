package org.yam.springbootlibrarycrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yam.springbootlibrarycrud.entitie.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
