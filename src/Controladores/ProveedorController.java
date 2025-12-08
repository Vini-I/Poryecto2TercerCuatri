package controladores;

import Controladores.BaseControlador;
import modelo.dtos.ProveedorDTO;
import modelo.servicios.ProveedorService;

import javax.swing.*;
import java.util.List;

public class ProveedorController extends BaseControlador {

    private final ProveedorService proveedorService;

    public ProveedorController(JDialog vista) {
        super(vista);
        this.proveedorService = new ProveedorService();
    }

    public List<String> crearProveedor(ProveedorDTO dto) {
        return proveedorService.crearProveedor(dto);
    }

    public List<String> actualizarProveedor(ProveedorDTO dto) {
        return proveedorService.actualizarProveedor(dto);
    }

    public List<String> eliminarProveedor(ProveedorDTO dto) {
        return proveedorService.eliminarProveedor(dto);
    }

    public boolean eliminarProveedor(int id) {
        return proveedorService.eliminarProveedor(id);
    }

    public ProveedorDTO obtenerProveedorPorId(int id) {
        return proveedorService.obtenerProveedorPorId(id);
    }

    public List<ProveedorDTO> listarTodos() {
        return proveedorService.listarTodos();
    }

    public List<ProveedorDTO> buscarPorNombre(String nombre) {
        return proveedorService.buscarPorNombre(nombre);
    }
}
