package prototype;

import java.math.BigDecimal;

public class Pizza extends ProductoBase {
    public Pizza(String id, String descripcion, double precio) {
        this.id = id;
        this.tipo = "Pizza sencilla";
        this.descripcion = descripcion;
        this.precio = BigDecimal.valueOf(precio);
    }

    @Override
    public ProductoBase copiar(String nuevoId) {
        return new Pizza(nuevoId, this.descripcion, this.precio.doubleValue());
    }
}