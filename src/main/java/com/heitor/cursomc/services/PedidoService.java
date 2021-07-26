package com.heitor.cursomc.services;

import com.heitor.cursomc.domain.ItemPedido;
import com.heitor.cursomc.domain.PagamentoComBoleto;
import com.heitor.cursomc.domain.Pedido;
import com.heitor.cursomc.enums.EstadoPagamento;
import com.heitor.cursomc.reposotories.PedidoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PedidoService {

    private final PedidoRepository repo;

    private final BoletoService serviceBoleto;

    private final PagamentoService servicePagamento;

    private final ItemPedidoService serviceItemPedido;

    private final ClienteService serviceCliente;

    private final ProdutoService serviceProduto;

    @Autowired
    public PedidoService(PedidoRepository repo, BoletoService serviceBoleto, PagamentoService servicePagamento, ItemPedidoService serviceItemPedido, ClienteService serviceCliente, ProdutoService serviceProduto) {
        this.repo = repo;
        this.serviceBoleto = serviceBoleto;
        this.servicePagamento = servicePagamento;
        this.serviceItemPedido = serviceItemPedido;
        this.serviceCliente = serviceCliente;
        this.serviceProduto = serviceProduto;
    }

    public Pedido buscar(Integer idPedido){
        Pedido obj = repo.findById(idPedido).orElseThrow(()-> new ObjectNotFoundException("Pedido não encontrado pelo id" + idPedido, PedidoService.class.getName()));
        return obj;
    }

    @Transactional
    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstate(LocalDateTime.now());
        obj.setCliente(serviceCliente.buscar(obj.getCliente().getId()));
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            serviceBoleto.preenchimentoPagamentoComBoleto(pagto, obj.getInstate());
        }
        obj = repo.save(obj);
        servicePagamento.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setProduto(serviceProduto.buscar(ip.getProduto().getId()));
            ip.setPreco(ip.getProduto().getPreco());
            ip.setPedido(obj);
        }
        serviceItemPedido.save(obj.getItens());
        return obj;
    }
}
