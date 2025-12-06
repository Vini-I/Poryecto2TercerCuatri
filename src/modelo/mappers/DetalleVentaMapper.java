/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.mappers;

import modelo.Cliente;
import modelo.EstadoVentaEnum;
import modelo.Venta;
import modelo.dtos.VentaDTO;

/**
 *
 * @author rodol
 */
public class DetalleVentaMapper {
    
    public static Venta toEntity(VentaDTO dto, Cliente cliente) {
        if (dto == null){
             throw new IllegalArgumentException("El dto no puede estar vacio");
        }
        
        return new Venta(
            dto.getId(),
            cliente,
            dto.getFecha(),
            dto.getEstado() != null ? EstadoVentaEnum.fromString(dto.getEstado()) : null
        );
    }

    public static VentaDTO toDTO(Venta entity) {
        if (entity == null) {
             throw new IllegalArgumentException("La venta no puede estar vacia");
        }
        
        return new VentaDTO(
            entity.getId(),
            entity.getCliente() != null ? entity.getCliente().getCedula() : null, 
            entity.getFecha(),
            entity.getEstado() != null ? entity.getEstado().name() : null
        );
    }
}
