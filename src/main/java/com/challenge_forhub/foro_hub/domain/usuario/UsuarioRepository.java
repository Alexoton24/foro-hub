package com.challenge_forhub.foro_hub.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario , Long> {
    Page<Usuario> findAllByActivoTrue(Pageable pageable);

    UserDetails findByEmail(String email);

}
