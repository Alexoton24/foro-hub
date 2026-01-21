package com.challenge_forhub.foro_hub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;


public record UsuarioDTO(@NotBlank(message = "El nombre no puede ser nulo")
                         String nombre,
                         @Email(message = "Ingrese un email valido") String email,
                         @NotBlank@Pattern(regexp = "\\d{8,}", message = "Contraseña invalida debe tener de 8 caraceres en adelante") String contrasena) {
}
