package com.darwin.galindo.agenda25.repo;

import com.darwin.galindo.agenda25.model.Contacto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Integer> {
    @Query("select c from Contacto c where ?1 is null or (?1 is not null and c.nombre like %?1%)")
    Page<Contacto> findByHack(String nombre, Pageable pageable);
}
