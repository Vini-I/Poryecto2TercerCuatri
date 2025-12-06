/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.daos;

import java.util.List;

/**
 *
 * @author rodol
 */
public interface IDao<T> {

    boolean insertar(T dto);

    T buscarPorId(int id);

    boolean actualizar(T dto);

    boolean eliminar(int id);
    
    List<T> listarTodos();
}
