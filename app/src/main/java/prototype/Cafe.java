package prototype;

import java.math.BigDecimal;

public class Cafe extends ProductoBase {
    public Cafe(String id, String descripcion, double precio) {
        this.id = id;
        this.tipo = "Café";
        this.descripcion = descripcion;
        this.precio = BigDecimal.valueOf(precio);
    }

    @Override
    public ProductoBase copiar(String nuevoId) {
        return new Cafe(nuevoId, this.descripcion, this.precio.doubleValue());
    }
}