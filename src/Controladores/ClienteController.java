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
        Cliente cliente = ClienteMapper.toEntity(dto);
        return clienteDao.insertar(cliente);
    }

    // Actualizar cliente
    public boolean actualizarCliente(ClienteDTO dto) {
        Cliente cliente = ClienteMapper.toEntity(dto);
        return clienteDao.actualizar(cliente);
    }

    // Eliminar cliente por id
    public boolean eliminarCliente(int id) {
        return clienteDao.eliminar(id);
    }

    // Obtener cliente por id (numeroCompras se deja en 0 por ahora)
    public ClienteDTO obtenerClientePorId(int id) {
        Cliente cliente = clienteDao.buscarPorId(id);
        if (cliente == null) {
            return null;
        }
        return ClienteMapper.toDto(cliente);
    }

    // Buscar cliente por cédula
    public ClienteDTO buscarPorCedula(String cedula) {
        Cliente cliente = clienteDao.buscarPorCedula(cedula);
        if (cliente == null) {
            return null;
        }
        return ClienteMapper.toDto(cliente);
    }

    // Buscar clientes por nombre
    public List<ClienteDTO> buscarPorNombre(String nombre) {
        List<Cliente> clientes = clienteDao.buscarPorNombre(nombre);
        List<ClienteDTO> dtos = new ArrayList<>();

        for (Cliente c : clientes) {
            dtos.add(ClienteMapper.toDto(c));
        }

        return dtos;
    }

    // Listar todos los clientes
    public List<ClienteDTO> listarTodos() {
        List<Cliente> clientes = clienteDao.listarTodos();
        List<ClienteDTO> dtos = new ArrayList<>();

        for (Cliente c : clientes) {
            dtos.add(ClienteMapper.toDto(c));
        }

        return dtos;
    }
}
