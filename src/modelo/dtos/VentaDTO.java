/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dtos;

import java.time.LocalDateTime;

/**
 *
 * @author rodol
 */
public class VentaDTO {

    private Integer id;
    private String clienteId;
    private LocalDateTime fecha;
    private String estado;

    public VentaDTO() {
    }

    public VentaDTO(Integer id, String clienteId, LocalDateTime fecha, String estado) {
        this.id = id;
        this.clienteId = clienteId;
        this.fecha = fecha;
        this.estado = estado;
    }

    public VentaDTO(String clienteId, LocalDateTime fecha, String estado) {
        this.clienteId = clienteId;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public String getClienteId() {return clienteId;}
    public void setClienteId(String clienteId) {this.clienteId = clienteId;}
    public LocalDateTime getFecha() {return fecha;}
    public void setFecha(LocalDateTime fecha) {this.fecha = fecha;}
    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}

    @Override
    public String toString() {
        return "VentaDTO{"
                + "id=" + id
                + ", clienteId=" + clienteId
                + ", fecha=" + fecha
                + ", estado='" + estado + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VentaDTO venta = (VentaDTO) o;
        return id != null && id.equals(venta.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
