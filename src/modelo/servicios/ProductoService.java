package modelo.servicios;

import java.util.ArrayList;
import java.util.List;

import modelo.daos.IProductoDAO;
import modelo.daos.IProveedorDAO;
import modelo.daos.ProductoDAO;
import modelo.daos.ProveedorDAO;
import modelo.dtos.ProductoDTO;
import modelo.dtos.ProveedorDTO;

public class ProductoService {

    private final IProductoDAO productoDao;
    private final IProveedorDAO proveedorDao;

    public ProductoService() {
        this.productoDao = new ProductoDAO();
        this.proveedorDao = new ProveedorDAO();
    }

    public List<String> validarProducto(ProductoDTO dto, boolean esNuevo) {
        List<String> errores = new ArrayList<>();

        if (dto == null) {
            errores.add("El producto no puede ser nulo.");
            return errores;
        }

        if (esVacio(dto.getCodigo())) {
            errores.add("El código es obligatorio.");
        } else if (dto.getCodigo().length() > 50) {
            errores.add("El código no puede superar 50 caracteres.");
        }

        if (esVacio(dto.getNombre())) {
            errores.add("El nombre es obligatorio.");
        } else if (dto.getNombre().length() > 100) {
            errores.add("El nombre no puede superar 100 caracteres.");
        }

        if (dto.getCategoria() != null && dto.getCategoria().length() > 100) {
            errores.add("La categoría no puede superar 100 caracteres.");
        }

        if (dto.getPrecio() <= 0) {
            errores.add("El precio debe ser mayor a 0.");
        }

        if (dto.getStock() < 0) {
            errores.add("La cantidad no puede ser negativa.");
        }

        if (dto.getIdProveedor() <= 0) {
            errores.add("Debe seleccionar un proveedor.");
        } else {
            ProveedorDTO prov = proveedorDao.buscarPorId(dto.getIdProveedor());
            if (prov == null) {
                errores.add("El proveedor seleccionado no existe.");
            }
        }

        if (esNuevo && !esVacio(dto.getCodigo())) {
            ProductoDTO existente = productoDao.buscarPorCodigo(dto.getCodigo());
            if (existente != null) {
                errores.add("Ya existe un producto con el código especificado.");
            }
        }

        // validaciones para edición
        if (!esNuevo && dto.getIdProducto() <= 0) {
            errores.add("El ID del producto no es válido para edición.");
        }

        return errores;
    }

    public List<String> crearProducto(ProductoDTO dto) {
        List<String> errores = validarProducto(dto, true);
        if (!errores.isEmpty()) {
            return errores;
        }

        boolean ok = productoDao.insertar(dto);

        if (!ok) {
            errores.add("Error al guardar el producto en la base de datos.");
            return errores;
        }

        return errores;
    }

    public List<String> actualizarProducto(ProductoDTO dto) {
        List<String> errores = validarProducto(dto, false);
        if (!errores.isEmpty()) {
            return errores;
        }

        boolean ok = productoDao.actualizar(dto);

        if (!ok) {
            errores.add("Error al actualizar el producto en la base de datos.");
        }

        return errores;
    }

    public List<String> eliminarProducto(ProductoDTO dto) {
        List<String> errores = new ArrayList<>();

        if (dto == null || dto.getIdProducto() <= 0) {
            errores.add("El ID del producto no es válido para eliminar.");
            return errores;
        }

        boolean ok = productoDao.eliminar(dto.getIdProducto());

        if (!ok) {
            errores.add("Error al eliminar el producto en la base de datos.");
        }

        return errores;
    }

    private boolean esVacio(String s) {
        return s == null || s.trim().isEmpty();
    }

    public boolean eliminarProducto(int id) {
        if (id <= 0) return false;
        return productoDao.eliminar(id);
    }

    public ProductoDTO obtenerProductoPorId(int id) {
        if (id <= 0) return null;
        return productoDao.buscarPorId(id);
    }

    public ProductoDTO buscarPorCodigo(String codigo) {
        if (esVacio(codigo)) return null;
        return productoDao.buscarPorCodigo(codigo);
    }

    public List<ProductoDTO> listarTodos() {
        return productoDao.listarTodos();
    }

    public List<ProductoDTO> listarPorCategoria(String categoria) {
        return productoDao.buscarPorCategoria(categoria);
    }

    public List<ProductoDTO> listarAgotados() {
        return productoDao.listarAgotados();
    }

    public List<ProductoDTO> listarStockBajo(int umbral) {
        return productoDao.listarStockBajo(umbral);
    }

    public double valorTotalInventario() {
        return productoDao.calcularValorTotalInventario();
    }
}
