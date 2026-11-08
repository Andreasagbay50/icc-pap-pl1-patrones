package icc.pap.pl1.patrones.prototype;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.models.TipoProducto;

public class Pizza extends ProductoBase {

    public Pizza(String idProducto) {
        super(idProducto, TipoProducto.PIZZA, "Pizza sencilla", new BigDecimal("5.00"));
    }

    @Override
    public ProductoBase copiar(String nuevoId) {
        return new Pizza(nuevoId);
    }
}