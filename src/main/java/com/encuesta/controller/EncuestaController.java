package com.encuesta.controller;

import com.encuesta.entity.Encuesta;
import com.encuesta.entity.Usuario;
import com.encuesta.repository.EncuestaRepository;
import com.encuesta.repository.UsuarioRepository;
import com.encuesta.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/encuestas")
public class EncuestaController {

    @Autowired
    private EncuestaRepository encuestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<Encuesta> getAllEncuestas() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return encuestaRepository.findByUsuarioId(userDetails.getId());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createEncuesta(@RequestBody Encuesta encuesta) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Optional<Usuario> usuario = usuarioRepository.findById(userDetails.getId());
        if (usuario.isPresent()) {
            encuesta.setUsuario(usuario.get());
            encuestaRepository.save(encuesta);
            return ResponseEntity.ok(encuesta);
        } else {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Encuesta> getEncuestaById(@PathVariable Long id) {
        Optional<Encuesta> encuesta = encuestaRepository.findById(id);
        return encuesta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Encuesta> updateEncuesta(@PathVariable Long id, @RequestBody Encuesta encuestaDetails) {
        return encuestaRepository.findById(id).map(encuesta -> {
            encuesta.setPaisResidencia(encuestaDetails.getPaisResidencia());
            encuesta.setNacionalidad(encuestaDetails.getNacionalidad());
            encuesta.setSexo(encuestaDetails.getSexo());
            encuesta.setEdad(encuestaDetails.getEdad());
            encuesta.setViajaCon(encuestaDetails.getViajaCon());
            encuesta.setCantidadPersonas(encuestaDetails.getCantidadPersonas());
            encuesta.setMotivoViaje(encuestaDetails.getMotivoViaje());
            encuesta.setOrganizacionViaje(encuestaDetails.getOrganizacionViaje());
            encuesta.setServiciosPaquete(encuestaDetails.getServiciosPaquete());
            encuesta.setGastosPaquete(encuestaDetails.getGastosPaquete());
            encuesta.setGastosTransporte(encuestaDetails.getGastosTransporte());
            encuesta.setPaisesVisita(encuestaDetails.getPaisesVisita());

            Encuesta updatedEncuesta = encuestaRepository.save(encuesta);
            return ResponseEntity.ok(updatedEncuesta);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteEncuesta(@PathVariable Long id) {
        return encuestaRepository.findById(id).map(encuesta -> {
            encuestaRepository.delete(encuesta);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
