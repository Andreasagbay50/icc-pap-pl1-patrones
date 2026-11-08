package icc.pap.pl1.patrones.models;

import java.math.BigDecimal;

public interface Pedido {
    String descripcion();
    BigDecimal precio();
    String id ();
    TipoProducto tipo();
}