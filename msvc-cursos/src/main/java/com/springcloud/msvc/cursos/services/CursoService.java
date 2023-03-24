package com.springcloud.msvc.cursos.services;

import com.springcloud.msvc.cursos.models.UsuarioModel;
import com.springcloud.msvc.cursos.models.entity.CursoEntity;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<CursoEntity> listar();
    Optional<CursoEntity> porId(Long id);
    CursoEntity guardar(CursoEntity curso);
    void eliminar(Long id);

    Optional<UsuarioModel> asignarUsuario(UsuarioModel usuario, Long cursoId);
    Optional<UsuarioModel> crearUsuario(UsuarioModel usuario,  Long cursoId);
    Optional<UsuarioModel> desasignarUsuario(UsuarioModel usuario, Long cursoId);

    void desasignarUsuario(Long id);
}
