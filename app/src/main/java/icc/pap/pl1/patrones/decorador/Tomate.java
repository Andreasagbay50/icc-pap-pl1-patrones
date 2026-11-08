package icc.pap.pl1.patrones.decorador;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.models.Pedido;

public class Tomate extends DecoradorIngrediente {

    public Tomate(Pedido pedido) {
        super(pedido);
    }

    @Override
    public String descripcion() {
        return super.descripcion() + "+ Tomate ";
    }

    @Override
    public BigDecimal precio() {
        return super.precio().add(new BigDecimal("1.10"));
    }

}
