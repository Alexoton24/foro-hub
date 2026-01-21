package com.challenge_forhub.foro_hub.infra.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosAutenticacionUsuario(@Email(message = "Formato de email invalido") String email, @NotBlank(message = "La contraseña no puede estar vacia")
@Pattern(regexp = "\\d{8,}",message = "La contraseña debe tener 8 caracteres en adelante") String contrasena) {
}
