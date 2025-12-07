/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.daos;

import bd.ConexionBD;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import modelo.dtos.UsuarioDTO;

/**
 *
 * @author rodol
 */
public class UsuarioDAO implements IUsuarioDao {

    @Override
    public UsuarioDTO buscarPorUsername(String username) {
        try (Connection conn = ConexionBD.getInstance().getConnection();
             CallableStatement cs = conn.prepareCall("CALL obtener_usuario_username(?)")) {
            
            cs.setString(1, username);
            
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    UsuarioDTO usuario = mapResultSetToDTO(rs);
                    System.out.println("Usuario encontrado: " + username);
                    return usuario;
                }
            }
            
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
        }
        
        System. out.println("Usuario '" + username + "' no encontrado");
        return null;
    }

    @Override
    public List<UsuarioDTO> listarPorRol(String rol) {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id, username, password, nombre, rol, fecha_creacion, ultimo_acceso " +
                     "FROM usuarios WHERE rol = ?")) {
            
            ps. setString(1, rol);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UsuarioDTO usuario = mapResultSetToDTO(rs);
                    usuarios.add(usuario);
                }
            }
            
            System.out.println("Se encontraron " + usuarios.size() + " usuarios con rol " + rol);
            
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
        }
        
        return usuarios;
    }

    @Override
    public boolean existeUsername(String username) {
        try (Connection conn = ConexionBD.getInstance().getConnection(); 
                PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM usuarios WHERE username = ?")) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    boolean existe = rs.getInt(1) > 0;
                    System.out.println(existe
                            ? "Username '" + username + "' ya existe"
                            : "Username '" + username + "' disponible");
                    return existe;
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error " + ex);
        }

        return false;
    }
    
    @Override
    public boolean actualizarUltimoAcceso(int id) {
        String sql = "UPDATE usuarios SET ultimo_acceso = NOW() WHERE id = ?";
        
        try (Connection conn = ConexionBD.getInstance().getConnection();
             PreparedStatement ps = conn. prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                System. out.println("Ultimo acceso actualizado para usuario ID: " + id);
                return true;
            }
            
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
        }
        
        return false;
    }

    @Override
    public boolean insertar(UsuarioDTO dto) {
         try (Connection conn = ConexionBD.getInstance(). getConnection();
             CallableStatement cs = conn.prepareCall("CALL insertar_usuario(?, ?, ?, ?)")) {
  
            cs.setString(1, dto.getUsername());
            cs.setString(2, dto.getPassword());
            cs.setString(3, dto.getNombre());
            cs.setString(4, dto.getRol());
        
            int filasAfectadas = cs.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Usuario insertado: " + dto.getUsername());
                return true;
            }
            
        } catch (SQLException ex) {
            System.err. println("Error : " + ex);
            
            if (ex. getErrorCode() == 1062) {
                System.err. println("El username '" + dto.getUsername() + "' ya existe");
            }

        }
        
        return false;
    }

    @Override
    public UsuarioDTO buscarPorId(int id) {
        try (Connection conn = ConexionBD.getInstance().getConnection();
             CallableStatement cs = conn.prepareCall("CALL obtener_usuario_id(?)")) {
            
            cs.setInt(1, id);
            
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    UsuarioDTO usuario = mapResultSetToDTO(rs);
                    System.out.println("Usuario encontrado: " + usuario.getUsername());
                    return usuario;
                }
            }
            
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
        }
        
        System.out.println("Usuario con ID " + id + " no encontrado");
        return null;
    }

    @Override
    public boolean actualizar(UsuarioDTO dto) {
        try (Connection conn = ConexionBD.getInstance().getConnection();
             CallableStatement cs = conn.prepareCall("CALL actualizar_usuario(?, ?, ?)")) {
            
            cs.setInt(1, dto.getId());
            cs.setString(2, dto.getNombre());
            cs.setString(3, dto.getRol());
            
            int filasAfectadas = cs.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Usuario actualizado: ID " + dto.getId());
                return true;
            } else {
                System.out. println("No se encontró usuario con ID " + dto.getId());
            }
            
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
        }
        
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        try (Connection conn = ConexionBD.getInstance().getConnection();
             CallableStatement cs = conn.prepareCall("CALL eliminar_usuario(?)")) {
            
            cs.setInt(1, id);
            
            int filasAfectadas = cs.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Usuario eliminado: ID " + id);
                return true;
            } else {
                System. out.println("No se encontró usuario con ID " + id);
            }
            
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
        }
        
        return false;
    }

    @Override
    public List listarTodos() {
        ArrayList<UsuarioDTO> usuarios = new ArrayList<>();

        try (Connection conn = ConexionBD.getInstance().getConnection(); 
                CallableStatement cs = conn.prepareCall("CALL obtener_usuarios()");
                ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                UsuarioDTO usuario = mapResultSetToDTO(rs);
                usuarios.add(usuario);
            }

            System.out.println("Se listaron " + usuarios.size() + " usuarios");

        } catch (SQLException ex) {
            System.out.println("Error" + ex);
        }

        return usuarios;
    }

    private UsuarioDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        UsuarioDTO dto = new UsuarioDTO();
        
        dto.setId(rs.getInt("id"));
        dto.setUsername(rs. getString("username"));
        dto.setPassword(rs.getString("password"));
        dto.setNombre(rs.getString("nombre"));
        dto.setRol(rs.getString("rol"));
        
        Timestamp fechaCreacion = rs.getTimestamp("fecha_creacion");
        if (fechaCreacion != null) {
            dto.setFechaCreacion(fechaCreacion.toLocalDateTime());
        }
        
        Timestamp ultimoAcceso = rs.getTimestamp("ultimo_acceso");
        if (ultimoAcceso != null) {
            dto.setUltimoAcceso(ultimoAcceso.toLocalDateTime());
        }
        
        return dto;
    }
    
}
