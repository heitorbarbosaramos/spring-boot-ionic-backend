package com.heitor.cursomc.services;

import com.heitor.cursomc.domain.Pagamento;
import com.heitor.cursomc.reposotories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    private final PagamentoRepository repo;

    @Autowired
    public PagamentoService(PagamentoRepository repo) {
        this.repo = repo;
    }

    public Pagamento save(Pagamento pagamento){
        pagamento = repo.save(pagamento);
        return pagamento;
    }
}
