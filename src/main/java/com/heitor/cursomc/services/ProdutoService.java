package com.heitor.cursomc.services;

import com.heitor.cursomc.domain.Categoria;
import com.heitor.cursomc.domain.Produto;
import com.heitor.cursomc.reposotories.CategoriaRepository;
import com.heitor.cursomc.reposotories.ProdutoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repo;

    private final CategoriaRepository repoCategoria;

    @Autowired
    public ProdutoService(ProdutoRepository repo, CategoriaRepository repoCategoria) {
        this.repo = repo;
        this.repoCategoria = repoCategoria;
    }

    public Produto buscar(Integer idProduto){
        Produto obj = repo.findById(idProduto).orElseThrow(()-> new ObjectNotFoundException("Produto não encontrado pelo id" + idProduto, ProdutoService.class.getName()));
        return obj;
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = repoCategoria.findAllById(ids);
        return repo.search(nome, categorias, pageRequest);
    }
}
