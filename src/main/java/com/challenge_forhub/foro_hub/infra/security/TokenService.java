package com.challenge_forhub.foro_hub.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.challenge_forhub.foro_hub.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${application.security.secret}")
    private String secret;

    public String generaToken(Usuario usuario){

        Algorithm algoritmo = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer("foro-hub")
                .withSubject(usuario.getEmail())
                .withClaim("id" , usuario.getId())
                .withExpiresAt(generaFechaExpiracionToken())
                .sign(algoritmo);

    }

    public Instant generaFechaExpiracionToken(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"));
    }

}
