package icc.pap.pl1.patrones.servicio;

import icc.pap.pl1.patrones.models.Pedido;
import icc.pap.pl1.patrones.models.TipoProducto;
import icc.pap.pl1.patrones.prototype.RegistroPrototipos;
import icc.pap.pl1.patrones.decorador.*;

public class ServicioPedido {
    private RegistroPrototipos registro;
    private int contadorPedidos;

    public ServicioPedido() {
        this.registro = new RegistroPrototipos();
        this.contadorPedidos = 1;
    }

    public Pedido crearPedido(TipoProducto tipoProducto) {
        String nuevoId = String.format("P-%03d", contadorPedidos);

        contadorPedidos++;

        return registro.clonar(tipoProducto, nuevoId);
    }

    public Pedido personalizarPedido(Pedido pedido, String ingrediente) throws Exception {
        if (pedido == null) {
            throw new Exception("Error: No hay pedido seleccionado");
        }

        TipoProducto base = pedido.tipo();

        if (base == TipoProducto.CAFE) {
            if (!ingrediente.equals("Azúcar") && !ingrediente.equals("Crema")) {
                throw new Exception("Error: Ingrediente incompatible con Café");
            }
        } else if (base == TipoProducto.ENSALADA) {
            if (!ingrediente.equals("Tomate") && !ingrediente.equals("Pollo")) {
                throw new Exception("Error: Ingrediente incompatible con Ensalada");
            }
        } else if (base == TipoProducto.PIZZA) {
            if (!ingrediente.equals("Queso") && !ingrediente.equals("Pepperoni")) {
                throw new Exception("Error: Ingrediente incompatible con Pizza");
            }
        }

        return decoradorPedido(pedido, ingrediente);
    }

    private Pedido decoradorPedido(Pedido pedido, String ingrediente) {
        if (ingrediente.equals("Azúcar")) {
            return new Azucar(pedido);
        } else if (ingrediente.equals("Crema")) {
            return new Crema(pedido);
        } else if (ingrediente.equals("Tomate")) {
            return new Tomate(pedido);
        } else if (ingrediente.equals("Pollo")) {
            return new Pollo(pedido);
        } else if (ingrediente.equals("Queso")) {
            return new Queso(pedido);
        } else if (ingrediente.equals("Pepperoni")) {
            return new Pepperoni(pedido);
        }
        return pedido;
    }

    public void reiniciarPedidos() {
        this.contadorPedidos = 1;
    }
}