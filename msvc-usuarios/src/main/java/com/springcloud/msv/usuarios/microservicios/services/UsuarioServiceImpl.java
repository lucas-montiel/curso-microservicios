package com.springcloud.msv.usuarios.microservicios.services;

import com.springcloud.msv.usuarios.microservicios.client.CursoClientRest;
import com.springcloud.msv.usuarios.microservicios.models.entity.UsuarioEntity;
import com.springcloud.msv.usuarios.microservicios.repositirys.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private CursoClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioEntity> listar() {
        return (List<UsuarioEntity>) repository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioEntity> porId(Long id) {
        return repository.findById(id);
    }
    @Override
    @Transactional
    public UsuarioEntity guardar(UsuarioEntity usuario) {
        return repository.save(usuario);
    }
    @Override
    public void eliminar(Long id) {
          client.desasignarUsuarioCurso(id);
          repository.deleteById(id);
    }

    @Override
    public Optional<UsuarioEntity> porEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public List<UsuarioEntity> obtenerUsuariosByIds(Iterable<Long> ids) {
        return (List<UsuarioEntity>) repository.findAllById(ids);
    }



}
