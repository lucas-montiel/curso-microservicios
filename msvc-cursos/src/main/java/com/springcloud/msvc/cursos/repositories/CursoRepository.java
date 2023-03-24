package com.springcloud.msvc.cursos.repositories;

import com.springcloud.msvc.cursos.models.entity.CursoEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepository extends CrudRepository<CursoEntity, Long> {

    @Modifying
    @Query("delete from CursoUsuarioEntity cu where cu.usuarioId=?1")
    void desasignarUsuarioCurso(Long id);


}
