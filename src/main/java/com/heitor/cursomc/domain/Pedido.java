package com.heitor.cursomc.domain;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

@Entity
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime instate;

    @OneToOne(mappedBy = "pedido")
    private Pagamento pagamento;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "endereco_de_entrega")
    private Endereco enderecoDeEntrega;


    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemPedido> itens = new HashSet<>();

    public Pedido(){
    }

    public Pedido(Integer id, LocalDateTime instate, Cliente cliente, Endereco enderecoDeEntrega) {
        this.id = id;
        this.instate = instate;
        this.cliente = cliente;
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    public Double getValorTotal(){
        Double valorTotal = 0.0;
        for(ItemPedido x : itens){
            valorTotal += x.getSubTotal();
        }
        return valorTotal;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getInstate() {
        return instate;
    }

    public void setInstate(LocalDateTime instate) {
        this.instate = instate;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEnderecoDeEntrega() {
        return enderecoDeEntrega;
    }

    public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    public Set<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(Set<ItemPedido> itens) {
        this.itens = itens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return id.equals(pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

        sb.append("Pedido número: ").append(id);
        sb.append(", instante: ").append(getInstate().format(formatter));
        sb.append(", cliente: ").append(getCliente().getNome());
        sb.append(", situação pagamento: ").append(getPagamento().getEstado().getDescricao());
        sb.append("\nDetalhes:\n");
        for(ItemPedido x : getItens()){
            sb.append(x.toString());
        }
        sb.append("Valor total: ").append(format.format(getValorTotal()));
        return sb.toString();
    }
}
