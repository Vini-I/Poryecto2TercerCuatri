/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import Utils.UtilsDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author rodol
 */
public class Venta {

    private Integer id;
    private Cliente cliente;
    private LocalDateTime fecha;
    private EstadoVentaEnum estado;
    private List<DetalleVenta> detalles;

    public Venta() {
        this.detalles = new ArrayList<>();
    }

    public Venta(Integer id, Cliente cliente, LocalDateTime fecha, EstadoVentaEnum estado) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = fecha;
        this.estado = estado;
        this.detalles = new ArrayList<>();
    }
    
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public Cliente getCliente() {return cliente;}
    public void setCliente(Cliente cliente) {this.cliente = cliente;}
    public LocalDateTime getFecha() {return fecha;}
    public void setFecha(LocalDateTime fecha) {this.fecha = fecha;}
    public EstadoVentaEnum getEstado() {return estado;}
    public void setEstado(EstadoVentaEnum estado) {this.estado = estado;}
    public List<DetalleVenta> getDetalles() {return detalles;}
    public void setDetalles(List<DetalleVenta> detalles) {this.detalles = new ArrayList<>(detalles);}
    
    public void agregarDetalle(DetalleVenta detalle) {
        if (detalle == null) {
            throw new IllegalArgumentException("El detalle no puede ser nulo");
        }
        detalle.setVenta(this);
        this.detalles.add(detalle);
    }

    public void eliminarDetalle(DetalleVenta detalle) {
        this.detalles.remove(detalle);
    }

    public void eliminarDetallePorIndice(int indice) {
        if (indice >= 0 && indice < detalles.size()) {
            detalles.remove(indice);
        }
    }

    public void limpiarDetalles() {
        this.detalles.clear();
    }

    public double calcularSubtotal() {
        return detalles.stream().mapToDouble(DetalleVenta::calcularSubtotal)
                .sum();
    }

    public double calcularImpuesto() {
        return calcularSubtotal() * 0.18;
    }

    public double calcularTotal() {
        return calcularSubtotal() + calcularImpuesto();
    }

public int obtenerCantidadTotalProductos() {
        return detalles.stream()
                .mapToInt(DetalleVenta::getCantidad)
                .sum();
    }
     
     public int obtenerCantidadLineas() {
        return detalles.size();
    }
     
       public boolean tieneDetalles() {
        return !detalles.isEmpty();
    }
       
       public boolean estaCompletada() {
        return estado != null && estado.esCompletada();
    }

    public boolean estaAnulada() {
        return estado != null && estado.esAnulada();
    }
    
    public boolean estaPendiente() {
        return estado != null && estado.esPendiente();
    }

    public void anular() {
        if (estaAnulada()) {
            throw new IllegalStateException("La venta ya está anulada");
        }
        if (estaCompletada()){
            throw new IllegalStateException("No puede anular una venta completada");
        }
        this.estado = EstadoVentaEnum.ANULADA;
    }

  
    public void completar() {
        if (estaCompletada()) {
            throw new IllegalStateException("La venta ya está completada");
        }
        if (!tieneDetalles()) {
            throw new IllegalStateException("No se puede completar una venta sin detalles");
        }
        this.estado = EstadoVentaEnum.COMPLETADA;
    }

    public boolean puedeModificarse() {
        return estaPendiente();
    }

    public String getNombreCliente() {
        return cliente != null ? cliente.getNombreCompleto() : "Cliente no asignado";
    }

    public String getFechaFormateada() {
        return UtilsDate.formatDate(fecha);
    }

    public boolean esNueva() {
        return this.id == null;
    }

    public String getEstadoDescripcion() {
        return estado != null ? estado.getDescripcion() : "Sin estado";
    }

    @Override
    public String toString() {
        return String.format("Venta{id=%d, cliente='%s', fecha=%s, estado=%s, " +
                           "detalles=%d, subtotal=%.2f, impuesto=%.2f, total=%.2f}",
                id, getNombreCliente(), getFechaFormateada(), estado,
                obtenerCantidadLineas(), calcularSubtotal(), 
                calcularImpuesto(), calcularTotal());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venta venta = (Venta) o;
        return id != null && id.equals(venta.id);
    }

    @Override
    public int hashCode() {
        return Objects. hash(id);
    }

}
