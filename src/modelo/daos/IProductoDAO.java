package modelo.daos;

import modelo.Producto;

import java.util.List;

public interface IProductoDAO extends IDao<Producto> {

    Producto buscarPorCodigo(String codigo);

    List<Producto> buscarPorCategoria(String categoria);

    List<Producto> listarAgotados();

    List<Producto> listarStockBajo(int umbral);

    double calcularValorTotalInventario();
}
