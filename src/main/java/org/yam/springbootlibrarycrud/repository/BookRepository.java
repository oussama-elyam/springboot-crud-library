package org.yam.springbootlibrarycrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yam.springbootlibrarycrud.entitie.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByName(String name);
}
