package icc.pap.pl1.patrones.prototype;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.models.TipoProducto;

public class Ensalada extends ProductoBase {

    public Ensalada(String idProducto) {
        super(idProducto, TipoProducto.ENSALADA, "Ensalada sencilla", new BigDecimal("3.00"));
    }

    @Override
    public ProductoBase copiar(String nuevoId) {
        return new Ensalada(nuevoId);
    }
}