package icc.pap.pl1.patrones.decorador;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.models.Pedido;

public class Azucar extends DecoradorIngrediente {

    public Azucar(Pedido pedido) {
        super(pedido);
    }

    @Override
    public String descripcion() {
        return super.descripcion() + " + Azúcar ";
    }

    @Override
    public BigDecimal precio() {
        return super.precio().add(new BigDecimal("0.50"));
    }

    

}
