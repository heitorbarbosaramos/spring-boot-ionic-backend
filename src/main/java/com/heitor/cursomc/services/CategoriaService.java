package com.heitor.cursomc.services;

import com.heitor.cursomc.domain.Categoria;
import com.heitor.cursomc.reposotories.CategoriaRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repo;

    @Autowired
    public CategoriaService(CategoriaRepository repo) {
        this.repo = repo;
    }

    public Categoria buscar(Integer idCategoria){
        Categoria obj = repo.findById(idCategoria).orElseThrow(()-> new ObjectNotFoundException("Categoria não encontrada", CategoriaService.class.getName()));
        return obj;
    }

    public Categoria insert(Categoria categoria){
        categoria = repo.save(categoria);
        return categoria;
    }

    public Categoria update(Categoria categoria){
        buscar(categoria.getId());
        categoria = repo.save(categoria);
        return categoria;
    }

    public void delete(Integer idCategoria){
        Categoria categoria = buscar(idCategoria);
        try {
            repo.delete(categoria);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Não é possivel excluir categoria com produtos: ");
        }
    }

    public List<Categoria> findAll(){
        List<Categoria> lista = repo.findAll();
        return lista;
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }
}
