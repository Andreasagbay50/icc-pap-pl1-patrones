package decorator;
import java.math.BigDecimal;

import models.Pedido;
public class Azucar extends DecoradorIngredientes {
    public Azucar(Pedido pedido) {
        super(pedido);
    }
    @Override 
    public String getDescripcion() { 
        return pedidoEnvuelto.getDescripcion() + ", Azúcar "; 
    }
    @Override public BigDecimal getPrecio() {
         return pedidoEnvuelto.getPrecio().add(BigDecimal.valueOf(0.50)); }
}
