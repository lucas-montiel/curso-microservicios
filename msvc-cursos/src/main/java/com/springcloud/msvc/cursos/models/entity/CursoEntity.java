package com.springcloud.msvc.cursos.models.entity;

import com.springcloud.msvc.cursos.models.UsuarioModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
@Getter @Setter
public class CursoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nombre;

    @Transient
    private List<UsuarioModel> usuarios;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name ="curso_id")
    private List<CursoUsuarioEntity> cursoUsuarios;

    public CursoEntity(){
        cursoUsuarios = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public void addCursoUsuario(CursoUsuarioEntity cursoUsuario){
        cursoUsuarios.add(cursoUsuario);
    }

    public void removeCursoUsuario(CursoUsuarioEntity cursoUsuario){
        cursoUsuarios.remove(cursoUsuario);
    }

    public void addUsuario(UsuarioModel usuario){
        usuarios.add(usuario);
    }

}
