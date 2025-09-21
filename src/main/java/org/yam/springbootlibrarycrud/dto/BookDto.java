package org.yam.springbootlibrarycrud.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.yam.springbootlibrarycrud.model.StatusBook;

@Data
public class BookDto {
    private Long id;

    @NotBlank(message = "Name shouldn't be NULL or EMPTY")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters long")
    private String name;

    @NotBlank(message = "price shouldn't be NULL or EMPTY")
    private String price;

    @NotNull(message = "QTE shouldn't be NULL")
    private Long qte;

    @Enumerated(EnumType.STRING)
    private StatusBook statusBook;
}
