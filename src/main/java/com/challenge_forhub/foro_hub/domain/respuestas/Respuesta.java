package com.challenge_forhub.foro_hub.domain.respuestas;

import com.challenge_forhub.foro_hub.domain.topico.Topico;
import com.challenge_forhub.foro_hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity(name = "Respuesta")
@Table(name = "respuestas")
@SQLRestriction("activo = true")
@Getter
@Setter
@NoArgsConstructor
public class Respuesta {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean activo;

    private LocalDateTime fecha;

    @Column(name = "fecha_actualizacion")
    @UpdateTimestamp
    private LocalDateTime fechaActualizacion;

    private String respuesta;

    //Muchas repuestas apuntan a un usuario y tambien con topico
    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Topico topico;

    public Respuesta(RespuestaDTO dto) {
        this.activo = true;
        this.respuesta = dto.respuesta();
        this.fecha = LocalDateTime.now();
    }

    public void borraRespuesta(){
        this.activo = false;
    }

    public void activaRespuesta(){
        this.activo = true;
    }

    public void actualizaRepuesta(ActualizaRespuesta actualizaRespuesta){
        if(actualizaRespuesta != null){
            this.respuesta = actualizaRespuesta.respuesta();
        }
    }

}
