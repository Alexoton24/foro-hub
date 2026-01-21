package com.challenge_forhub.foro_hub.domain.topico;


import com.challenge_forhub.foro_hub.domain.usuario.Usuario;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findAllByActivoTrue(Pageable page);

    @Modifying
    @Query(" Update Topico t Set t.activo= false Where t.usuario= :usuario ")
    void desactivaTopicosUsuarioAsociado(Usuario usuario);


}
