package com.challenge_forhub.foro_hub.domain.topico;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record DatosActualizaTopico(@NotNull Long id,
        String titulo,
        Categorias categoria,
        String mensaje,
        @JsonProperty("estado_topico") EstadoTopico estadoTopico) {

    public DatosActualizaTopico (Topico topico,Long id){
        this(id,topico.getTitulo(),topico.getCategorias(),topico.getMensaje(), topico.getEstado());
    }
}
