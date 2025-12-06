/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dtos;

/**
 *
 * @author rodol
 */
public class DetalleVentaDTO {
    
    private Integer id;                    
    private Integer ventaId;             
    private Integer productoId;           
    private Integer cantidad;              
    private Double precioUnitario;         

    public DetalleVentaDTO() {
    }

    public DetalleVentaDTO(Integer id, Integer ventaId, Integer productoId, 
                          Integer cantidad, Double precioUnitario) {
        this.id = id;
        this.ventaId = ventaId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public DetalleVentaDTO(Integer ventaId, Integer productoId, 
                          Integer cantidad, Double precioUnitario) {
        this.ventaId = ventaId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public Integer getVentaId() {return ventaId;}
    public void setVentaId(Integer ventaId) {this.ventaId = ventaId;}
    public Integer getProductoId() {return productoId;}
    public void setProductoId(Integer productoId) {this.productoId = productoId;}
    public Integer getCantidad() {return cantidad;}
    public void setCantidad(Integer cantidad) {this.cantidad = cantidad;}
    public Double getPrecioUnitario() {return precioUnitario;}
    public void setPrecioUnitario(Double precioUnitario) {this.precioUnitario = precioUnitario;}

    @Override
    public String toString() {
        return "DetalleVentaDTO{" +
                "id=" + id +
                ", ventaId=" + ventaId +
                ", productoId=" + productoId +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetalleVentaDTO detalleVenta = (DetalleVentaDTO) o;
        return id != null && id.equals(detalleVenta.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id. hashCode() : 0;
    }
}
