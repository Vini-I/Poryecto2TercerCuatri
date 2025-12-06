/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.mappers;

import modelo.DetalleVenta;
import modelo.Producto;
import modelo.Venta;
import modelo.dtos.DetalleVentaDTO;

/**
 *
 * @author rodol
 */
public class VentaMapper {
     
    public static DetalleVenta toEntity(DetalleVentaDTO dto, Venta venta, Producto producto) {
        if (dto == null){
             throw new IllegalArgumentException("El dto no puede estar vacio");
        }
        
        return new DetalleVenta(
            dto.getId(),
            venta, 
            producto, 
            dto.getCantidad(),
            dto.getPrecioUnitario()
        );
    }

    public static DetalleVentaDTO toDTO(DetalleVenta entity) {
        if (entity == null){
             throw new IllegalArgumentException("El detalle de venta no puede estar vacio");
        }
        
        return new DetalleVentaDTO(
            entity.getId(),
            entity. getVenta() != null ? entity.getVenta().getId() : null,     
            entity.getProducto() != null ?  entity.getProducto().getCodigo() : null,
            entity.getCantidad(),
            entity.getPrecioUnitario()
        );
    }
}
