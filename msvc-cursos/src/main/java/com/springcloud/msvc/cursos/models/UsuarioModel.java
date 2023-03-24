package com.springcloud.msvc.cursos.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioModel {
    private Long id;
    private String nombre;

    private String email;
    private String password;
}
