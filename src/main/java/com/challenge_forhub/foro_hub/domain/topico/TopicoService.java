package com.challenge_forhub.foro_hub.domain.topico;

import com.challenge_forhub.foro_hub.domain.respuestas.RespuestaRepository;
import com.challenge_forhub.foro_hub.domain.usuario.Usuario;
import com.challenge_forhub.foro_hub.domain.usuario.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final RespuestaRepository respuestaRepository;
    private final UsuarioService usuarioService;



    @Autowired
    public TopicoService(TopicoRepository topicoRepository,  UsuarioService usuarioService, RespuestaRepository respuestaRepository) {
        this.topicoRepository = topicoRepository;
        this.usuarioService = usuarioService;
        this.respuestaRepository = respuestaRepository;
    }

    public  Topico obtenerPorId(Long id) {
        return topicoRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Topico con id " + id + " no encontrado"));
    }

    public void verificaTopicoNoEstaBorrado(Long id){
        Topico topico = obtenerPorId(id);
        if(!topico.isActivo()){
            throw new ValidationException("No se pueden responder a topicos borrados");
        }
    }


    @Transactional
    public MuestraTopicoRegistrado construyeTopico(TopicoDTO dto){

        var authentication= SecurityContextHolder.getContext().getAuthentication();

        Usuario usuarioLogeado =  (Usuario) authentication.getPrincipal();

        Usuario usuario = usuarioService.encontrarUsuario(usuarioLogeado.getId());

            usuarioService.validarEstadoUsuario(usuario);

            Topico topico = new Topico(dto);
            topico.setUsuario(usuario);

            topicoRepository.save(topico);

            return new MuestraTopicoRegistrado(topico);


    }


    public Page<MuestraTopicoRegistrado> muestraTopicos(Pageable page){

        return  topicoRepository.findAllByActivoTrue(page).map( MuestraTopicoRegistrado::new);
    }

    public MuestraTopicoDetallado muestraTopicoDetallado (Topico topico){
        return new MuestraTopicoDetallado(topico);
    }

    @Transactional
    public Topico actualizaTopico(Long id, DatosActualizaTopico datos){

       Topico topico = obtenerPorId(id);

        topico.actualizaTopico(datos);

        return topico;

    }

    @Transactional
    public void borraTopico(Long id){
        Topico topico = obtenerPorId(id);
        topico.borraTopico();
        respuestaRepository.borraRespuestaTopicoAsociado(topico);
    }

    @Transactional
    public void activaTopico(Long id){
        Topico topico = obtenerPorId(id);
        topico.activaTopico();
    }

}
