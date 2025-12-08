/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author rodol
 */
public abstract class BaseControlador<V>{
    
    protected V vista;
    
    public BaseControlador(V vista) {
        this.vista = vista;
    }
    
    public void mostrarError(String mensaje) {
        if (vista instanceof JFrame) {
            JOptionPane.showMessageDialog(
                (JFrame) vista,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
 
    public void mostrarMensaje(String mensaje, String titulo) {
        if (vista instanceof JFrame) {
            JOptionPane.showMessageDialog(
                (JFrame) vista,
                mensaje,
                titulo,
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public boolean confirmar(String mensaje, String titulo) {
        if (vista instanceof JFrame) {
            int result = JOptionPane.showConfirmDialog(
                (JFrame) vista,
                mensaje,
                titulo,
                JOptionPane. YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            return result == JOptionPane.YES_OPTION;
        }
        return false;
    }

    public boolean validarCampoVacio(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            mostrarError("El campo '" + nombreCampo + "' es obligatorio");
            return false;
        }
        return true;
    }

    public boolean validarLongitudMinima(String valor, String nombreCampo, int longitudMinima) {
        if (valor. trim().length() < longitudMinima) {
            mostrarError("El campo '" + nombreCampo + "' debe tener al menos " + longitudMinima + " caracteres");
            return false;
        }
        return true;
    }
}
