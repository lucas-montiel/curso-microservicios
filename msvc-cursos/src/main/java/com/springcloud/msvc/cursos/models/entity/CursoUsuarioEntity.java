package com.springcloud.msvc.cursos.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "curso_usuario")
@Getter @Setter
public class CursoUsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="usuario_id", unique = true)
    private Long usuarioId;

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(!(obj instanceof CursoUsuarioEntity)){
            return false;
        }
        CursoUsuarioEntity o = (CursoUsuarioEntity) obj;
        return this.usuarioId != null && this.usuarioId.equals(o.usuarioId);
    }
}
