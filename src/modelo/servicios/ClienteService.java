package modelo.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import modelo.daos.IClienteDAO;
import modelo.daos.ClienteDAO;
import modelo.dtos.ClienteDTO;

public class ClienteService {

    private final IClienteDAO clienteDao;
    private final Pattern emailPattern =
            Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    public ClienteService() {
        this.clienteDao = new ClienteDAO();
    }

    public List<String> validarCliente(ClienteDTO dto, boolean esNuevo) {
        List<String> errores = new ArrayList<>();

        if (dto == null) {
            errores.add("El cliente no puede ser nulo.");
            return errores;
        }

        if (dto.getCedula() <= 0) {
            errores.add("La cédula es obligatoria.");
        } else if (Integer.toString(dto.getCedula()).length() > 20) {
            errores.add("La cédula no puede superar 20 caracteres.");
        } else if (esNuevo) {
            ClienteDTO existente = clienteDao.buscarPorId(dto.getCedula());
            if (existente != null) {
                errores.add("Ya existe un cliente con la cédula indicada.");
            }
        }

        if (esVacio(dto.getNombreCompleto())) {
            errores.add("El nombre del cliente es obligatorio.");
        } else if (dto.getNombreCompleto().length() > 150) {
            errores.add("El nombre del cliente no puede superar 150 caracteres.");
        }

        if (dto.getDireccion() != null && dto.getDireccion().length() > 200) {
            errores.add("La dirección no puede superar 200 caracteres.");
        }

        if (dto.getTelefono() != null && dto.getTelefono().length() > 50) {
            errores.add("El teléfono no puede superar 50 caracteres.");
        }

        if (dto.getEmail() != null && dto.getEmail().length() > 150) {
            errores.add("El correo no puede superar 150 caracteres.");
        } else if (!esVacio(dto.getEmail()) &&
                   !emailPattern.matcher(dto.getEmail()).matches()) {
            errores.add("El formato del correo electrónico no es válido.");
        }

        if (!esNuevo && dto.getCedula() <= 0) {
            errores.add("El ID del cliente no es válido para edición.");
        }

        return errores;
    }

    public List<String> crearCliente(ClienteDTO dto) {
        List<String> errores = validarCliente(dto, true);
        if (!errores.isEmpty()) {
            return errores;
        }

        boolean ok = clienteDao.insertar(dto);

        if (!ok) {
            errores.add("Error al guardar el cliente en la base de datos.");
            return errores;
        }

        return errores;
    }

    public List<String> actualizarCliente(ClienteDTO dto) {
        List<String> errores = validarCliente(dto, false);
        if (!errores.isEmpty()) {
            return errores;
        }

        boolean ok = clienteDao.actualizar(dto);

        if (!ok) {
            errores.add("Error al actualizar el cliente en la base de datos.");
        }

        return errores;
    }

    public List<String> eliminarCliente(ClienteDTO dto) {
        List<String> errores = new ArrayList<>();

        if (dto == null || dto.getCedula() <= 0) {
            errores.add("El ID del cliente no es válido para eliminar.");
            return errores;
        }

        boolean ok = clienteDao.eliminar(dto.getCedula());

        if (!ok) {
            errores.add("Error al eliminar el cliente en la base de datos.");
        }

        return errores;
    }

    public boolean eliminarCliente(int id) {
        if (id <= 0) return false;
        return clienteDao.eliminar(id);
    }

    public ClienteDTO obtenerClientePorId(int id) {
        if (id <= 0) return null;
        return clienteDao.buscarPorId(id);
    }

    public List<ClienteDTO> buscarPorNombre(String nombre) {
        return clienteDao.buscarPorNombre(nombre);
    }

    public List<ClienteDTO> listarTodos() {
        return clienteDao.listarTodos();
    }

    private boolean esVacio(String s) {
        return s == null || s.trim().isEmpty();
    }
}
