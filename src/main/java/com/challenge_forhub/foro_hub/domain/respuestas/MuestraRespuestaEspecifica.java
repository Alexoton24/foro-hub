package com.challenge_forhub.foro_hub.domain.respuestas;

public record MuestraRespuestaEspecifica(String autor,
                                         Long idTopico,
                                         String respuesta) {
    public MuestraRespuestaEspecifica(Respuesta respuesta) {
        this(
                respuesta.getUsuario().getNombre(),
                respuesta.getTopico().getId(),
                respuesta.getRespuesta()
        );
    }
}
