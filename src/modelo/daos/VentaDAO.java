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
import java. sql.SQLException;
import java. sql.Timestamp;
import java. util.ArrayList;
import java.util.List;
import modelo.dtos.VentaDTO;

/**
 *
 * @author rodol
 */
public class VentaDAO implements IVentaDao {

    @Override
    public int insertarObtenerId(VentaDTO venta) {
        try (Connection conn = ConexionBD.getInstance().getConnection();
                CallableStatement cs = conn.prepareCall("CALL insertar_venta(?, ?)")) {

            cs.setInt(1, venta.getClienteId());
            cs.setString(2, venta.getEstado());
            
            boolean hasResultSet = cs.execute();
            if (hasResultSet) {
                try (ResultSet rs = cs.getResultSet()) {
                    if (rs.next()) {
                        int ventaId = rs.getInt("venta_id");
                        System.out.println("Venta insertada con ID: " + ventaId);
                        return ventaId;
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }

        return -1;
    }

    @Override
    public List<VentaDTO> listarPorCliente(int clienteId) {
        ArrayList<VentaDTO> ventas = new ArrayList<>();

        try (Connection conn = ConexionBD.getInstance().getConnection(); 
                PreparedStatement ps = conn.prepareStatement("SELECT id, cliente_id, fecha, estado FROM ventas WHERE cliente_id = ? ")) {

            ps.setInt(1, clienteId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    VentaDTO venta = mapResultSetToDTO(rs);
                    ventas.add(venta);
                }
            }

            System.out.println("Se encontraron " + ventas.size() + " ventas del cliente ID " + clienteId);

        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }

        return ventas;
    }

    @Override
    public List<VentaDTO> listarPorEstado(String estado) {
        ArrayList<VentaDTO> ventas = new ArrayList<>();

        try (Connection conn = ConexionBD.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT id, cliente_id, fecha, estado FROM ventas WHERE estado = ?")) {

            ps.setString(1, estado);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    VentaDTO venta = mapResultSetToDTO(rs);
                    ventas.add(venta);
                }
            }

            System.out.println("Se encontraron " + ventas.size() + " ventas con estado " + estado);

        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }

        return ventas;
    }

    @Override
    public List<VentaDTO> listarVentasDelDia() {
        ArrayList<VentaDTO> ventas = new ArrayList<>();;

        try (Connection conn = ConexionBD.getInstance().getConnection(); 
                PreparedStatement ps = conn.prepareStatement("SELECT id, cliente_id, fecha, estado FROM ventas "
                + "WHERE DATE(fecha) = CURDATE()"); 
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                VentaDTO venta = mapResultSetToDTO(rs);
                ventas.add(venta);
            }

            System.out.println("Se encontraron " + ventas.size() + " ventas del día");

        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }

        return ventas;
    }

    @Override
    public boolean insertar(VentaDTO dto) {
        return false;
    }

    @Override
    public VentaDTO buscarPorId(int id) {
        try (Connection conn = ConexionBD.getInstance().getConnection(); 
                CallableStatement cs = conn.prepareCall("CALL obtener_venta(?)")) {

            cs.setInt(1, id);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    VentaDTO venta = mapResultSetToDTO(rs);
                    System.out.println("Venta encontrada: ID " + id);
                    return venta;
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }

        System.out.println("Venta con ID " + id + " no encontrada");
        return null;
    }

    @Override
    public boolean actualizar(VentaDTO dto) {
        try (Connection conn = ConexionBD.getInstance().getConnection(); 
                CallableStatement cs = conn.prepareCall("CALL actualizar_venta(?, ?)")) {

            cs.setInt(1, dto.getId());
            cs.setString(2, dto.getEstado());

            int filasAfectadas = cs.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Venta actualizada: ID " + dto.getId());
                return true;
            } else {
                System.out.println("No se encontró venta con ID " + dto.getId());
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }

        return false;
    }

    @Override
    public boolean eliminar(int id) {
        try (Connection conn = ConexionBD.getInstance().getConnection(); 
                CallableStatement cs = conn.prepareCall("CALL eliminar_venta(?)")) {

            cs.setInt(1, id);

            int filasAfectadas = cs.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Venta eliminada: ID " + id);
                return true;
            } else {
                System.out.println("No se encontró venta con ID " + id);
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }

        return false;
    }
    

    @Override
    public List<VentaDTO> listarTodos() {
        ArrayList<VentaDTO> ventas = new ArrayList<>();

        try (Connection conn = ConexionBD.getInstance().getConnection();
             CallableStatement cs = conn.prepareCall("CALL obtener_ventas()");
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                VentaDTO venta = mapResultSetToDTO(rs);
                ventas.add(venta);
            }

            System.out.println("Se listaron " + ventas.  size() + " ventas");

        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }

        return ventas;
    }

    private VentaDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        VentaDTO dto = new VentaDTO();

        dto.setId(rs.getInt("id"));
        dto.setClienteId(rs.getInt("cliente_id"));
        dto.setEstado(rs.getString("estado"));

        Timestamp fecha = rs.getTimestamp("fecha");
        if (fecha != null) {
            dto.setFecha(fecha.toLocalDateTime());
        }

        return dto;
    }
    
}
