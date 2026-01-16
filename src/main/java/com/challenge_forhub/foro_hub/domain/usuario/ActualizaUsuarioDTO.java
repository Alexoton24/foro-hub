package com.challenge_forhub.foro_hub.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActualizaUsuarioDTO(@NotNull(message = "Ingrese un id") Long id,
                                  @NotBlank(message = "No puede tener un nombre vacio") String nombre) {
}
