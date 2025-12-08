package controladores;

import Controladores.BaseControlador;
import modelo.dtos.ProductoDTO;
import modelo.servicios.ProductoService;

import javax.swing.*;
import java.util.List;

public class ProductoController extends BaseControlador {

    private final ProductoService productoService;

    public ProductoController(JDialog vista) {
        super(vista);
        this.productoService = new ProductoService();
    }

    public List<String> crearProducto(ProductoDTO dto) {
        return productoService.crearProducto(dto);
    }

    public List<String> actualizarProducto(ProductoDTO dto) {
        return productoService.actualizarProducto(dto);
    }

    public List<String> eliminarProducto(ProductoDTO dto) {
        return productoService.eliminarProducto(dto);
    }

    public boolean eliminarProducto(int id) {
        return productoService.eliminarProducto(id);
    }

    public ProductoDTO obtenerProductoPorId(int id) {
        return productoService.obtenerProductoPorId(id);
    }

    public ProductoDTO buscarPorCodigo(String codigo) {
        return productoService.buscarPorCodigo(codigo);
    }

    public List<ProductoDTO> listarTodos() {
        return productoService.listarTodos();
    }

    public List<ProductoDTO> listarPorCategoria(String categoria) {
        return productoService.listarPorCategoria(categoria);
    }

    public List<ProductoDTO> listarAgotados() {
        return productoService.listarAgotados();
    }

    public List<ProductoDTO> listarStockBajo(int umbral) {
        return productoService.listarStockBajo(umbral);
    }

    public double valorTotalInventario() {
        return productoService.valorTotalInventario();
    }
}
