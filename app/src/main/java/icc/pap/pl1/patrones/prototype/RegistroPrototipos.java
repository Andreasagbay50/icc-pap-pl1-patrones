package icc.pap.pl1.patrones.prototype;

import java.util.HashMap;
import java.util.Map;

import icc.pap.pl1.patrones.models.Pedido;
import icc.pap.pl1.patrones.models.TipoProducto;

public class RegistroPrototipos {
    private final Map<TipoProducto, ProductoBase> prototipos = new HashMap<>();

    public RegistroPrototipos() {
        prototipos.put(TipoProducto.CAFE, new Cafe("PROTOTIPO"));
        prototipos.put(TipoProducto.ENSALADA, new Ensalada("PROTOTIPO"));
        prototipos.put(TipoProducto.PIZZA, new Pizza("PROTOTIPO"));
    }

    public Pedido clonar(TipoProducto tipo, String nuevoId) {
        ProductoBase pedidoduplicado = prototipos.get(tipo);

        if (pedidoduplicado != null) {
            return pedidoduplicado.copiar(nuevoId);
        }
        return null;
    }
}