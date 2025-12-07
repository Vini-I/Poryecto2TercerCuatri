package modelo.mappers;

import modelo.Producto;
import modelo.Proveedor;
import modelo.dtos.ProductoDTO;

public class ProductoMapper {

    private ProductoMapper() {
        // Utilidad, no instanciable
    }

    public static ProductoDTO toDto(Producto producto, String nombreProveedor) {
        if (producto == null) {
            return null;
        }

        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setCodigo(producto.getCodigo());
        dto.setNombre(producto.getNombre());
        dto.setCategoria(producto.getCategoria());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setIdProveedor(producto.getIdProveedor());
        return dto;
    }

    public static Producto toEntity(ProductoDTO dto) {
        if (dto == null) {
            return null;
        }

        Producto producto = new Producto();
        producto.setIdProducto(dto.getIdProducto());
        producto.setCodigo(dto.getCodigo());
        producto.setNombre(dto.getNombre());
        producto.setCategoria(dto.getCategoria());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setIdProducto(dto.getIdProveedor());
        return producto;
    }
}

