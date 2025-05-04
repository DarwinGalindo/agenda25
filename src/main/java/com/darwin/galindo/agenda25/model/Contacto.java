package com.darwin.galindo.agenda25.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String nombre;

    @NotNull
    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaNacimiento;

    @NotBlank
    @Size(max = 15)
    private String celular;

    @NotEmpty
    @Email
    private String email;

    private LocalDateTime fechaRegistro;

    @PrePersist
    void prePersist() {
        fechaRegistro = LocalDateTime.now();
    }

}
