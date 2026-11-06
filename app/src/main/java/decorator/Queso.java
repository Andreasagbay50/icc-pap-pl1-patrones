package decorator;
import models.Pedido;
import java.math.BigDecimal;
public class Queso extends DecoradorIngredientes {
    public Queso(Pedido pedido) {
         super(pedido);
         }
    @Override public String getDescripcion() { 
        return pedidoEnvuelto.getDescripcion() + ", Queso ($1.00)"; 
    }
    @Override public BigDecimal getPrecio() { 
        return pedidoEnvuelto.getPrecio().add(BigDecimal.valueOf(1.00)); 
    }
}