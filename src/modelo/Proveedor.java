package modelo;

public class Proveedor {
    private int idProveedor;
    private String nombre;
    private String contacto;
    private String direccion;
    private String telefono;

    public int getIdProveedor() {
        return idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Proveedor() {
    }

    public Proveedor(String nombre, String contacto, String direccion, String telefono) {
        this.nombre = nombre;
        this.contacto = contacto;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public Proveedor(int idProveedor, String nombre, String contacto, String direccion, String telefono) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.contacto = contacto;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Proveedor{" +
                "idProveedor=" + idProveedor +
                ", nombre='" + nombre + '\'' +
                ", contacto='" + contacto + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
