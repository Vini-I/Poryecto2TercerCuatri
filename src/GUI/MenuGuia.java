/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import javax.swing.JOptionPane;
import modelo.servicios.SessionManager;

/**
 *
 * @author rodol
 */
public class MenuGuia {
     private FrmMenu menu;
     
      public MenuGuia(FrmMenu menu) {
        this.menu = menu;
    }
     
     public void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(
            menu,
            "¿Está seguro de que desea cerrar sesión? ",
            "Confirmar Cierre de Sesión",
            JOptionPane.YES_NO_OPTION,
            JOptionPane. QUESTION_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            SessionManager. getInstance().cerrarSesion();

            menu.dispose();

            FrmLogin login = new FrmLogin();
            login.setVisible(true);
        }
    }
     
     
     
     
     
     
     
     
     
     
     
}
