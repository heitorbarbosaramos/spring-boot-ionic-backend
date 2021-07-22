package com.heitor.cursomc.enums;

public enum EstadoPagamento {

    PENDENTE(1, "Pendente"),
    QUIADO(2, "Cancelado"),
    CANCELADO(3, "Cancelado");

    private int cod;
    private String descricao;

    private EstadoPagamento(int cod, String descricao){
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static EstadoPagamento toEnum(Integer cod){
        for(EstadoPagamento x : EstadoPagamento.values()){
            if(cod.equals(x.getCod())){
                return x;
            }
        }
        throw new IllegalArgumentException("Codigo tipo pagamento invalido " + cod);
    }
}
