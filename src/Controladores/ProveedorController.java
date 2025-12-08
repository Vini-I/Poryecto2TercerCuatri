package controladores;

import java.util.ArrayList;
import java.util.List;

import modelo.Proveedor;
import modelo.daos.IProveedorDAO;
import modelo.daos.IProductoDAO;
import modelo.dtos.ProveedorDTO;
import modelo.mappers.ProveedorMapper;
import modelo.Producto;

public class ProveedorController {

    private final IProveedorDAO proveedorDao;
    private final IProductoDAO productoDao;

    public ProveedorController(IProveedorDAO proveedorDao, IProductoDAO productoDao) {
        this.proveedorDao = proveedorDao;
        this.productoDao = productoDao;
    }

    // Crear proveedor
    public boolean crearProveedor(ProveedorDTO dto) {
        return proveedorDao.insertar(dto);
    }

    // Actualizar proveedor
    public boolean actualizarProveedor(ProveedorDTO dto) {
        return proveedorDao.actualizar(dto);
    }

    // Eliminar proveedor por id
    public boolean eliminarProveedor(int id) {
        return proveedorDao.eliminar(id);
    }

    // Obtener proveedor por id
    public ProveedorDTO obtenerProveedorPorId(int id) {
        ProveedorDTO proveedor = proveedorDao.buscarPorId(id);
        
        return proveedor;
    }

    // Listar todos los proveedores
    public List<ProveedorDTO> listarTodos() {
        List<ProveedorDTO> dtos = proveedorDao.listarTodos();

        return dtos;
    }

    // Buscar proveedores por nombre
    public List<ProveedorDTO> buscarPorNombre(String nombre) {
        List<ProveedorDTO> dtos = proveedorDao.buscarPorNombre(nombre);

        return dtos;
    }
}
