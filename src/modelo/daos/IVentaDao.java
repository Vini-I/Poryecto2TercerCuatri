/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.daos;

import java.util.List;
import modelo.dtos.VentaDTO;

/**
 *
 * @author rodol
 */
public interface IVentaDao extends IDao {

    int insertarObtenerId(VentaDTO venta);

    List<VentaDTO> listarPorCliente(int clienteId);

    List<VentaDTO> listarPorEstado(String estado);

    List<VentaDTO> listarVentasDelDia();
}
