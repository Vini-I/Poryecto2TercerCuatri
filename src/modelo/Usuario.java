/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author rodol
 */
public class Usuario {
    private Integer id;
    private String username;
    private String password;
    private String nombre;
    private RolEnum rol;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimoAcceso;

     public Usuario() {
    }
    
 public Usuario(Integer id, String username, String password, String nombreCompleto, 
                  RolEnum rol, LocalDateTime fechaCreacion, LocalDateTime ultimoAcceso) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nombre = nombreCompleto;
        this.rol = rol;
        this.fechaCreacion = fechaCreacion;
        this.ultimoAcceso = ultimoAcceso;
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getNombre() {return nombre;}
    public void setNombreCompleto(String nombreCompleto) {this.nombre = nombreCompleto;}
    public RolEnum getRol() {return rol;}
    public void setRol(RolEnum rol) {this.rol = rol;}
    public LocalDateTime getFechaCreacion() {return fechaCreacion;}
    public void setFechaCreacion(LocalDateTime fechaCreacion) {this.fechaCreacion = fechaCreacion;}
    public LocalDateTime getUltimoAcceso() {return ultimoAcceso;}

    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public boolean esAdministrador() {
        return rol.esAdministrador();
    }

    public boolean esVendedor() {
        return rol.esVendedor();
    }

    public void registrarAcceso() {
        this.ultimoAcceso = LocalDateTime.now();
    }

    public boolean esNuevo() {
        return this.id == null;
    }

    public String getRolDescripcion() {
        return rol != null ? rol.getDescripcion() : "Sin rol";
    }
     
     public boolean tienePermiso(String permiso) {
        if (rol == null) {
            return false;
        }
      
        if (esAdministrador()) {
            return true;
        }
        
        if (esVendedor()) {
            return permiso.equals("CREAR_VENTA") || 
                   permiso.equals("CREAR_CLIENTE") ||
                   permiso.equals("VER_PRODUCTOS") ||
                   permiso.equals("VER_CLIENTES") ||
                   permiso.equals("VER_VENTAS")||
                   permiso.equals("VER_FACTURAS")||
                   permiso.equals("GENERAR_FACTURA");
        }
        
        return false;
    }

     @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nombreCompleto='" + nombre + '\'' +
                ", rol=" + rol +
                ", ultimoAcceso=" + ultimoAcceso +
                '}';
    }
     
}
