package com.springcloud.msvc.cursos.services;

import com.springcloud.msvc.cursos.clients.UsuarioClientRest;
import com.springcloud.msvc.cursos.models.UsuarioModel;
import com.springcloud.msvc.cursos.models.entity.CursoEntity;
import com.springcloud.msvc.cursos.models.entity.CursoUsuarioEntity;
import com.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository repository;
    @Autowired
    private UsuarioClientRest client;

    @Override
    public List<CursoEntity> listar() {
        return (List<CursoEntity>)repository.findAll();
    }

    @Override
    public Optional<CursoEntity> porId(Long id) {
        Optional<CursoEntity> o = repository.findById(id);
            if(!o.get().getCursoUsuarios().isEmpty()){
                List<Long> ids = o.get().getCursoUsuarios().stream().map(cu -> cu.getUsuarioId()).collect(Collectors.toList());
                List<UsuarioModel> usuarios = client.obtenerUsuariosPorIds(ids);
                o.get().setUsuarios(usuarios);
            }
        return o;

    }

    @Override
    @Transactional
    public CursoEntity guardar(CursoEntity curso) {
        return repository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
         repository.deleteById(id);
    }

    @Override
    public Optional<UsuarioModel> asignarUsuario(UsuarioModel usuario, Long cursoId) {
        Optional<CursoEntity> o = repository.findById(cursoId);
        if(o.isPresent()){
            UsuarioModel usuarioMsvc  = client.obtenerUsuario(usuario.getId());

            CursoEntity curso = o.get();
            CursoUsuarioEntity cursoUsuario = new CursoUsuarioEntity();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());
            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMsvc);

        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UsuarioModel> crearUsuario(UsuarioModel usuario, Long cursoId) {
        Optional<CursoEntity> o = repository.findById(cursoId);
        if(o.isPresent()){
            UsuarioModel usuarioMsvc  = client.crear(usuario);

            CursoEntity curso = o.get();
            CursoUsuarioEntity cursoUsuario = new CursoUsuarioEntity();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());
            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMsvc);

        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UsuarioModel> desasignarUsuario(UsuarioModel usuario, Long cursoId) {
        Optional<CursoEntity> o = repository.findById(cursoId);
        if(o.isPresent()){
            UsuarioModel usuarioMsvc  = client.obtenerUsuario(usuario.getId());

            CursoEntity curso = o.get();

            CursoUsuarioEntity cursoUsuario = new CursoUsuarioEntity();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.removeCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMsvc);

        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void desasignarUsuario(Long id) {
        repository.desasignarUsuarioCurso(id);
    }
}
