package com.heitor.cursomc.services;

import com.heitor.cursomc.domain.Cliente;
import com.heitor.cursomc.domain.ItemPedido;
import com.heitor.cursomc.domain.PagamentoComBoleto;
import com.heitor.cursomc.domain.Pedido;
import com.heitor.cursomc.enums.EstadoPagamento;
import com.heitor.cursomc.reposotories.PedidoRepository;
import com.heitor.cursomc.security.UserSecuritySpring;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AuthorizationServiceException;
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

    private final EmailService serviceEmail;

    @Autowired
    public PedidoService(PedidoRepository repo, BoletoService serviceBoleto, PagamentoService servicePagamento, ItemPedidoService serviceItemPedido, ClienteService serviceCliente, ProdutoService serviceProduto, EmailService serviceEmail) {
        this.repo = repo;
        this.serviceBoleto = serviceBoleto;
        this.servicePagamento = servicePagamento;
        this.serviceItemPedido = serviceItemPedido;
        this.serviceCliente = serviceCliente;
        this.serviceProduto = serviceProduto;
        this.serviceEmail = serviceEmail;
    }

    public Pedido buscar(Integer idPedido){
        Pedido obj = repo.findById(idPedido).orElseThrow(()-> new ObjectNotFoundException("Pedido não encontrado pelo id" + idPedido, PedidoService.class.getName()));
        return obj;
    }

    @Transactional
    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(LocalDateTime.now());
        obj.setCliente(serviceCliente.buscar(obj.getCliente().getId()));
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            serviceBoleto.preenchimentoPagamentoComBoleto(pagto, obj.getInstante());
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
        serviceEmail.sendOrderConfirmationHtmlEmail(obj);
        return obj;
    }

    public Page<Pedido> findPage(Integer pages, Integer linesPerPage, String orderBy, String direction){

        UserSecuritySpring user = UserService.authenticated();
        if(user == null){
            throw new AuthorizationServiceException("Acesso negado");
        }

        PageRequest pageRequest = PageRequest.of(pages, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Cliente cliente = serviceCliente.buscar(user.getId());
        return repo.findByCliente(cliente, pageRequest);
    }
}
