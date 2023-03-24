package com.springcloud.msv.usuarios.microservicios.repositirys;

import com.springcloud.msv.usuarios.microservicios.models.entity.UsuarioEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByEmail(String email);

    //Se puede usar otra forma en la que se le arme una query
    //@Query("select U from UsuarioEntity U where U.email=?1")
    //Optional<UsuarioEntity> porEmail(String email);

    //otra opcion
    //boolean existsByEmail(String email);
}
