package controladores;

import Controladores.BaseControlador;
import modelo.dtos.ClienteDTO;
import modelo.servicios.ClienteService;

import javax.swing.*;
import java.util.List;

public class ClienteController extends BaseControlador {

    private final ClienteService clienteService;

    public ClienteController(JDialog vista) {
        super(vista);
        this.clienteService = new ClienteService();
    }

    public List<String> crearCliente(ClienteDTO dto) {
        return clienteService.crearCliente(dto);
    }

    public List<String> actualizarCliente(ClienteDTO dto) {
        return clienteService.actualizarCliente(dto);
    }

    public List<String> eliminarCliente(ClienteDTO dto) {
        return clienteService.eliminarCliente(dto);
    }

    public boolean eliminarCliente(int id) {
        return clienteService.eliminarCliente(id);
    }

    public ClienteDTO obtenerClientePorId(int id) {
        return clienteService.obtenerClientePorId(id);
    }

    public List<ClienteDTO> buscarPorNombre(String nombre) {
        return clienteService.buscarPorNombre(nombre);
    }

    public List<ClienteDTO> listarTodos() {
        return clienteService.listarTodos();
    }
}
