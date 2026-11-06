package models;

import java.math.BigDecimal;

public interface Pedido {
    String getId();
    void setId(String id);
    String getTipo();
    String getDescripcion();
    BigDecimal getPrecio();
}