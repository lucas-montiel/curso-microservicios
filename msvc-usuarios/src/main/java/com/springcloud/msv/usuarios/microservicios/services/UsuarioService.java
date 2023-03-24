package com.springcloud.msv.usuarios.microservicios.services;

import com.springcloud.msv.usuarios.microservicios.models.entity.UsuarioEntity;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<UsuarioEntity> listar();
    Optional<UsuarioEntity> porId(Long id);
    UsuarioEntity guardar(UsuarioEntity usuario);
    void eliminar(Long id);

    Optional<UsuarioEntity> porEmail(String email);
    List<UsuarioEntity> obtenerUsuariosByIds (Iterable<Long> ids);


}
