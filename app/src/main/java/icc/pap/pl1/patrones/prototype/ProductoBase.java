package icc.pap.pl1.patrones.prototype;

import java.math.BigDecimal;

import icc.pap.pl1.patrones.models.Pedido;
import icc.pap.pl1.patrones.models.TipoProducto;

public abstract class ProductoBase implements Pedido, Prototipo<ProductoBase> {
    private String id;
    private TipoProducto tipo;          
    private String descripcion;
    private BigDecimal precio;

    public ProductoBase(String id, TipoProducto tipo, 
        String descripcion, BigDecimal precio) { 
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    @Override
    public String descripcion() {
        return this.descripcion;
    }

    @Override
    public BigDecimal precio() {
        return this.precio;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public TipoProducto tipo() {
        return this.tipo;
    }

    @Override
    public abstract ProductoBase copiar(String nuevoId);
}