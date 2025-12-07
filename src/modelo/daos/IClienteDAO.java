package modelo.daos;

import modelo.Cliente;

import java.util.List;

public interface IClienteDAO extends IDao<Cliente> {

    Cliente buscarPorCedula(String cedula);

    List<Cliente> buscarPorNombre(String nombre);
}
