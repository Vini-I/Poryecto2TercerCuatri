/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import GUI.PnlVentas;
import java.util.List;
import modelo.dtos.VentaDTO;
import modelo.servicios.VentaServicio;

/**
 *
 * @author rodol
 */
public class VentaControlador extends BaseControlador<PnlVentas> {
    private VentaServicio ventaServicio;
    
    public VentaControlador(PnlVentas vista) {
        super(vista);
        this.ventaServicio = VentaServicio.getInstance();
    }
    public void cargarVentas() {
        try {
            List<VentaDTO> ventas = ventaServicio.obtenerTodos();
            vista.actualizarTabla(ventas);
        } catch (Exception e) {
            mostrarError("Error al cargar ventas: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
      public void buscarVentas(String criterio) {
        try {
            if (criterio == null || criterio.trim().isEmpty()) {
                cargarVentas();
                return;
            }
            
            List<VentaDTO> ventas = ventaServicio.buscar(criterio);
            vista.actualizarTabla(ventas);
        } catch (Exception e) {
            mostrarError("Error al buscar ventas: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
     public void eliminarVenta(VentaDTO venta) {
        if (venta == null) {
            mostrarError("Seleccione una venta de la tabla");
            return;
        }
        
        boolean confirma = confirmar(
            "¿Está seguro de eliminar la venta #" + venta.getId() + "?",
            "Confirmar Eliminación"
        );
        
        if (!confirma) {
            System.out.println("Eliminación cancelada");
            return;
        }
        
        try {
            System.out.println("Llamando a ventaServicio.eliminar(" + venta.getId() + ")");
            boolean resultado = ventaServicio.eliminarVenta(venta.getId());
            System.out.println("Resultado: " + resultado);
            
            if (resultado) {
                mostrarMensaje("Venta eliminada exitosamente", "Éxito");
                cargarVentas();
            }
        } catch (Exception e) {
            mostrarError("Error al eliminar venta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
}
