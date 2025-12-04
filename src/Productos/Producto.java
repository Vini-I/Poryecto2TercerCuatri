package Productos;

public class Producto {
    private int idProducto;
    private String codigo;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;
    private int idProveedor;

    public int getIdProducto() {
        return idProducto;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Producto() {
    }

    public Producto(String codigo, String nombre, String categoria, double precio, int stock, int idProveedor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.idProveedor = idProveedor;
    }

    public Producto(int idProducto, String codigo, String nombre, String categoria, double precio, int stock, int idProveedor) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.idProveedor = idProveedor;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "stock=" + stock +
                ", precio=" + precio +
                ", categoria='" + categoria + '\'' +
                ", nombre='" + nombre + '\'' +
                ", codigo='" + codigo + '\'' +
                ", idProducto=" + idProducto +
                '}';
    }

    public boolean isAgotado() {
        if (stock == 0) return true;
        else return false;
    }

    public boolean isStockBajo(int umbral) {
        if (stock <= umbral) return true;
        else return false;
    }
}
