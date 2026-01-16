package com.challenge_forhub.foro_hub.domain.respuestas;

import com.challenge_forhub.foro_hub.domain.topico.Topico;
import com.challenge_forhub.foro_hub.domain.usuario.Usuario;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Embeddable
public class Respuestas {

    Long id;

    LocalDate fecha;

    @ManyToOne
    Usuario usuario;

}
