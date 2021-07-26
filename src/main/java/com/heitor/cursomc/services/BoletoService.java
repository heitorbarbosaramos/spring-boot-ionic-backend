package com.heitor.cursomc.services;

import com.heitor.cursomc.domain.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class BoletoService {

    public void preenchimentoPagamentoComBoleto(PagamentoComBoleto boleto, LocalDateTime instanteDoPedido){
        instanteDoPedido.plusDays(7);
        boleto.setDataPagamento(instanteDoPedido.toLocalDate());
    }
}
