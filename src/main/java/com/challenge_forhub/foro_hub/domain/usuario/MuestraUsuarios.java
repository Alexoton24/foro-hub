package com.challenge_forhub.foro_hub.domain.usuario;

import com.fasterxml.jackson.annotation.JsonAlias;

public record MuestraUsuarios(
        Long id,
                              String nombre,
                              @JsonAlias("total_topicos") Long numeroTopicosPublicados,
                              @JsonAlias("total_Respuestas") Long numeroRespuestas) {

}
