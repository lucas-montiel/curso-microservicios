package com.springcloud.msv.usuarios.microservicios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="msvc-cursos", url="localhost:8002")
public interface CursoClientRest {
    @DeleteMapping("curso/desasignar-usuario/{id}")
    void desasignarUsuarioCurso(@PathVariable Long id);
}
