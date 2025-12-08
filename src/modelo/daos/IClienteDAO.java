package modelo.daos;

import modelo.dtos.ClienteDTO;

import java.util.List;

public interface IClienteDAO extends IDao<ClienteDTO> {

    ClienteDTO buscarPorCedula(String cedula);

    List<ClienteDTO> buscarPorNombre(String nombre);
}
