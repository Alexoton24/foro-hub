package com.challenge_forhub.foro_hub.domain.topico;

import com.challenge_forhub.foro_hub.domain.respuestas.Respuesta;
import com.challenge_forhub.foro_hub.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record TopicoDTO(@NotNull(message = "Ingrese un id valido")@JsonAlias("id_usuario")Long idUsuario,
                        @NotNull (message = "Categorias invalidas")@JsonAlias("categoria")Categorias categorias,
                        @NotBlank(message = "No puede haber un titulo nulo") String titulo,
                        @NotBlank(message = "No puede haber un mensaje vacio") String mensaje) {
}
