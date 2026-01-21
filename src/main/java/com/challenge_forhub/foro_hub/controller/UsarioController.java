package com.challenge_forhub.foro_hub.controller;

import com.challenge_forhub.foro_hub.domain.usuario.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping( "/usuario")
public class UsarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity creaUsuario(@RequestBody @Valid UsuarioDTO datos, UriComponentsBuilder uriComponentsBuilder){

        MuestraDetallesUsuario detallesUsuario =  usuarioService.registraUsuario(datos);

        var uri = uriComponentsBuilder.path("/usuario/{id}").buildAndExpand(detallesUsuario.id()).toUri();

        return ResponseEntity.created(uri).body(detallesUsuario);
    }

    @GetMapping
    public ResponseEntity<Page<MuestraUsuarios>> muestraUsuarios(@PageableDefault(size = 10) Pageable pageable){

        return ResponseEntity.ok(usuarioService.muestraUsuarios(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity <MuestraUsuariosEspecificos> muestraUsuariosEspecifcos(@PathVariable Long id){
        Usuario usuario = usuarioService.encontrarUsuario(id);
        return ResponseEntity.ok(new MuestraUsuariosEspecificos(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity borraUsuario(@PathVariable Long id){
        usuarioService.verificaIdentidad(id);

        usuarioService.borraUsuario(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/activar")
    public ResponseEntity activaUsuario(@PathVariable Long id){

        usuarioService.verificaIdentidad(id);

        usuarioService.activaUsuario(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActualizaUsuarioDTO> actualizaUsuario(@PathVariable Long id, @Valid @RequestBody ActualizaUsuarioDTO actualizaUsuarioDTO){

        usuarioService.verificaIdentidad(id);

       usuarioService.actualizaUsuario(actualizaUsuarioDTO);

        return ResponseEntity.ok(new ActualizaUsuarioDTO(id, actualizaUsuarioDTO.nombre()));
    }




}
