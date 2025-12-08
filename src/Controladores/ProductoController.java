package controladores;

import Controladores.BaseControlador;
import modelo.Producto;
import modelo.Proveedor;
import modelo.daos.IProductoDAO;
import modelo.daos.IProveedorDAO;
import modelo.dtos.ProductoDTO;
import modelo.dtos.ProveedorDTO;
import modelo.mappers.ProductoMapper;
import modelo.servicios.ProductoService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoController extends BaseControlador {

    private final IProductoDAO productoDao;
    private final IProveedorDAO proveedorDao;
    private final ProductoService productoService;

    public ProductoController(JPanel vista, IProductoDAO productoDao, IProveedorDAO proveedorDao) {
        super(vista);
        this.productoDao = productoDao;
        this.proveedorDao = proveedorDao;
        this.productoService = new ProductoService(productoDao, proveedorDao);
    }

    public List<String> crearProducto(ProductoDTO dto) {
        return productoService.crearProducto(dto);
    }

    public List<String> actualizarProducto(ProductoDTO dto) {
        return productoService.actualizarProducto(dto);
    }

    public boolean eliminarProducto(int id) {
        return productoDao.eliminar(id);
    }

    public ProductoDTO obtenerProductoPorId(int id) {
        ProductoDTO p = productoDao.buscarPorId(id);
        if (p == null) return null;

        return p;
    }

    public ProductoDTO buscarPorCodigo(String codigo) {
        ProductoDTO p = productoDao.buscarPorCodigo(codigo);
        if (p == null) return null;

        return p;
    }

    public List<ProductoDTO> listarTodos() {
        List<ProductoDTO> productos = productoDao.listarTodos();

        return productos;
    }

    public List<ProductoDTO> listarPorCategoria(String categoria) {
        List<ProductoDTO> productos = productoDao.buscarPorCategoria(categoria);

        return productos;
    }

    public List<ProductoDTO> listarAgotados() {
        List<ProductoDTO> productos = productoDao.listarAgotados();

        return productos;
    }

    public List<ProductoDTO> listarStockBajo(int umbral) {
        List<ProductoDTO> productos = productoDao.listarStockBajo(umbral);

        return productos;
    }

    public double valorTotalInventario() {
        return productoDao.calcularValorTotalInventario();
    }
}
