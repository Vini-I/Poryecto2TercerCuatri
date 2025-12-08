/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Exceptions.LogicaNegocioException;
import Exceptions.ValidacionException;
import GUI.FrmLogin;
import GUI.FrmRegister;
import modelo.RolEnum;
import modelo.servicios.UsuarioServicio;

/**
 *
 * @author rodol
 */
public class RegisterControlador extends BaseControlador<FrmRegister> {
    
    private UsuarioServicio usuarioServicio;
    
    public RegisterControlador(FrmRegister vista) {
        super(vista);
        this.usuarioServicio = new UsuarioServicio();
    }
    
    public void registrarUsuario(String username, String nombreCompleto, String password, String rolSeleccionado) {
        if (!validarCampoVacio(username, "Nombre de Usuario")) {
            return;
        }
        
        if (!validarCampoVacio(nombreCompleto, "Nombre Completo")) {
            return;
        }
        
        if (! validarCampoVacio(password, "Contraseña")) {
            return;
        }
        
        if (!validarCampoVacio(rolSeleccionado, "Rol")) {
            return;
        }

        if (!validarLongitudMinima(username, "Nombre de Usuario", 3)) {
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
            rol = RolEnum.valueOf(rolSeleccionado.toUpperCase());
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
            
            mostrarMensaje(
                "Usuario registrado exitosamente.\nYa puede iniciar sesión.",
                "Registro Exitoso"
            );

            volverAlLogin();
            
        } catch (ValidacionException e) {
            mostrarError(e.getMessage());
            
        } catch (LogicaNegocioException e) {
            mostrarError(e.getMessage());
            
        } catch (Exception e) {
            mostrarError("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
     public void volverAlLogin() {
        FrmLogin frmLogin = new FrmLogin();
        frmLogin.setVisible(true);
        vista.dispose();
    }
    
    public void limpiarCampos() {
        vista.limpiarCampos();
    }
    
    
    
}
