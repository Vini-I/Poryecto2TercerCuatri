package modelo.servicios;

import java.util.ArrayList;
import java.util.List;

import modelo.Producto;
import modelo.Proveedor;
import modelo.daos.IProductoDAO;
import modelo.daos.IProductoDAO;
import modelo.daos.IProveedorDAO;
import modelo.daos.IProveedorDAO;
import modelo.dtos.ProductoDTO;
import modelo.dtos.ProveedorDTO;
import modelo.mappers.ProductoMapper;

public class ProductoService {

    private final IProductoDAO productoDao;
    private final IProveedorDAO proveedorDao;

    public ProductoService(IProductoDAO productoDao, IProveedorDAO proveedorDao) {
        this.productoDao = productoDao;
        this.proveedorDao = proveedorDao;
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

        dto.setIdProducto(dto.getIdProducto());
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

    private boolean esVacio(String s) {
        return s == null || s.trim().isEmpty();
    }
}

