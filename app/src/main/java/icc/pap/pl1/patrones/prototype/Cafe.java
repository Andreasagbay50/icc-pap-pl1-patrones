package icc.pap.pl1.patrones.prototype;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.models.TipoProducto;

public class Cafe extends ProductoBase {

    public Cafe(String idProducto) {
        super(idProducto, TipoProducto.CAFE, "Café", new BigDecimal("2.20"));
    }

    @Override
    public ProductoBase copiar(String nuevoId) {
        return new Cafe(nuevoId);
    }
}