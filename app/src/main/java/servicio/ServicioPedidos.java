package servicio;

import decorator.*;
import models.Pedido;
import prototype.*;

import java.util.ArrayList;
import java.util.List;

public class ServicioPedidos {
    private RegistroPrototipos registro = new RegistroPrototipos();
    private List<Pedido> pedidosCreados = new ArrayList<>();
    private int contadorId = 1;

    public ServicioPedidos() {
        registro.registrar("cafe", new Cafe("P-000", "Café", 2.20));
        registro.registrar("ensalada", new Ensalada("P-000", "Ensalada sencilla", 3.00));
        registro.registrar("pizza", new Pizza("P-000", "Pizza sencilla", 5.00));
    }

    public Pedido crearPedidoBase(String claveCatalogo) {
        String nuevoId = String.format("P-%03d", contadorId++);
        Pedido nuevoPedido = registro.clonar(claveCatalogo, nuevoId);
        pedidosCreados.add(nuevoPedido);
        return nuevoPedido;
    }

    public boolean validarCompatibilidad(String tipoProducto, String ingrediente) {
        if (tipoProducto.equalsIgnoreCase("Café")) {
            return ingrediente.equals("Azúcar") || ingrediente.equals("Crema");
        } else if (tipoProducto.equalsIgnoreCase("Ensalada sencilla")) {
            return ingrediente.equals("Tomate") || ingrediente.equals("Pollo");
        } else if (tipoProducto.equalsIgnoreCase("Pizza sencilla")) {
            return ingrediente.equals("Queso") || ingrediente.equals("Pepperoni");
        }
        return false;
    }

    public Pedido agregarIngrediente(Pedido pedidoActual, String nombreIngrediente) {
        if (!validarCompatibilidad(pedidoActual.getTipo(), nombreIngrediente)) {
            throw new IllegalArgumentException("Ingrediente incompatible con el producto seleccionado.");
        }

        Pedido pedidoDecorado;
        switch (nombreIngrediente) {
            case "Azúcar": pedidoDecorado = new Azucar(pedidoActual); break;
            case "Crema": pedidoDecorado = new Crema(pedidoActual); break;
            case "Tomate": pedidoDecorado = new Tomate(pedidoActual); break;
            case "Pollo": pedidoDecorado = new Pollo(pedidoActual); break;
            case "Queso": pedidoDecorado = new Queso(pedidoActual); break;
            case "Pepperoni": pedidoDecorado = new Pepperoni(pedidoActual); break;
            default: throw new IllegalArgumentException("Ingrediente desconocido");
        }

        int index = pedidosCreados.indexOf(pedidoActual);
        if (index != -1) {
            pedidosCreados.set(index, pedidoDecorado);
        }
        return pedidoDecorado;
    }

    public List<Pedido> getPedidosCreados() { return pedidosCreados; }
    
    public void reiniciarPedidos() {
        pedidosCreados.clear();
        contadorId = 1;
    }
}