package decorator;
import models.Pedido;
import java.math.BigDecimal;
public class Tomate extends DecoradorIngredientes {
    public Tomate(Pedido pedido) {  
        super(pedido);
    }    @Override public String getDescripcion() { 
        return pedidoEnvuelto.getDescripcion() + ", Tomate ($1.10)";
     }
    @Override public BigDecimal getPrecio() {
         return pedidoEnvuelto.getPrecio().add(BigDecimal.valueOf(1.10)); 
        }
}