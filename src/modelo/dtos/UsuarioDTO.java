/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dtos;

import java.time.LocalDateTime;

/**
 *
 * @author rodol
 */
public class UsuarioDTO {

    private Integer id;
    private String username;
    private String password;
    private String nombre;
    private String rol;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimoAcceso;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Integer id, String username, String password,
            String nombreCompleto, String rol,
            LocalDateTime fechaCreacion, LocalDateTime ultimoAcceso) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nombre = nombreCompleto;
        this.rol = rol;
        this.fechaCreacion = fechaCreacion;
        this.ultimoAcceso = ultimoAcceso;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) {this.username = username;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public String getRol() {return rol;}
    public void setRol(String rol) {this.rol = rol;}
    public LocalDateTime getFechaCreacion() {return fechaCreacion;}
    public void setFechaCreacion(LocalDateTime fechaCreacion) {this.fechaCreacion = fechaCreacion;}
    public LocalDateTime getUltimoAcceso() {return ultimoAcceso;}
    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {this.ultimoAcceso = ultimoAcceso;}

    @Override
    public String toString() {
        return "UsuarioDTO{"
                + "id=" + id
                + ", username='" + username + '\''
                + ", nombreCompleto='" + nombre + '\''
                + ", rol='" + rol +  '\''
                + ", fechaCreacion=" + fechaCreacion
                + ", ultimoAcceso=" + ultimoAcceso
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UsuarioDTO usuario = (UsuarioDTO) o;
        return id != null && id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
