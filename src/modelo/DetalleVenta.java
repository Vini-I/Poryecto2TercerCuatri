/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Objects;

/**
 *
 * @author rodol
 */
public class DetalleVenta {

    private Integer id;
    private Venta venta;
    private Producto producto;
    private Integer cantidad;
    private Double precioUnitario;

    public DetalleVenta() {
    }

    public DetalleVenta(Integer id, Venta venta, Producto producto,
            Integer cantidad, Double precioUnitario) {
        this.id = id;
        this.venta = venta;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }
    
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public Venta getVenta() {return venta;}
    public void setVenta(Venta venta) {this.venta = venta;}
    public Producto getProducto() {return producto;}
    public void setProducto(Producto producto) {this.producto = producto;}
    public Integer getCantidad() {return cantidad;}
    public void setCantidad(Integer cantidad) {this.cantidad = cantidad;}
    public Double getPrecioUnitario() {return precioUnitario;}
    public void setPrecioUnitario(Double precioUnitario) {this.precioUnitario = precioUnitario;}
    
    
    public double calcularSubtotal() {
        return cantidad * precioUnitario;
    }

    public String getNombreProducto() {
        return producto != null ? producto.getNombre() : "Producto no asignado";
    }

    public String getCodigoProducto() {
        return producto != null ? producto.getCodigo() : null;
    }

    public void incrementarCantidad() {
        if (this.cantidad != null) {
            this.cantidad++;
        }
    }

    public void disminuirCantidad() {
        if (this.cantidad != null && this.cantidad > 1) {
            this.cantidad--;
        }
    }

    @Override
    public String toString() {
        return String.format("DetalleVenta{id=%d, producto='%s', cantidad=%d, " +
                           "precioUnitario=%. 2f, subtotal=%.2f}",
                id, getNombreProducto(), cantidad, precioUnitario, calcularSubtotal());
    }
}
