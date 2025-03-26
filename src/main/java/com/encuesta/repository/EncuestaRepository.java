package com.encuesta.repository;

import com.encuesta.entity.Encuesta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EncuestaRepository extends JpaRepository<Encuesta, Long> {
    List<Encuesta> findByUsuarioId(Long usuarioId);
}
