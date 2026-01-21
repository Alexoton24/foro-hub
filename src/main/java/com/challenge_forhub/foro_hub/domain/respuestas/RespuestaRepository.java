package com.challenge_forhub.foro_hub.domain.respuestas;

import com.challenge_forhub.foro_hub.domain.topico.Topico;
import com.challenge_forhub.foro_hub.domain.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RespuestaRepository extends JpaRepository<Respuesta ,Long> {

    Page<Respuesta> findAllByActivoTrue(Pageable pageable);

    @Modifying
    @Query("Update Respuesta r Set r.activo = false Where r.usuario = :usuario")
    void borraRespuestasUsuarioAsociado(Usuario usuario);

    @Modifying
    @Query("Update Respuesta r Set r.activo = false Where r.topico = :topico")
    void borraRespuestaTopicoAsociado(Topico topico);


}
