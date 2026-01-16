package com.challenge_forhub.foro_hub.domain.usuario;

public record MuestraDetallesUsuario(Long id,
                                     String nombre) {

    public MuestraDetallesUsuario(Usuario usuario) {
        this(

                usuario.getId(), usuario.isActivo() ? usuario.getNombre() : "Anonimo"
        );
    }
}
