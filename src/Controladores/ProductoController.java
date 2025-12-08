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
        return productoDao.insertar(dto);
    }

    // Actualizar producto existente
    public boolean actualizarProducto(ProductoDTO dto) {
        return productoDao.actualizar(dto);
    }

    // Eliminar producto por id
    public boolean eliminarProducto(int id) {
        return productoDao.eliminar(id);
    }

    // Obtener un producto por id y devolver DTO con nombre de proveedor y agotado
    public ProductoDTO obtenerProductoPorId(int id) {
        ProductoDTO producto = productoDao.buscarPorId(id);
        if (producto == null) {
            return null;
        }

        return producto;
    }

    // Listar todos los productos
    public List<ProductoDTO> listarTodos() {
        List<ProductoDTO> dtos = productoDao.listarTodos();

        return dtos;
    }

    // Listar productos por categoría
    public List<ProductoDTO> listarPorCategoria(String categoria) {
        List<ProductoDTO> dtos = productoDao.buscarPorCategoria(categoria);

        return dtos;
    }

    // Buscar por código
    public ProductoDTO buscarPorCodigo(String codigo) {
        ProductoDTO producto = productoDao.buscarPorCodigo(codigo);
        if (producto == null) {
            return null;
        }

        return producto;
    }

    // Listar productos agotados
    public List<ProductoDTO> listarAgotados() {
        List<ProductoDTO> dtos = productoDao.listarAgotados();

        return dtos;
    }

    // Listar productos con stock bajo
    public List<ProductoDTO> listarStockBajo(int umbral) {
        List<ProductoDTO> dtos = productoDao.listarStockBajo(umbral);

        return dtos;
    }

    // Valor total del inventario
    public double obtenerValorTotalInventario() {
        return productoDao.calcularValorTotalInventario();
    }
}
