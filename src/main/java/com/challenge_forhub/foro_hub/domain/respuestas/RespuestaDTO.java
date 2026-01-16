package com.challenge_forhub.foro_hub.domain.respuestas;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RespuestaDTO(
        @JsonAlias("id_usuario") @NotNull Long idUsuario,
        @JsonAlias("id_topico")@NotNull Long idTopico,
        @NotBlank String respuesta
) {
}
