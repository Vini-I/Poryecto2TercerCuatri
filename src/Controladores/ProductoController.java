package controladores;

import java.util.ArrayList;
import java.util.List;

import modelo.Producto;
import modelo.Proveedor;
import modelo.daos.IProductoDAO;
import modelo.daos.IProveedorDAO;
import modelo.daos.IProveedorDAO;
import modelo.dtos.ProductoDTO;
import modelo.mappers.ProductoMapper;

public class ProductoController {

    private final IProductoDAO productoDao;
    private final IProveedorDAO proveedorDao;

    public ProductoController(IProductoDAO productoDao, IProveedorDAO proveedorDao) {
        this.productoDao = productoDao;
        this.proveedorDao = proveedorDao;
    }

    // Crear producto desde DTO
    public boolean crearProducto(ProductoDTO dto) {
        Producto producto = ProductoMapper.toEntity(dto);
        return productoDao.insertar(producto);
    }

    // Actualizar producto existente
    public boolean actualizarProducto(ProductoDTO dto) {
        Producto producto = ProductoMapper.toEntity(dto);
        return productoDao.actualizar(producto);
    }

    // Eliminar producto por id
    public boolean eliminarProducto(int id) {
        return productoDao.eliminar(id);
    }

    // Obtener un producto por id y devolver DTO con nombre de proveedor y agotado
    public ProductoDTO obtenerProductoPorId(int id) {
        Producto producto = productoDao.buscarPorId(id);
        if (producto == null) {
            return null;
        }

        return ProductoMapper.toDto(producto);
    }

    // Listar todos los productos
    public List<ProductoDTO> listarTodos() {
        List<Producto> productos = productoDao.listarTodos();
        List<ProductoDTO> dtos = new ArrayList<>();

        for (Producto p : productos) {
            dtos.add(ProductoMapper.toDto(p));
        }

        return dtos;
    }

    // Listar productos por categoría
    public List<ProductoDTO> listarPorCategoria(String categoria) {
        List<Producto> productos = productoDao.buscarPorCategoria(categoria);
        List<ProductoDTO> dtos = new ArrayList<>();

        for (Producto p : productos) {
            dtos.add(ProductoMapper.toDto(p));
        }

        return dtos;
    }

    // Buscar por código
    public ProductoDTO buscarPorCodigo(String codigo) {
        Producto producto = productoDao.buscarPorCodigo(codigo);
        if (producto == null) {
            return null;
        }

        return ProductoMapper.toDto(producto);
    }

    // Listar productos agotados
    public List<ProductoDTO> listarAgotados() {
        List<Producto> productos = productoDao.listarAgotados();
        List<ProductoDTO> dtos = new ArrayList<>();

        for (Producto p : productos) {
            dtos.add(ProductoMapper.toDto(p));
        }

        return dtos;
    }

    // Listar productos con stock bajo
    public List<ProductoDTO> listarStockBajo(int umbral) {
        List<Producto> productos = productoDao.listarStockBajo(umbral);
        List<ProductoDTO> dtos = new ArrayList<>();

        for (Producto p : productos) {
            dtos.add(ProductoMapper.toDto(p));
        }

        return dtos;
    }

    // Valor total del inventario
    public double obtenerValorTotalInventario() {
        return productoDao.calcularValorTotalInventario();
    }
}
