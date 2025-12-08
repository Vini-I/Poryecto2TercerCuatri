package modelo.daos;

import modelo.dtos.ProveedorDTO;

import java.util.List;

public interface IProveedorDAO extends IDao<ProveedorDTO> {

    List<ProveedorDTO> buscarPorNombre(String nombre);
}
