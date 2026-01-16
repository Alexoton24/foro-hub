package com.challenge_forhub.foro_hub.domain.respuestas;

import com.challenge_forhub.foro_hub.domain.topico.Topico;
import com.challenge_forhub.foro_hub.domain.topico.TopicoService;
import com.challenge_forhub.foro_hub.domain.usuario.Usuario;
import com.challenge_forhub.foro_hub.domain.usuario.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;
    private final TopicoService topicoService;
    private final UsuarioService usuarioService;

    @Autowired
    public RespuestaService(RespuestaRepository respuestaRepository, TopicoService topicoService,UsuarioService usuarioService) {
        this.respuestaRepository = respuestaRepository;
        this.topicoService = topicoService;
        this.usuarioService = usuarioService;
    }

    public Respuesta encuentraRespuesta (Long id){
        Respuesta respuesta = respuestaRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Recurso no encontrado"));
        return respuesta;
    }




    @Transactional
    public Respuesta setRespuestaRepository(RespuestaDTO dto){

        Topico topico =  topicoService.obtenerPorId(dto.idTopico());

        topicoService.verificaTopicoNoEstaBorrado(dto.idTopico());

        Usuario usuario = usuarioService.encontrarUsuario(dto.idUsuario());
        Respuesta respuesta = new Respuesta(dto);


        usuarioService.validarEstadoUsuario(usuario);


        topico.setRespuesta(respuesta);
        respuesta.setTopico(topico);
        usuario.getRespuestas().add(respuesta);
        respuesta.setUsuario(usuario);

        respuestaRepository.save(respuesta);

        return respuesta;

    }



    public Page<MuestraDetallesRespuesta> muestraRespuestas(Pageable pageable){
       Page<Respuesta> respuesta =  respuestaRepository.findAllByActivoTrue(pageable);

        return respuesta.map(MuestraDetallesRespuesta::new);
    }


    public MuestraRespuestaEspecifica muestraRespuestaEspecifica(Long id){
        Respuesta respuesta = encuentraRespuesta(id);
        return new MuestraRespuestaEspecifica(respuesta);
    }

    @Transactional
    public void desactivaRespuesta(Long id){
        Respuesta respuesta = encuentraRespuesta(id);
        respuesta.borraRespuesta();
    }

    @Transactional
    public void activaRespuesta(Long id){
        Respuesta respuesta = encuentraRespuesta(id);
        respuesta.activaRespuesta();
    }

    @Transactional
    public void actualizaRespuesta(Long id, ActualizaRespuesta datos){
        Respuesta respuesta = encuentraRespuesta(id);
        respuesta.actualizaRepuesta(datos);
    }

}
