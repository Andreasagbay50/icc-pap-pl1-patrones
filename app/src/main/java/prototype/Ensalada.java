package prototype;

import java.math.BigDecimal;

public class Ensalada extends ProductoBase {
    public Ensalada(String id, String descripcion, double precio) {
        this.id = id;
        this.tipo = "Ensalada sencilla";
        this.descripcion = descripcion;
        this.precio = BigDecimal.valueOf(precio);
    }

    @Override
    public ProductoBase copiar(String nuevoId) {
        return new Ensalada(nuevoId, this.descripcion, this.precio.doubleValue());
    }
}