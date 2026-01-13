package com.challenge_forhub.foro_hub.domain.topico;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@Setter
@NoArgsConstructor
public class Topico {
    /*
status (estado del tópico)
autor
curso
     */

    @Id
    private Long id;

    private String titulo;

    private String mensaje;

    LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private EstadoTopico estado;



}
