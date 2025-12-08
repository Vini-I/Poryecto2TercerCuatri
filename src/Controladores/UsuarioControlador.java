/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import GUI.PnlUsuarios;
import java.util.List;
import modelo.RolEnum;
import modelo.dtos.UsuarioDTO;
import modelo.servicios.UsuarioServicio;

/**
 *
 * @author rodol
 */
public class UsuarioControlador extends BaseControlador<PnlUsuarios> {

    private UsuarioServicio usuarioServicio;

    public UsuarioControlador(PnlUsuarios vista) {
        super(vista);
        this.usuarioServicio = new UsuarioServicio();
    }

    public void cargarUsuarios() {
        try {
            List<UsuarioDTO> usuarios = usuarioServicio.obtenerTodos();
            vista.actualizarTabla(usuarios);
        } catch (Exception e) {
            mostrarError("Error al cargar usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void buscarUsuarios(String criterio) {
        try {
            if (criterio == null || criterio.trim().isEmpty()) {
                cargarUsuarios();
                return;
            }

            List<UsuarioDTO> usuarios = usuarioServicio.buscar(criterio);
            vista.actualizarTabla(usuarios);
        } catch (Exception e) {
            mostrarError("Error al buscar usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void crearUsuario(String username, String nombreCompleto, String password, String rolStr) {
        if (!validarCampoVacio(username, "Usuario")) {
            return;
        }
        if (!validarCampoVacio(nombreCompleto, "Nombre Completo")) {
            return;
        }
        if (!validarCampoVacio(password, "Contraseña")) {
            return;
        }
        if (!validarCampoVacio(rolStr, "Rol")) {
            return;
        }

        if (!validarLongitudMinima(username, "Usuario", 3)) {
            return;
        }
        if (!validarLongitudMinima(nombreCompleto, "Nombre Completo", 3)) {
            return;
        }
        if (!validarLongitudMinima(password, "Contraseña", 6)) {
            return;
        }

        RolEnum rol;
        try {
            rol = RolEnum.valueOf(rolStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            mostrarError("Rol inválido.  Seleccione 'Vendedor' o 'Administrador'");
            return;
        }

        try {
            usuarioServicio.registrarUsuario(
                    username.trim(),
                    password,
                    nombreCompleto.trim(),
                    rol.toString()
            );

            mostrarMensaje("Usuario creado exitosamente", "Éxito");

        } catch (Exception e) {
            mostrarError("Error al crear usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void actualizarUsuario(int idUsuario, String nombreCompleto, String rolStr) {
        if (!validarCampoVacio(nombreCompleto, "Nombre Completo")) {
            return;
        }
        if (!validarCampoVacio(rolStr, "Rol")) {
            return;
        }
        if (!validarLongitudMinima(nombreCompleto, "Nombre Completo", 3)) {
            return;
        }

        RolEnum rol;
        try {
            rol = RolEnum.valueOf(rolStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            mostrarError("Rol inválido");
            return;
        }

        try {
            usuarioServicio.actualizarUsuario(idUsuario, nombreCompleto.trim(), rol.toString());
            mostrarMensaje("Usuario actualizado exitosamente", "Éxito");

        } catch (Exception e) {
            mostrarError("Error al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminarUsuario(UsuarioDTO usuario) {
        if (usuario == null) {
                    System.out.println("❌ Usuario es NULL");
            mostrarError("Seleccione un usuario de la tabla");
            return;
        }
  System.out.println("Usuario a eliminar:");
    System.out.println("  ID: " + usuario.getId());
    System.out.println("  Username: " + usuario. getUsername());
    System.out.println("  Nombre: " + usuario.getNombre());
    System.out.println("  Rol: " + usuario. getRol());
        boolean confirma = confirmar(
                "¿Está seguro de eliminar el usuario '" + usuario.getUsername() + "'?",
                "Confirmar Eliminación"
        );

        if (!confirma) {
              System.out.println("Eliminación cancelada por el usuario");
            return;
        }

        try {
                    System.out.println("Llamando a usuarioServicio.eliminar(" + usuario.getId() + ")");
           boolean resultado =  usuarioServicio.eliminarUsuario(usuario.getId());
                    System.out.println("Resultado del servicio: " + resultado);
           if(resultado){
                           System.out.println("✅ Eliminación exitosa, mostrando mensaje");
               mostrarMensaje("Usuario eliminado exitosamente", "Éxito");
            cargarUsuarios();
                        System.out.println("Usuarios recargados");
           }
        } catch (Exception e) {
                        System.out. println("⚠️ El servicio retornó false");
            mostrarError("Error al eliminar usuario: " + e.getMessage());
            e.printStackTrace();

        }
    }
}