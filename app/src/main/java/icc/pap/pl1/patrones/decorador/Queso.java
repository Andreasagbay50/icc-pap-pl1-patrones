package icc.pap.pl1.patrones.decorador;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.models.Pedido;

public class Queso extends DecoradorIngrediente {

    public Queso(Pedido pedido) {
        super(pedido);
    }

    @Override
    public String descripcion() {
        return super.descripcion() + "+ Queso ";
    }

    @Override
    public BigDecimal precio() {
        return super.precio().add(new BigDecimal("1.00"));
    }

}
