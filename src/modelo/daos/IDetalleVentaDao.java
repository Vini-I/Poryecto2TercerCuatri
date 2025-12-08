/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.daos;

import java.util.List;
import modelo.dtos.DetalleVentaDTO;

/**
 *
 * @author rodol
 */
public interface IDetalleVentaDao extends IDao<DetalleVentaDTO> {

    List<DetalleVentaDTO> listarPorVenta(int ventaId);

    List<DetalleVentaDTO> listarPorProducto(int productoId);
}
