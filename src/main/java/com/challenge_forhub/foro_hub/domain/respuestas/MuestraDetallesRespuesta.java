package com.challenge_forhub.foro_hub.domain.respuestas;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record MuestraDetallesRespuesta(
        Long id
        ,@JsonProperty("id_topico") Long idTopico,
                                        String autor,
                                       String respuesta,
                                       LocalDateTime fecha) {

    public MuestraDetallesRespuesta(Respuesta respuesta){
        this(
                respuesta.getId(), respuesta.getTopico().getId(),respuesta.getUsuario().isActivo() ? respuesta.getUsuario().getNombre() : "Anonimo", respuesta.getRespuesta(),respuesta.getFecha()
        );
    }
}
