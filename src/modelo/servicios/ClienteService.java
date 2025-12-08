package modelo.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import modelo.Cliente;
import modelo.daos.IClienteDAO;
import modelo.dtos.ClienteDTO;
import modelo.mappers.ClienteMapper;

public class ClienteService {

    private final IClienteDAO clienteDao;
    private final Pattern emailPattern =
            Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    public ClienteService(IClienteDAO clienteDao) {
        this.clienteDao = clienteDao;
    }

    public List<String> validarCliente(ClienteDTO dto, boolean esNuevo) {
        List<String> errores = new ArrayList<>();

        if (dto == null) {
            errores.add("El cliente no puede ser nulo.");
            return errores;
        }

        if (esVacio(dto.getCedula())) {
            errores.add("La cédula es obligatoria.");
        } else if (Integer.toString(dto.getCedula()).length() > 20) {
            errores.add("La cédula no puede superar 20 caracteres.");
        } else if (esNuevo) {
            Cliente existente = ClienteMapper.toEntity(clienteDao.buscarPorId(dto.getCedula()));
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
        } else if (!esVacio(dto.getEmail()) && !emailPattern.matcher(dto.getEmail()).matches()) {
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

        dto.setCedula(dto.getCedula());
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

    private boolean esVacio(String s) {
        return s == null || s.trim().isEmpty();
    }

    private boolean esVacio(Integer i) {
        return i == null;
    }
}
