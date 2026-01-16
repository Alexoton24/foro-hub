package com.challenge_forhub.foro_hub.infra.exeptionHandler;

import org.springframework.validation.FieldError;

public record DatosErrorValidacion(String campo , String mensaje) {

    public DatosErrorValidacion (FieldError error){

        this(error.getField(), error.getDefaultMessage());
    }
}
