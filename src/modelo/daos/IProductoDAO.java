package modelo.daos;

import modelo.dtos.ProductoDTO;

import java.util.List;

public interface IProductoDAO extends IDao<ProductoDTO> {

    ProductoDTO buscarPorCodigo(String codigo);

    List<ProductoDTO> buscarPorCategoria(String categoria);

    List<ProductoDTO> listarAgotados();

    List<ProductoDTO> listarStockBajo(int umbral);

    double calcularValorTotalInventario();
}
