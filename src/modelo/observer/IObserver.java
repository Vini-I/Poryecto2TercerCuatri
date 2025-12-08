/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.observer;

/**
 *
 * @author rodol
 */
public interface IObserver <T> {
    void Agregar(T t);
    
    void Actualizar(T t);

    void Eliminar(int id);

    void Cambio();
}
