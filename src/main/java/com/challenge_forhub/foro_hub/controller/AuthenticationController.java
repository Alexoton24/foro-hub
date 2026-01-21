package com.challenge_forhub.foro_hub.controller;


import com.challenge_forhub.foro_hub.domain.usuario.Usuario;
import com.challenge_forhub.foro_hub.infra.security.DatosAutenticacionUsuario;
import com.challenge_forhub.foro_hub.infra.security.DatosJWT;
import com.challenge_forhub.foro_hub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @PostMapping
    public ResponseEntity logueaUsuario(@RequestBody@Valid DatosAutenticacionUsuario datos){

        Authentication authentication = new UsernamePasswordAuthenticationToken(datos.email(), datos.contrasena());

        var usuarioAutenticado = authenticationManager.authenticate(authentication);

        var usuario =  (Usuario)usuarioAutenticado.getPrincipal();

        var tokenJWT = tokenService.generaToken(usuario);

        return ResponseEntity.ok(new DatosJWT(tokenJWT));
    }

}
