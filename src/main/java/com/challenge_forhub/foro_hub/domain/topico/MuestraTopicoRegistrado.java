package com.challenge_forhub.foro_hub.domain.topico;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record MuestraTopicoRegistrado(@JsonProperty("topico_id") Long id
        ,String usuario,
                                      @JsonProperty("estado") EstadoTopico estadoTopico,
                                      String mensaje,
                                      @JsonProperty("fecha_publicacion") LocalDateTime fechaPublicacion
                            ) {
    public MuestraTopicoRegistrado(Topico topico){
        this(
                topico.getId(), topico.getUsuario().isActivo() ? topico.getUsuario().getNombre() : "Anonimo",topico.getEstado(), topico.getMensaje(), topico.getFecha()
        );
    }
}
