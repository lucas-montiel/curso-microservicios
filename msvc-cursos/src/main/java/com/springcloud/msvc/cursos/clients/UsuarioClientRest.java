package com.springcloud.msvc.cursos.clients;

import com.springcloud.msvc.cursos.models.UsuarioModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//Dentro de feignClient agregamos el nombre del microservicio que nos vamos a comunicar pero el nombre esta en su properties y la url
@FeignClient(name="msvc-usuarios", url="localhost:8001")
public interface UsuarioClientRest {

    @GetMapping("/usuarios/{id}")
    UsuarioModel obtenerUsuario(@PathVariable Long id);

    @PostMapping("/usuario/crear")
    UsuarioModel crear(@RequestBody UsuarioModel usuario);

    @GetMapping("/usuarios/porIds")
    List<UsuarioModel> obtenerUsuariosPorIds(@RequestParam Iterable<Long> ids);

}
