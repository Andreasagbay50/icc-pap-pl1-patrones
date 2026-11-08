package icc.pap.pl1.patrones.decorador;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.models.Pedido;
import icc.pap.pl1.patrones.models.TipoProducto;


public abstract class DecoradorIngrediente implements Pedido {
    protected Pedido pedido;

    public DecoradorIngrediente(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String descripcion() {
        if (pedido != null) {
            return pedido.descripcion();
        }
        return "";
    }

    @Override
    public String id() {
        if (pedido != null) {
            return pedido.id();
        }
        return "";
    }

    @Override
    public BigDecimal precio() {
        if (pedido != null) {
            return pedido.precio();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public TipoProducto tipo() {
        if (pedido != null) {
            return pedido.tipo();
        }
        return null;
    }
}