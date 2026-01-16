package com.challenge_forhub.foro_hub.domain.topico;

import com.challenge_forhub.foro_hub.domain.respuestas.MuestraDetallesRespuesta;

import java.util.List;

public record MuestraTopicoDetallado(MuestraTopicoRegistrado topico,
                                     List<MuestraDetallesRespuesta> respuestas) {


    public MuestraTopicoDetallado (Topico topico){
        this(
                new MuestraTopicoRegistrado(topico) , topico.getRespuestaTopico().stream()
                        .map(MuestraDetallesRespuesta::new).toList()
        );
    }
}
