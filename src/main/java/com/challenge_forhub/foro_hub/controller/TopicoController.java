package com.challenge_forhub.foro_hub.controller;

import com.challenge_forhub.foro_hub.domain.topico.*;
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
@RequestMapping("topic")
public class TopicoController {

   private final TopicoService topicoService;

   @Autowired
    public TopicoController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    @PostMapping
    public ResponseEntity guardaPost(@RequestBody @Valid  TopicoDTO topicoDTO, UriComponentsBuilder uriComponentsBuilder) {

       MuestraTopicoRegistrado topicoRegistrado =  topicoService.construyeTopico(topicoDTO);

       var uri = uriComponentsBuilder
               .path("/topic/{id}")
               .buildAndExpand(topicoRegistrado.id())
               .toUri();

        return ResponseEntity.created(uri).body(topicoRegistrado);
    }

    @GetMapping
    public ResponseEntity muestraTopicos(@PageableDefault(size = 10) Pageable pageable){
       Page topicoMostrados = topicoService.muestraTopicos(pageable);
        return ResponseEntity.ok(topicoMostrados);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MuestraTopicoDetallado> muestraTopicoDetallado(@PathVariable Long id){

                Topico topico = topicoService.obtenerPorId(id);

        return ResponseEntity.ok(new MuestraTopicoDetallado(topico));
    }

    @PutMapping
    public ResponseEntity  actualizaTopico(@RequestBody @Valid DatosActualizaTopico datosActualizaTopico) {

       Topico topico = topicoService.actualizaTopico(datosActualizaTopico.id(), datosActualizaTopico);

       topicoService.verificaTopicoNoEstaBorrado(datosActualizaTopico.id());

        if(!topico.getUsuario().isActivo()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se puede actualizar el post de un usuario inactivo ");
        }


        return ResponseEntity.ok(new DatosActualizaTopico(topico, topico.getId()));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity borraPost(@PathVariable Long id){
       topicoService.borraTopico(id);
       return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity activaPost(@PathVariable Long id){
       topicoService.activaTopico(id);
        return ResponseEntity.noContent().build();
    }



}
