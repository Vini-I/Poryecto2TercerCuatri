package modelo.daos;

import modelo.Proveedor;

import java.util.List;

public interface IProveedorDAO extends IDao<Proveedor> {

    List<Proveedor> buscarPorNombre(String nombre);
}
