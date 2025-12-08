/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.util.ArrayList;
import java.util.List;
import modelo.DetalleVenta;
import modelo.Producto;
import modelo.daos.ProductoDAO;
import modelo.dtos.ProductoDTO;
import modelo.servicios.VentaServicio;

/**
 *
 * @author rodol
 */
public class VentaDialogAyuda {
    private DialogVenta dialog;
    private VentaServicio ventaServicio;
    private ProductoDAO productoServicio;
    
    public VentaDialogAyuda(DialogVenta dialog) {
        this.dialog = dialog;
        this.ventaServicio = VentaServicio.getInstance();
        this.productoServicio = new ProductoDAO();
    }
    
    public void cargarProductos() {
        try {
            List<ProductoDTO> productos = productoServicio.listarTodos();
            dialog.cargarProductos(productos);
        } catch (Exception e) {
            dialog.mostrarError("Error al cargar productos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
     public void guardarVenta(int cedulaCliente, int productoId, int cantidad, double precioUnitario) {
        DetalleVenta detalle = new DetalleVenta();
        
        Producto producto = new Producto();
        producto.setIdProducto(productoId);
        detalle.setProducto(producto);
        
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(precioUnitario);
  
        List<DetalleVenta> detalles = new ArrayList<>();
        detalles. add(detalle);
 
        try {
            ventaServicio.registrarVenta(cedulaCliente, detalles);
            
            System.out.println("✅ Venta registrada exitosamente");
            dialog.ventaGuardadaExitosamente();
            
        } catch (Exception e) {
            System. err.println("❌ Error al registrar venta: " + e.getMessage());
            e. printStackTrace();
            dialog.mostrarError("Error al registrar la venta: " + e.getMessage());
        }
    }
    
    
}
