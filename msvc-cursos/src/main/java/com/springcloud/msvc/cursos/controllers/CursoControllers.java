package com.springcloud.msvc.cursos.controllers;

import com.springcloud.msvc.cursos.models.UsuarioModel;
import com.springcloud.msvc.cursos.models.entity.CursoEntity;
import com.springcloud.msvc.cursos.models.entity.CursoUsuarioEntity;
import com.springcloud.msvc.cursos.services.CursoServiceImpl;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CursoControllers {

    @Autowired
    private CursoServiceImpl service;

    @GetMapping("/cursos")
    public List<CursoEntity> lista(){
        return service.listar();
    }

    @GetMapping("/cursos/{id}")
    public ResponseEntity<?> curso(@PathVariable Long id){
        Optional<CursoEntity> o = service.porId(id);
        if(o.get() != null){
            return ResponseEntity.ok().body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/cursos/eliminar/{id}")
    public ResponseEntity<?> eliminarCurso( @PathVariable Long id){
         Optional<CursoEntity> o = service.porId(id);
         if(o.isPresent()){
             service.eliminar(id);
             return ResponseEntity.noContent().build();
         }
         return ResponseEntity.notFound().build();
    }

    @PutMapping("/cursos/modificar/{id}")
    public ResponseEntity<?> moficarCurso (@Valid @RequestBody CursoEntity curso, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return devolverError(result);

        }

        Optional<CursoEntity> o = service.porId(id);
        if(o.isPresent()){
            CursoEntity cursoBD = o.get();
            cursoBD.setNombre(curso.getNombre());
            return  ResponseEntity.ok().body(cursoBD);
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping("/cursos/crear")
    public ResponseEntity<?> crear(@Valid @RequestBody CursoEntity nuevoCurso, BindingResult result){
        if(result.hasErrors()){
            return devolverError(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(nuevoCurso));
    }
    @PutMapping("/cursos/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody UsuarioModel usuario, @PathVariable Long cursoId){
        Optional<UsuarioModel> o;
        try{
            o = service.asignarUsuario(usuario, cursoId);
        }catch (FeignException e){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("Error: ", e.getMessage()));
        }

        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cursos/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioModel usuario, @PathVariable Long cursoId){
        Optional<UsuarioModel> o;
        try{
            o = service.crearUsuario(usuario, cursoId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error: ", e.getMessage()));
        }

        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/cursos/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody UsuarioModel usuario, @PathVariable Long cursoId){
        Optional<UsuarioModel> o;
        try{
            o = service.desasignarUsuario(usuario, cursoId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error: ", e.getMessage()));
        }

        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("curso/desasignar-usuario/{id}")
    public void desasignarUsuarioCurso(@PathVariable Long id){
        service.desasignarUsuario(id);
        ResponseEntity.noContent().build();
    }

    private static ResponseEntity<Map<String, String>> devolverError(BindingResult result) {
        Map<String,String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {errores.put(err.getField(), "El campo "+ err.getField()+ " "+ err.getDefaultMessage() );});
        return ResponseEntity.badRequest().body(errores);
    }


}
