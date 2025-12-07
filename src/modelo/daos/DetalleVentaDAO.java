/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.daos;

import bd.ConexionBD;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql. SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.dtos.DetalleVentaDTO;

/**
 *
 * @author rodol
 */
public class DetalleVentaDAO implements IDetalleVentaDao {

    @Override
    public List<DetalleVentaDTO> listarPorVenta(int ventaId) {
        ArrayList<DetalleVentaDTO> detalles = new ArrayList<>();

        try (Connection conn = ConexionBD.getInstance().getConnection(); 
                CallableStatement cs = conn.prepareCall("CALL obtener_detalles_venta(?)")) {

            cs.setInt(1, ventaId);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    DetalleVentaDTO detalle = mapResultSetToDTO(rs);
                    detalles.add(detalle);
                }
            }

            System.out.println("Se encontraron " + detalles.size() + " detalles de la venta ID " + ventaId);

        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }

        return detalles;
    }

    @Override
    public List<DetalleVentaDTO> listarPorProducto(int productoId) {
        ArrayList<DetalleVentaDTO> detalles = new ArrayList<>();

        try (Connection conn = ConexionBD.getInstance().getConnection(); 
                CallableStatement cs = conn.prepareCall("CALL obtener_detalles_venta_producto(?)")) {

            cs.setInt(1, productoId);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    DetalleVentaDTO detalle = mapResultSetToDTO(rs);
                    detalles.add(detalle);
                }
            }

            System.out.println("Se encontraron " + detalles.size() + " detalles del producto ID " + productoId);

        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }

        return detalles;
    }

    @Override
    public boolean insertar(DetalleVentaDTO dto) {
        try (Connection conn = ConexionBD.getInstance().getConnection(); 
                CallableStatement cs = conn.prepareCall("CALL insertar_detalle_venta(?, ?, ?, ?)")) {

            cs.setInt(1, dto.getVentaId());
            cs.setInt(2, dto.getProductoId());
            cs.setInt(3, dto.getCantidad());
            cs.setDouble(4, dto.getPrecioUnitario());

            int filasAfectadas = cs.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Detalle de venta insertado");
                return true;
            }

        } catch (SQLException ex) {
            System.out.println("Error al insertar detalle de venta: " + ex);
        }

        return false;
    }

    @Override
    public DetalleVentaDTO buscarPorId(int id) {
        try (Connection conn = ConexionBD.getInstance().getConnection(); 
                CallableStatement cs = conn.prepareCall("CALL obtener_detalle_venta(?)")) {

            cs.setInt(1, id);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    DetalleVentaDTO detalle = mapResultSetToDTO(rs);
                    System.out.println("Detalle de venta encontrado: ID " + id);
                    return detalle;
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }

        System.out.println("Detalle de venta con ID " + id + " no encontrado");
        return null;
    }

    @Override
    public boolean actualizar(DetalleVentaDTO dto) {
        try (Connection conn = ConexionBD.getInstance().getConnection();
                CallableStatement cs = conn.prepareCall("CALL actualizar_detalle_venta(?, ?, ?)")) {

            cs.setInt(1, dto.getId());
            cs.setInt(2, dto.getCantidad());
            cs.setDouble(3, dto.getPrecioUnitario());

            int filasAfectadas = cs.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Detalle de venta actualizado: ID " + dto.getId());
                return true;
            } else {
                System.out.println("No se encontró detalle con ID " + dto.getId());
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }

        return false;
    }

    @Override
    public boolean eliminar(int id) {
        try (Connection conn = ConexionBD.getInstance().getConnection(); CallableStatement cs = conn.prepareCall("CALL eliminar_detalle_venta(?)")) {

            cs.setInt(1, id);

            int filasAfectadas = cs.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Detalle de venta eliminado: ID " + id);
                return true;
            } else {
                System.out.println("No se encontró detalle con ID " + id);
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }

        return false;
    }

    @Override
    public List<DetalleVentaDTO> listarTodos() {
        ArrayList<DetalleVentaDTO> detalles = new ArrayList<>();

        try (Connection conn = ConexionBD.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM detalle_venta"); 
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DetalleVentaDTO detalle = mapResultSetToDTO(rs);
                detalles.add(detalle);
            }

            System.out.println("Se listaron " + detalles.size() + " detalles de venta");

        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }

        return detalles;
    }
    
    private DetalleVentaDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        DetalleVentaDTO dto = new DetalleVentaDTO();

        dto.setId(rs.getInt("id"));
        dto.setVentaId(rs.getInt("venta_id"));
        dto.setProductoId(rs.getInt("producto_id"));
        dto.setCantidad(rs.getInt("cantidad"));
        dto.setPrecioUnitario(rs.getDouble("precio_unitario"));

        return dto;
    }
    
    
}
