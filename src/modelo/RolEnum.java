/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package modelo;

/**
 *
 * @author rodol
 */
public enum RolEnum {
    ADMIN("Administrador del Sistema", "ADMIN"),
    VENDEDOR("Vendedor", "VENDEDOR");

    private final String descripcion;
    private final String codigo;

    RolEnum(String descripcion, String codigo) {
        this.descripcion = descripcion;
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public static RolEnum fromString(String texto) {
        if (texto == null) {
            return null;
        }

        for (RolEnum rol : RolEnum.values()) {
            if (rol.name().equalsIgnoreCase(texto)) {
                return rol;
            }
        }
        return null;
    }

    public boolean esAdministrador() {
        return this == ADMIN;
    }

    public boolean esVendedor() {
        return this == VENDEDOR;
    }
}
