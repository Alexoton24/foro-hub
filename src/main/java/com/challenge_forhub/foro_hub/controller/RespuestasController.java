package com.challenge_forhub.foro_hub.controller;

import com.challenge_forhub.foro_hub.domain.respuestas.*;
import com.challenge_forhub.foro_hub.domain.topico.TopicoService;
import com.challenge_forhub.foro_hub.domain.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/respuesta")
public class RespuestasController {

    private final RespuestaService respuestaService;
    private final TopicoService topicoService;
    private final UsuarioService usuarioService;

    @Autowired
    public RespuestasController(RespuestaService respuestaService, TopicoService topicoService,UsuarioService usuarioService) {
        this.respuestaService = respuestaService;
        this.topicoService = topicoService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<MuestraDetallesRespuesta> respuesta(@RequestBody @Valid RespuestaDTO respuestaDTO, UriComponentsBuilder uriComponentsBuilder){

        usuarioService.verificaIdentidad(respuestaDTO.idUsuario());

        Respuesta respuesta = respuestaService.setRespuestaRepository(respuestaDTO);

        var uri = uriComponentsBuilder.path("/respuesta/{id}").buildAndExpand(respuesta.getId()).toUri();

        return ResponseEntity.created(uri).body(new MuestraDetallesRespuesta(respuesta));

    }

    @GetMapping
    public ResponseEntity<Page<MuestraDetallesRespuesta>> muestraRespuestas(@PageableDefault(size = 10)Pageable pageable){

        return ResponseEntity.ok(respuestaService.muestraRespuestas(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity muestraRespuestaEspecifica(@PathVariable Long id){

        return ResponseEntity.ok(respuestaService.muestraRespuestaEspecifica(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity borraRespuesta(@PathVariable Long id){
        usuarioService.verificaIdentidad(id);
        respuestaService.desactivaRespuesta(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity activaRespuesta(@PathVariable Long id){

        usuarioService.verificaIdentidad(id);
        respuestaService.activaRespuesta(id);

        return  ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity actualizaRespuesta(@PathVariable Long id ,@Valid@RequestBody ActualizaRespuesta datos) {
        usuarioService.verificaIdentidad(id);
       Respuesta respuesta = respuestaService.encuentraRespuesta(id);
       if(!respuesta.isActivo()){
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes editar una respuesta eliminada");
       }

        respuestaService.actualizaRespuesta(id, datos);

        return ResponseEntity.noContent().build();
    }
}
