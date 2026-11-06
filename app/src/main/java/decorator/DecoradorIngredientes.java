package decorator;

import java.math.BigDecimal;

import models.Pedido;

public abstract class DecoradorIngredientes implements Pedido {
    protected Pedido pedidoEnvuelto;

    public DecoradorIngredientes(Pedido pedidoEnvuelto) {
        this.pedidoEnvuelto = pedidoEnvuelto;
    }

    @Override
    public String getId() { return pedidoEnvuelto.getId(); }

    @Override
    public void setId(String id) { pedidoEnvuelto.setId(id); }

    @Override
    public String getTipo() { return pedidoEnvuelto.getTipo(); }

    @Override
    public String getDescripcion() { return pedidoEnvuelto.getDescripcion(); }

    @Override
    public BigDecimal getPrecio() { return pedidoEnvuelto.getPrecio(); }
}