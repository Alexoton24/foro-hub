package com.challenge_forhub.foro_hub.domain.usuario;

import com.challenge_forhub.foro_hub.domain.respuestas.MuestraDetallesRespuesta;
import com.challenge_forhub.foro_hub.domain.topico.MuestraTopicoRegistrado;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MuestraUsuariosEspecificos(
        String nombre,
        @JsonProperty("topicos") List<MuestraTopicoRegistrado> topicosPublicados,
        @JsonProperty("rspuestas")List<MuestraDetallesRespuesta> respuestasPublicadas) {

    public MuestraUsuariosEspecificos (Usuario usuario){
        this(
              usuario.isActivo() ? usuario.getNombre() : "Anonimo",
                usuario.getTopicoList().stream().map(t-> new MuestraTopicoRegistrado(t)).toList()
                ,usuario.getRespuestas().stream().map(MuestraDetallesRespuesta::new).toList()
        );
    }
}
