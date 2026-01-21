package com.challenge_forhub.foro_hub.domain.usuario;

import com.challenge_forhub.foro_hub.domain.respuestas.Respuesta;
import com.challenge_forhub.foro_hub.domain.respuestas.RespuestaRepository;
import com.challenge_forhub.foro_hub.domain.topico.Topico;
import com.challenge_forhub.foro_hub.domain.topico.TopicoRepository;
import com.challenge_forhub.foro_hub.domain.topico.TopicoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    UsuarioRepository usuarioRepository;
    TopicoRepository topicoRepository;
    RespuestaRepository respuestaRepository;



    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, TopicoRepository topicoRepository,RespuestaRepository respuestaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.topicoRepository = topicoRepository;
        this.respuestaRepository = respuestaRepository;
    }

    public Usuario encontrarUsuario(Long id) {

       // return usuarioRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("El ususario con id " + id + " no se encuentra"));
        var usuarioLogeado =  (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioLogeado;
    }

    public void verificaIdentidad(Long idSolicitado){
        var usuarioLogeado = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!usuarioLogeado.getId().equals(idSolicitado)){
            throw new ValidationException("No puedes hacer acciones sobre un perfil que no es tuyo");
        }
    }

    public void validarEstadoUsuario(Usuario usuarioP){
        Usuario usuario = usuarioP;
        if (!usuario.isActivo()){
            throw new ValidationException("Acceso denegado: El usuario debe estar activo para realizar esta acción.");
        }
    }

    public Page<MuestraUsuarios> muestraUsuarios(Pageable pageable) {
//        .map(usuario -> new MuestraUsuarios(
//                usuario.getId(), usuario.isActivo() ? usuario.getNombre() : "Anonimo",
//                usuario.getTopicoList().stream().count(),
//                usuario.getRespuestas().stream().count()
//        ))
        return usuarioRepository.findAllByActivoTrue(pageable).map(u-> new MuestraUsuarios(
                u.getId(),
                u.getNombre(),
                (long) u.getTopicoList().size(),
                (long)u.getRespuestas().size()
        ));
    }

    @Transactional
    public Usuario actualizaUsuario(ActualizaUsuarioDTO actualizaUsuarioDTO){
        Usuario usuario = encontrarUsuario(actualizaUsuarioDTO.id());

        validarEstadoUsuario(usuario);

        usuario.actualizaUsuario(actualizaUsuarioDTO);

        return usuario;
    }

    @Transactional
    public MuestraDetallesUsuario registraUsuario(UsuarioDTO dto) {

        var encriptada = passwordEncoder.encode(dto.contrasena());

        UsuarioDTO usuarioDTO = new UsuarioDTO(dto.nombre(),dto.email(), encriptada);

        Usuario usuario = new Usuario(usuarioDTO);

        usuarioRepository.save(usuario);

        return new MuestraDetallesUsuario(usuario);
    }

  @Transactional
    public void guardaUsuario(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void borraUsuario(Long id){
        Usuario usuario = encontrarUsuario(id);

        usuario.borraUsuario();



        topicoRepository.desactivaTopicosUsuarioAsociado(usuario);
        respuestaRepository.borraRespuestasUsuarioAsociado(usuario);

    }

    @Transactional
    public void activaUsuario(Long id){
        Usuario usuario = encontrarUsuario(id);
        usuario.activaUsuario();
    }

}
