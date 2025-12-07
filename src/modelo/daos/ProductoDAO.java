package modelo.daos;

import bd.ConexionBD;
import modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO implements IProductoDAO {

    private Connection getConnection() throws SQLException {
        return ConexionBD.getInstance().getConnection();
    }

    @Override
    public boolean insertar(Producto producto) {
        String sql = "INSERT INTO productos " +
                "(codigo, nombre, categoria, precio, cantidad, proveedor_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getCategoria());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getStock());
            ps.setInt(6, producto.getIdProveedor());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        producto.setIdProducto(rs.getInt(1));
                    }
                }
            }

            return filas > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Producto buscarPorId(int id) {
        String sql = "SELECT id, codigo, nombre, categoria, precio, cantidad, proveedor_id " +
                "FROM productos WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE productos SET " +
                "codigo = ?, nombre = ?, categoria = ?, precio = ?, " +
                "cantidad = ?, proveedor_id = ? " +
                "WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getCategoria());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getStock());
            ps.setInt(6, producto.getIdProveedor());
            ps.setInt(7, producto.getIdProducto());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";

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
    public List<Producto> listarTodos() {
        String sql = "SELECT id, codigo, nombre, categoria, precio, cantidad, proveedor_id " +
                "FROM productos";

        List<Producto> lista = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    @Override
    public Producto buscarPorCodigo(String codigo) {
        String sql = "SELECT id, codigo, nombre, categoria, precio, cantidad, proveedor_id " +
                "FROM productos WHERE codigo = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Producto> buscarPorCategoria(String categoria) {
        String sql = "SELECT id, codigo, nombre, categoria, precio, cantidad, proveedor_id " +
                "FROM productos WHERE categoria = ?";

        List<Producto> lista = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, categoria);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearProducto(rs));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    @Override
    public List<Producto> listarAgotados() {
        String sql = "SELECT id, codigo, nombre, categoria, precio, cantidad, proveedor_id " +
                "FROM productos WHERE cantidad = 0";

        List<Producto> lista = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    @Override
    public List<Producto> listarStockBajo(int umbral) {
        String sql = "SELECT id, codigo, nombre, categoria, precio, cantidad, proveedor_id " +
                "FROM productos WHERE cantidad <= ?";

        List<Producto> lista = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, umbral);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearProducto(rs));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    @Override
    public double calcularValorTotalInventario() {
        String sql = "SELECT SUM(precio * cantidad) AS total_inventario FROM productos";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("total_inventario");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return 0.0;
    }

    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setIdProducto(rs.getInt("id"));
        p.setCodigo(rs.getString("codigo"));
        p.setNombre(rs.getString("nombre"));
        p.setCategoria(rs.getString("categoria"));
        p.setPrecio(rs.getDouble("precio"));
        p.setStock(rs.getInt("cantidad"));
        p.setIdProveedor(rs.getInt("proveedor_id"));
        return p;
    }
}

