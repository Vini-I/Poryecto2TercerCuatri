package controladores;

import java.util.ArrayList;
import java.util.List;

import modelo.Cliente;
import modelo.daos.IClienteDAO;
import modelo.dtos.ClienteDTO;
import modelo.mappers.ClienteMapper;

public class ClienteController {

    private final IClienteDAO clienteDao;

    public ClienteController(IClienteDAO clienteDao) {
        this.clienteDao = clienteDao;
    }

    // Crear cliente (sin numeroCompras)
    public boolean crearCliente(ClienteDTO dto) {
        return clienteDao.insertar(dto);
    }

    // Actualizar cliente
    public boolean actualizarCliente(ClienteDTO dto) {
        return clienteDao.actualizar(dto);
    }

    // Eliminar cliente por id
    public boolean eliminarCliente(int id) {
        return clienteDao.eliminar(id);
    }

    // Obtener cliente por id (numeroCompras se deja en 0 por ahora)
    public ClienteDTO obtenerClientePorId(int id) {
        ClienteDTO cliente = clienteDao.buscarPorId(id);
        if (cliente == null) {
            return null;
        }
        return cliente;
    }

    // Buscar clientes por nombre
    public List<ClienteDTO> buscarPorNombre(String nombre) {
        List<ClienteDTO> clientes = clienteDao.buscarPorNombre(nombre);
        List<ClienteDTO> dtos = new ArrayList<>();

        for (ClienteDTO c : clientes) {
            dtos.add(c);
        }

        return dtos;
    }

    // Listar todos los clientes
    public List<ClienteDTO> listarTodos() {
        List<ClienteDTO> clientes = clienteDao.listarTodos();
        List<ClienteDTO> dtos = new ArrayList<>();

        for (ClienteDTO c : clientes) {
            dtos.add(c);
        }

        return dtos;
    }
}
