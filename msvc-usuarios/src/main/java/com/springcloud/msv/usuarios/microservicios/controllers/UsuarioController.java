package com.springcloud.msv.usuarios.microservicios.controllers;

import com.springcloud.msv.usuarios.microservicios.models.entity.UsuarioEntity;
import com.springcloud.msv.usuarios.microservicios.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping("/usuarios/lista")
    public ResponseEntity<List<UsuarioEntity>> obtenerLista(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long id){
        Optional<UsuarioEntity> usuarioEntityOptional = service.porId(id);
        if(usuarioEntityOptional.isPresent()){
        return ResponseEntity.ok(usuarioEntityOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/usuarios/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        Optional<UsuarioEntity> o = service.porId(id);
        if(o.isPresent()){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/usuario/crear")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioEntity usuario, BindingResult result){
        if(result.hasErrors()){
            return devolverError(result);
        }
        if(service.porEmail(usuario.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections.singletonMap("Error","Ya existe ese usuario con ese correo electronico!"));
        }

         return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
    }


    @PutMapping("/usuario/modificar/{id}")
    public ResponseEntity<?> modificarUsuario(@Valid @RequestBody UsuarioEntity usuario, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return devolverError(result);
        }
        if(service.porEmail(usuario.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections.singletonMap("Error","Ya existe ese usuario con ese correo electronico!"));
        }

        Optional<UsuarioEntity> o = service.porId(id);
        if(o.isPresent()){
            UsuarioEntity usuarioDB = o.get();
            usuarioDB.setNombre(usuario.getNombre());
            usuarioDB.setEmail(usuario.getEmail());
            usuarioDB.setPassword(usuario.getPassword());

            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuarioDB));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuarios/porIds")
    private ResponseEntity<?> obtenerUsuariosPorIds ( @RequestParam List<Long> ids){
            return ResponseEntity.ok(service.obtenerUsuariosByIds(ids));

    }

    private static ResponseEntity<Map<String, String>> devolverError(BindingResult result) {
        Map<String,String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {errores.put(err.getField(), "El campo "+ err.getField()+ " "+ err.getDefaultMessage() );});
        return ResponseEntity.badRequest().body(errores);
    }


}
