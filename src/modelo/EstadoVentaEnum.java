/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package modelo;

/**
 *
 * @author rodol
 */
public enum EstadoVentaEnum {
    COMPLETADA("Completada", "COMPLETADA"),
    PENDIENTE("Pendiente", "PENDIENTE"),
    ANULADA("Anulada", "ANULADA");
    
    private final String descripcion;
    private final String codigo;
    
    EstadoVentaEnum(String descripcion, String codigo) {
        this. descripcion = descripcion;
        this.codigo = codigo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public String getCodigo() {
        return codigo;
    }
   
    public static EstadoVentaEnum fromString(String texto) {
        if (texto == null) {
            return null;
        }
        
        for (EstadoVentaEnum estado : EstadoVentaEnum.values()) {
            if (estado.name().equalsIgnoreCase(texto)) {
                return estado;
            }
        }
        
        throw new IllegalArgumentException("Estado de venta inválido: " + texto);
    }
 
    public boolean esCompletada() {
        return this == COMPLETADA;
    }
    
    public boolean esAnulada() {
        return this == ANULADA;
    }
    
    public boolean esPendiente() {
        return this == PENDIENTE;
    }
}