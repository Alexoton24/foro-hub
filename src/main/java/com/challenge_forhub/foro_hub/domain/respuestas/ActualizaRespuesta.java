package com.challenge_forhub.foro_hub.domain.respuestas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ActualizaRespuesta(
        @NotBlank(message = "No puede haber una respuesta vacia")
        @Size(min = 10, max = 500, message = "Longitud requerida de 10 a 500 caracteres")
        String respuesta) {
}
