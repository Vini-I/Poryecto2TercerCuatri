package modelo.daos;

import bd.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.dtos.ClienteDTO;

public class ClienteDAO implements IClienteDAO {

    private Connection getConnection() throws SQLException {
        return ConexionBD.getInstance().getConnection();
    }

    @Override
    public boolean insertar(ClienteDTO dto) {
        String sql = "INSERT INTO clientes " +
                "(cedula, nombre, direccion, telefono, correo) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, dto.getCedula());
            ps.setString(2, dto.getNombreCompleto());
            ps.setString(3, dto.getDireccion());
            ps.setString(4, dto.getTelefono());
            ps.setString(5, dto.getEmail());

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public ClienteDTO buscarPorId(int id) {
        String sql = "SELECT cedula, nombre, direccion, telefono, correo " +
                "FROM clientes WHERE cedula = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean actualizar(ClienteDTO dto) {
        String sql = "UPDATE clientes SET " +
                "nombre = ?, direccion = ?, telefono = ?, correo = ? " +
                "WHERE cedula = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dto.getCedula());
            ps.setString(2, dto.getNombreCompleto());
            ps.setString(3, dto.getDireccion());
            ps.setString(4, dto.getTelefono());
            ps.setString(5, dto.getEmail());
            ps.setInt(6, dto.getCedula());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE cedula = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ClienteDTO> listarTodos() {
        String sql = "SELECT cedula, nombre, direccion, telefono, correo FROM clientes";

        List<ClienteDTO> lista = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    @Override
    public List<ClienteDTO> buscarPorNombre(String nombre) {
        String sql = "SELECT cedula, nombre, direccion, telefono, correo " +
                "FROM clientes WHERE nombre LIKE ?";

        List<ClienteDTO> lista = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearCliente(rs));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    private ClienteDTO mapearCliente(ResultSet rs) throws SQLException {
        ClienteDTO c = new ClienteDTO();
        c.setCedula(rs.getInt("cedula"));
        c.setNombreCompleto(rs.getString("nombre"));
        c.setDireccion(rs.getString("direccion"));
        c.setTelefono(rs.getString("telefono"));
        c.setEmail(rs.getString("correo"));
        return c;
    }
}
