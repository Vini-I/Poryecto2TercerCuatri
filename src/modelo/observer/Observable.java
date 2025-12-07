/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.observer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodol
 */
public abstract class Observable<T> {
        private List<IObserver<T>> observers = new ArrayList<>();
        
        public void agregarObserver(IObserver<T> observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Se agregaron observers " + observers.size());
        }
    }
 
    public void removerObserver(IObserver<T> observer) {
        observers.remove(observer);
    }

    public void notificarAgregar(T elemento) {
        System.out.println("📢 Notificando AGREGAR a " + observers.size() + " observers");
        for (IObserver<T> observer : observers) {
            observer.Agregar(elemento);
        }
    }

    public void notificarActualizar(T elemento) {
        System.out.println("📢 Notificando ACTUALIZAR a " + observers.size() + " observers");
        for (IObserver<T> observer : observers) {
            observer.Actualizar(elemento);
        }
    }

    public void notificarEliminar(int id) {
        System.out.println("📢 Notificando ELIMINAR a " + observers.size() + " observers");
        for (IObserver<T> observer : observers) {
            observer.Eliminar(id);
        }
    }
  
    public void notificarCambio() {
        System.out.println("📢 Notificando CAMBIO GENERAL a " + observers.size() + " observers");
        for (IObserver<T> observer : observers) {
            observer.Cambio();
        }
    }

    public int contarObservers() {
        return observers.size();
    }
        
        
        
        
        
        
        
        
        
        
        
        
}
