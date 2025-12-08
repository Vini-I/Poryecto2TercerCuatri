package modelo.servicios;

import java.util.ArrayList;
import java.util.List;

import modelo.daos.IProveedorDAO;
import modelo.daos.ProveedorDAO;
import modelo.dtos.ProveedorDTO;

public class ProveedorService {

    private final IProveedorDAO proveedorDao;

    public ProveedorService() {
        this.proveedorDao = new ProveedorDAO();
    }

    public List<String> validarProveedor(ProveedorDTO dto, boolean esNuevo) {
        List<String> errores = new ArrayList<>();

        if (dto == null) {
            errores.add("El proveedor no puede ser nulo.");
            return errores;
        }

        if (esVacio(dto.getNombre())) {
            errores.add("El nombre del proveedor es obligatorio.");
        } else if (dto.getNombre().length() > 100) {
            errores.add("El nombre del proveedor no puede superar 100 caracteres.");
        }

        if (dto.getContacto() != null && dto.getContacto().length() > 100) {
            errores.add("El contacto del proveedor no puede superar 100 caracteres.");
        }

        if (dto.getDireccion() != null && dto.getDireccion().length() > 200) {
            errores.add("La dirección del proveedor no puede superar 200 caracteres.");
        }

        if (!esNuevo && dto.getIdProveedor() <= 0) {
            errores.add("El ID del proveedor no es válido para edición.");
        }

        return errores;
    }

    public List<String> crearProveedor(ProveedorDTO dto) {
        List<String> errores = validarProveedor(dto, true);
        if (!errores.isEmpty()) {
            return errores;
        }

        boolean ok = proveedorDao.insertar(dto);

        if (!ok) {
            errores.add("Error al guardar el proveedor en la base de datos.");
            return errores;
        }

        return errores;
    }

    public List<String> actualizarProveedor(ProveedorDTO dto) {
        List<String> errores = validarProveedor(dto, false);
        if (!errores.isEmpty()) {
            return errores;
        }

        boolean ok = proveedorDao.actualizar(dto);

        if (!ok) {
            errores.add("Error al actualizar el proveedor en la base de datos.");
        }

        return errores;
    }

    public List<String> eliminarProveedor(ProveedorDTO dto) {
        List<String> errores = new ArrayList<>();

        if (dto == null || dto.getIdProveedor() <= 0) {
            errores.add("El ID del proveedor no es válido para eliminar.");
            return errores;
        }

        boolean ok = proveedorDao.eliminar(dto.getIdProveedor());

        if (!ok) {
            errores.add("Error al eliminar el proveedor en la base de datos.");
        }

        return errores;
    }

    public boolean eliminarProveedor(int id) {
        if (id <= 0) return false;
        return proveedorDao.eliminar(id);
    }

    public ProveedorDTO obtenerProveedorPorId(int id) {
        if (id <= 0) return null;
        return proveedorDao.buscarPorId(id);
    }

    public List<ProveedorDTO> listarTodos() {
        return proveedorDao.listarTodos();
    }

    public List<ProveedorDTO> buscarPorNombre(String nombre) {
        return proveedorDao.buscarPorNombre(nombre);
    }

    private boolean esVacio(String s) {
        return s == null || s.trim().isEmpty();
    }
}
