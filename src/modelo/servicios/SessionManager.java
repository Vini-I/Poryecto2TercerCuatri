/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.servicios;

import modelo.Usuario;

/**
 *
 * @author rodol
 */
public class SessionManager {
    private static SessionManager instance;
    private Usuario usuarioActual;
   
    private SessionManager() {
    }
    
     public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
        System.out.println("Sesión iniciada: " + usuario.getUsername());
    }
  
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
 
    public boolean isLoggedIn() {
        return usuarioActual != null;
    }
 
    public boolean esAdmin() {
        return usuarioActual != null && usuarioActual.esAdministrador();
    }
   
    public boolean esVendedor() {
        return usuarioActual != null && usuarioActual.esVendedor();
    }

    public void cerrarSesion() {
        if (usuarioActual != null) {
            System.out.println("Sesión cerrada: " + usuarioActual.getUsername());
            usuarioActual = null;
        }
    }
    
    public String getNombreUsuario() {
        return usuarioActual != null ?  usuarioActual.getNombre() : "Invitado";
    }
    

    public String getRol() {
        return usuarioActual != null ? usuarioActual.getRol().name() : "NINGUNO";
    }

    public boolean tienePermiso(String permiso) {
        if (! isLoggedIn()) {
            return false;
        }
        return usuarioActual.tienePermiso(permiso);
    }
    
    
    
    
    
    
    
    
    
    
    
}
