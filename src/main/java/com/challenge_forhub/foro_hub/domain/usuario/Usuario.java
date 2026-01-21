package com.challenge_forhub.foro_hub.domain.usuario;

import com.challenge_forhub.foro_hub.domain.respuestas.Respuesta;
import com.challenge_forhub.foro_hub.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@SQLRestriction("activo = true")
@Getter
@Setter
@NoArgsConstructor
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String contrasena;

    @Column(name = "activo")
    private boolean activo;

    @Column(name = "fecha_actualizacion")
    @UpdateTimestamp
    private LocalDateTime fechaActualizacion;

    private  LocalDateTime fecha;


    //Un usuario tiene una lista de respuestas y topicos
    @OneToMany(mappedBy = "usuario")
    private List<Topico> topicoList = new ArrayList<>();

    @OneToMany(mappedBy = "usuario" )
    private List<Respuesta> respuestas = new ArrayList<>();

    public Usuario(UsuarioDTO usuarioDTO) {
        this.contrasena = usuarioDTO.contrasena();
        this.email = usuarioDTO.email();
        this.fecha = LocalDateTime.now();
        this.activo = true;
        this.nombre = usuarioDTO.nombre();
    }

    public void actualizaUsuario(ActualizaUsuarioDTO datos){
        if(datos.nombre() != null){
            this.nombre = datos.nombre();
        }
    }


    public void borraUsuario(){
        this.activo = false;
    }

    public void activaUsuario(){
        this.activo = true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public @Nullable String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
