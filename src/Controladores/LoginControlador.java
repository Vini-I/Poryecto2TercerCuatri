package Controladores;


import Controladores.BaseControlador;
import GUI.FrmLogin;
import GUI.FrmMenu;
import GUI.FrmRegister;
import modelo.Usuario;
import modelo.servicios.AutenticacionServicio;
import modelo.servicios.SessionManager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rodol
 */
public class LoginControlador extends BaseControlador<FrmLogin> {
    
     private AutenticacionServicio autenticacionServicio;
    
    public LoginControlador(FrmLogin vista) {
        super(vista);
        this.autenticacionServicio = new AutenticacionServicio();
    }
    
    public void iniciarSesion(String username, String password) {

        if (!validarCampoVacio(username, "Usuario")) {
            return;
        }
        
        if (!validarCampoVacio(password, "Contraseña")) {
            return;
        }

        if (! validarLongitudMinima(username, "Usuario", 3)) {
            return;
        }
        
        if (!validarLongitudMinima(password, "Contraseña", 6)) {
            return;
        }

        try {
            Usuario usuario = autenticacionServicio.login(username. trim(), password);
            
            if (usuario != null) {
                SessionManager.getInstance().setUsuarioActual(usuario);
                
                mostrarMensaje(
                    "Bienvenido, " + usuario. getNombre(),
                    "Login Exitoso"
                );

                abrirVentanaPrincipal();
                vista.dispose();
                
            } else {
                mostrarError("Usuario o contraseña incorrectos");
            }
            
        } catch (Exception e) {
            mostrarError("Error al iniciar sesión: " + e. getMessage());
            e.printStackTrace();
        }
    }
    
    public void abrirRegistro() {
        FrmRegister frmRegister = new FrmRegister();
        frmRegister.setVisible(true);
        vista.setVisible(false);
    }
    
    private void abrirVentanaPrincipal() {
        FrmMenu frmMenu = new FrmMenu();
        frmMenu.setVisible(true);
    }

    public void limpiarCampos() {
        vista.limpiarCampos();
    }
}
