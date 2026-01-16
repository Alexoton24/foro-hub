package com.challenge_forhub.foro_hub.domain.topico;

public record MuestraDetallesTopico(Categorias categorias,
                                    String mensaje
) {

    public MuestraDetallesTopico(TopicoDTO dto){
        this(
                dto.categorias(),
                dto.mensaje()
        );
    }
}
