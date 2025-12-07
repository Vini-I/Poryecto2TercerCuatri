package modelo.dtos;

public class ProductoDTO {
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

    public ProductoDTO() {
    }

    public ProductoDTO(String codigo, String nombre, String categoria, double precio, int stock, int idProveedor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.idProveedor = idProveedor;
    }

    public ProductoDTO(int idProducto, String codigo, String nombre, String categoria, double precio, int stock, int idProveedor) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.idProveedor = idProveedor;
    }
}
