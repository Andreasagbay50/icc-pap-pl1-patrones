package decorator;
import models.Pedido;
import java.math.BigDecimal;
public class Pepperoni extends DecoradorIngredientes {
    public Pepperoni(Pedido pedido) { 
        super(pedido); 
    }
    @Override public String getDescripcion() {
         return pedidoEnvuelto.getDescripcion() + ", Pepperoni ";
         }
    @Override public BigDecimal getPrecio() { 
        return pedidoEnvuelto.getPrecio().add(BigDecimal.valueOf(1.10)); 
    }
}