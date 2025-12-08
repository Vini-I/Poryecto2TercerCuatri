/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import GUI.PnlFacturas;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.dtos.VentaDTO;
import modelo.servicios.FacturaServicio;
import modelo.servicios.VentaServicio;

/**
 *
 * @author rodol
 */
public class FacturaControlador extends BaseControlador<PnlFacturas> {
    private VentaServicio ventaServicio;
    private FacturaServicio facturaServicio;
    
    public FacturaControlador(PnlFacturas vista) {
        super(vista);
        this.ventaServicio = new VentaServicio();
        this.facturaServicio = new FacturaServicio();
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
    
    public void generarFactura(VentaDTO venta) {
        if (venta == null) {
            mostrarError("Seleccione una venta de la tabla");
            return;
        }
        
        System.out.println("=== GENERAR FACTURA ===");
        System.out.println("Venta ID: " + venta. getId());
        System.out. println("Cliente ID: " + venta.getClienteId());

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Factura PDF");
        fileChooser. setSelectedFile(new File("Factura_" + venta.getId() + ".pdf"));
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos PDF (*.pdf)", "pdf");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showSaveDialog(null);
        
        if (result == JFileChooser. APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                String path = file.getAbsolutePath();

                if (! path.toLowerCase().endsWith(".pdf")) {
                    path += ".pdf";
                }
                
                System. out.println("Generando PDF en: " + path);

                facturaServicio. generarPDF(venta. getId(), path);
                
                System.out.println("✅ PDF generado exitosamente");
                
                mostrarMensaje("Factura generada exitosamente", "Éxito");

                boolean abrir = confirmar(
                    "¿Desea abrir la factura generada? ",
                    "Abrir Factura"
                );
                
                if (abrir) {
                    abrirPDF(path);
                }
                
            } catch (Exception e) {
                System.err.println("❌ Error al generar factura: " + e.getMessage());
                mostrarError("Error al generar factura: " + e. getMessage());
                e.printStackTrace();
            }
        } else {
            System.out. println("Generación de factura cancelada");
        }
    }
    
    private void abrirPDF(String path) {
        try {
            File pdfFile = new File(path);
            
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop desktop = java. awt.Desktop.getDesktop();
                if (pdfFile.exists()) {
                    desktop.open(pdfFile);
                    System.out.println("✅ PDF abierto: " + path);
                }
            } else {
                mostrarError("No se puede abrir el PDF automáticamente en este sistema");
            }
            
        } catch (Exception e) {
            System.err.println("Error al abrir PDF: " + e.getMessage());
            mostrarError("No se pudo abrir el PDF: " + e.getMessage());
        }
    }
}
