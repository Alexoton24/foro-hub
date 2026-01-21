package com.challenge_forhub.foro_hub.domain.topico;

import com.challenge_forhub.foro_hub.domain.respuestas.Respuesta;
import com.challenge_forhub.foro_hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Topico")
@Table(name = "topicos")
@SQLRestriction("activo = true")
@Getter
@Setter
@NoArgsConstructor
//Entidad topico
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean activo;

    private String titulo;

    //ENUM de las categorias que hay
    @Enumerated(EnumType.STRING)
    private Categorias categorias;

    //Duda
    private String mensaje;

    //Fecha donde se hizo
     private LocalDateTime fecha;

    //Enum para saber si esta respondido o no
    @Enumerated(EnumType.STRING)
    private EstadoTopico estado;

    //Relaciones un topicop tiene muchas rspuestas y un usuario

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "topico",cascade = CascadeType.ALL)
    private List<Respuesta> respuestaTopico =  new ArrayList<>();

    public Topico(TopicoDTO dto) {
        this.activo = true;
        this.titulo = dto.titulo();
        this.categorias = dto.categorias();
        this.mensaje = dto.mensaje();
        this.estado = EstadoTopico.SIN_RESPUESTA;
        this.fecha = LocalDateTime.now();
    }

    public void setRespuesta(Respuesta respuesta){
        this.respuestaTopico.add(respuesta);
        this.estado = EstadoTopico.RESPONDIDO;
    }

    public void actualizaTopico(DatosActualizaTopico datos) {
        if (datos.titulo() != null) {
            this.titulo = datos.titulo();
        }
        if (datos.mensaje() != null) {
            this.mensaje = datos.mensaje();
        }
        if (datos.categoria() != null) {
            this.categorias = datos.categoria();
        }
    }

    public void borraTopico(){
        this.activo = false;
    }

    public void activaTopico(){
        this.activo = true;
    }

}
