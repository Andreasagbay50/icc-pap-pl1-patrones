package decorator;
import models.Pedido;
import java.math.BigDecimal;
public class Pollo extends DecoradorIngredientes {
    public Pollo(Pedido pedido){
        super(pedido);
    }
    @Override public String getDescripcion() {
         return pedidoEnvuelto.getDescripcion() + ", Pollo ($1.50)"; 
        }
    @Override public BigDecimal getPrecio() {
        return pedidoEnvuelto.getPrecio().add(BigDecimal.valueOf(1.50));
     }
}