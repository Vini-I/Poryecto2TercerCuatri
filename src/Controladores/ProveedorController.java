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
        Proveedor proveedor = ProveedorMapper.toEntity(dto);
        return proveedorDao.insertar(proveedor);
    }

    // Actualizar proveedor
    public boolean actualizarProveedor(ProveedorDTO dto) {
        Proveedor proveedor = ProveedorMapper.toEntity(dto);
        return proveedorDao.actualizar(proveedor);
    }

    // Eliminar proveedor por id
    public boolean eliminarProveedor(int id) {
        return proveedorDao.eliminar(id);
    }

    // Obtener proveedor por id
    public ProveedorDTO obtenerProveedorPorId(int id) {
        Proveedor proveedor = proveedorDao.buscarPorId(id);
        if (proveedor == null) {
            return null;
        }
        
        return ProveedorMapper.toDto(proveedor);
    }

    // Listar todos los proveedores
    public List<ProveedorDTO> listarTodos() {
        List<Proveedor> proveedores = proveedorDao.listarTodos();
        List<ProveedorDTO> dtos = new ArrayList<>();

        for (Proveedor p : proveedores) {
            dtos.add(ProveedorMapper.toDto(p));
        }

        return dtos;
    }

    // Buscar proveedores por nombre
    public List<ProveedorDTO> buscarPorNombre(String nombre) {
        List<Proveedor> proveedores = proveedorDao.buscarPorNombre(nombre);
        List<ProveedorDTO> dtos = new ArrayList<>();

        for (Proveedor p : proveedores) {
            dtos.add(ProveedorMapper.toDto(p));
        }

        return dtos;
    }
}
