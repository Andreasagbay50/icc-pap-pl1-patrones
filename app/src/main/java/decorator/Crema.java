package decorator;
import models.Pedido;
import java.math.BigDecimal;
public class Crema extends DecoradorIngredientes {
    public Crema(Pedido pedido) {
        super(pedido);
    }
    
    @Override public String getDescripcion() {
         return pedidoEnvuelto.getDescripcion() + ", Crema ";
          }
    @Override public BigDecimal getPrecio() {
         return pedidoEnvuelto.getPrecio().add(BigDecimal.valueOf(1.20)); 
        }
}