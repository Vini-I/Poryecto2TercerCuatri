/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.servicios;

import Exceptions.LogicaNegocioException;
import Exceptions.ValidacionException;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;
import modelo.daos.UsuarioDAO;
import modelo.dtos.UsuarioDTO;
import modelo.mappers.UsuarioMapper;

/**
 *
 * @author rodol
 */
public class UsuarioServicio {

    private UsuarioDAO usuarioDAO;

    public UsuarioServicio() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario registrarUsuario(String username, String password, String nombre, String rol)
            throws ValidacionException, LogicaNegocioException {

        validarUsername(username);
        validarPassword(password);
        validarNombre(nombre);
        validarRol(rol);

        if (usuarioDAO.existeUsername(username)) {
            throw new LogicaNegocioException("El username '" + username + "' ya está en uso");
        }

        if (rol.equals("ADMIN")) {
            int cantidadAdmins = usuarioDAO.listarPorRol("ADMIN").size();
            if (cantidadAdmins >= 5) {
                throw new LogicaNegocioException("No se pueden crear más de 5 administradores");
            }
        }

        UsuarioDTO dto = new UsuarioDTO();
        dto.setUsername(username);
        dto.setPassword(password);
        dto.setNombre(nombre);
        dto.setRol(rol);

        boolean insertado = usuarioDAO.insertar(dto);

        if (!insertado) {
            throw new LogicaNegocioException("Error al registrar el usuario");
        }

        UsuarioDTO usuarioCreado = usuarioDAO.buscarPorUsername(username);
        System.out.println("Usuario registrado: " + username);

        return UsuarioMapper.toEntity(usuarioCreado);
    }

    public boolean actualizarUsuario(int id, String nombre, String rol)
            throws ValidacionException, LogicaNegocioException {

        validarNombre(nombre);
        validarRol(rol);

        UsuarioDTO usuario = usuarioDAO.buscarPorId(id);
        if (usuario == null) {
            throw new LogicaNegocioException("El usuario ID " + id + " no existe");
        }

        usuario.setNombre(nombre);
        usuario.setRol(rol);

        boolean actualizado = usuarioDAO.actualizar(usuario);

        if (actualizado) {
            System.out.println("Usuario actualizado: " + usuario.getUsername());
        }

        return actualizado;
    }


    public boolean eliminarUsuario(int id) throws LogicaNegocioException {
         System.out.println("ID a eliminar: " + id);
        UsuarioDTO usuario = usuarioDAO.buscarPorId(id);
            System.out.println("Usuario encontrado: " + (usuario != null ? usuario.getUsername() : "NULL"));
        if (usuario == null) {
            System. out.println("⚠️ Usuario no existe");
            throw new LogicaNegocioException("El usuario ID " + id + " no existe");
        }

        Usuario usuarioActual = SessionManager.getInstance().getUsuarioActual();
            System.out.println("Usuario actual: " + (usuarioActual != null ? usuarioActual.getUsername() : "NULL"));
        if (usuarioActual != null && usuarioActual.getId().equals(id)) {
            throw new LogicaNegocioException("No puedes eliminar tu propio usuario");
        }

        boolean eliminado = usuarioDAO.eliminar(id);
            System.out.println("Llamando a usuarioDAO.eliminar(" + id + ")");
    System. out.println("Resultado de DAO.eliminar(): " + eliminado);
        if (eliminado) {
            System.out.println("Usuario eliminado: " + usuario.getUsername());
        }

        return eliminado;
    }

 
    public List<Usuario> listarTodos() {
        List<UsuarioDTO> dtos = obtenerTodos();
        List<Usuario> usuarios = new ArrayList<>();

        for (UsuarioDTO dto : dtos) {
            usuarios.add(UsuarioMapper.toEntity(dto));
        }

        return usuarios;
    }
    
    public List<UsuarioDTO> obtenerTodos() {
        return usuarioDAO.listarTodos();
    }

    public List<Usuario> listarPorRol(String rol) {
        List<UsuarioDTO> dtos = usuarioDAO.listarPorRol(rol);
        List<Usuario> usuarios = new ArrayList<>();

        for (UsuarioDTO dto : dtos) {
            usuarios.add(UsuarioMapper.toEntity(dto));
        }

        return usuarios;
    }
    
     public List<UsuarioDTO> buscar(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return obtenerTodos();
        }

        List<UsuarioDTO> todosLosUsuarios = usuarioDAO.listarTodos();
        List<UsuarioDTO> resultados = new ArrayList<>();
        String criterioBusqueda = criterio.toLowerCase();

        for (UsuarioDTO usuario : todosLosUsuarios) {
            boolean coincide = false;

            if (usuario.getUsername() != null && 
                usuario.getUsername().toLowerCase().contains(criterioBusqueda)) {
                coincide = true;
            }

            if (usuario.getNombre() != null && 
                usuario.getNombre().toLowerCase().contains(criterioBusqueda)) {
                coincide = true;
            }

            if (usuario.getRol() != null && 
                usuario.getRol().toLowerCase().contains(criterioBusqueda)) {
                coincide = true;
            }

            if (coincide) {
                resultados.add(usuario);
            }
        }

        return resultados;
    }
    

    public Usuario buscarPorId(int id) throws LogicaNegocioException {
        UsuarioDTO dto = usuarioDAO.buscarPorId(id);

        if (dto == null) {
            throw new LogicaNegocioException("El usuario ID " + id + " no existe");
        }

        return UsuarioMapper.toEntity(dto);
    }

    public Usuario buscarPorUsername(String username) throws LogicaNegocioException {
        UsuarioDTO dto = usuarioDAO.buscarPorUsername(username);

        if (dto == null) {
            throw new LogicaNegocioException("El usuario '" + username + "' no existe");
        }

        return UsuarioMapper.toEntity(dto);
    }

    private void validarUsername(String username) throws ValidacionException {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidacionException("El username es obligatorio");
        }

        if (username.length() < 3) {
            throw new ValidacionException("El username debe tener al menos 3 caracteres");
        }

        if (username.length() > 50) {
            throw new ValidacionException("El username no puede exceder 50 caracteres");
        }

        if (!username.matches("[a-zA-Z0-9_]+")) {
            throw new ValidacionException("El username solo puede contener letras, números y guiones bajos");
        }
    }

    private void validarPassword(String password) throws ValidacionException {
        if (password == null || password.isEmpty()) {
            throw new ValidacionException("La contraseña es obligatoria");
        }

        if (password.length() < 6) {
            throw new ValidacionException("La contraseña debe tener al menos 6 caracteres");
        }
    }

    private void validarNombre(String nombre) throws ValidacionException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre es obligatorio");
        }

        if (nombre.length() > 100) {
            throw new ValidacionException("El nombre no puede exceder 100 caracteres");
        }
    }

    private void validarRol(String rol) throws ValidacionException {
        if (rol == null || rol.trim().isEmpty()) {
            throw new ValidacionException("El rol es obligatorio");
        }

        if (!rol.equals("ADMIN") && !rol.equals("VENDEDOR")) {
            throw new ValidacionException("El rol debe ser ADMIN o VENDEDOR");
        }
    }

}
