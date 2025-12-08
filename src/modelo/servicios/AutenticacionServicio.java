/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.servicios;

import Exceptions.LogicaNegocioException;
import Exceptions.ValidacionException;
import modelo.Usuario;
import modelo.daos.UsuarioDAO;
import modelo.dtos.UsuarioDTO;
import modelo.mappers.UsuarioMapper;

/**
 *
 * @author rodol
 */
public class AutenticacionServicio {

    private UsuarioDAO usuarioDAO;

    public AutenticacionServicio() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario login(String username, String password)
            throws ValidacionException, LogicaNegocioException {

        System.out.println("Intentando login: " + username);

        validarUsername(username);
        validarPassword(password);

        UsuarioDTO usuarioDTO = usuarioDAO.buscarPorUsername(username);

        if (usuarioDTO == null) {
            throw new LogicaNegocioException("Usuario o contraseña incorrectos");
        }

        if (!password.equals(usuarioDTO.getPassword())) {
            throw new LogicaNegocioException("Usuario o contraseña incorrectos");
        }

        usuarioDAO.actualizarUltimoAcceso(usuarioDTO.getId());

        Usuario usuario = UsuarioMapper.toEntity(usuarioDTO);
        usuario.registrarAcceso();

        SessionManager.getInstance().setUsuarioActual(usuario);

        System.out.println("Login exitoso: " + username + " [" + usuario.getRol() + "]");

        return usuario;
    }

    public void logout() {
        SessionManager.getInstance().cerrarSesion();
    }

    public boolean estaLogueado() {
        return SessionManager.getInstance().isLoggedIn();
    }

    public Usuario getUsuarioActual() {
        return SessionManager.getInstance().getUsuarioActual();
    }

    private void validarUsername(String username) throws ValidacionException {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidacionException("El username es obligatorio");
        }

        if (username.length() < 3) {
            throw new ValidacionException("El username debe tener al menos 3 caracteres");
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

}
