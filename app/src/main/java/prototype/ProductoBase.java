package prototype;

import java.math.BigDecimal;

import models.Pedido;

public abstract class ProductoBase implements Pedido, Prototipo<ProductoBase> {
    protected String id;
    protected String tipo;
    protected String descripcion;
    protected BigDecimal precio;

    @Override
    public String getId() { return id; }

    @Override
    public void setId(String id) { this.id = id; }

    @Override
    public String getTipo() { return tipo; }

    @Override
    public String getDescripcion() { return descripcion; }

    @Override
    public BigDecimal getPrecio() { return precio; }
    
    @Override
    public abstract ProductoBase copiar(String nuevoId);
}