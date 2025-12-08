/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.servicios;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf. PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml. transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml. transform.dom.DOMSource;
import javax.xml.transform. stream.StreamResult;
import java.sql.*;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import modelo.Venta;
import modelo.daos.VentaDAO;
import modelo.dtos.DetalleVentaDTO;
import modelo.dtos.DetalleVentaFacturaDTO;
import modelo.dtos.VentaDTO;
import modelo.dtos.VentaFacturaDTO;

/**
 *
 * @author rodol
 */
public class FacturaServicio {
    private VentaDAO ventaDAO;

    public FacturaServicio() {
        this.ventaDAO = new VentaDAO();
    } 
    
    public void generarPDF(int idVenta, String rutaArchivo) throws SQLException, IOException {

        VentaFacturaDTO ventaFactura = ventaDAO.obtenerParaFactura(idVenta);
        
        if (ventaFactura == null) {
            throw new IllegalArgumentException("Venta no encontrada");
        }

        double subtotal = calcularSubtotal(ventaFactura);
        double impuesto = subtotal * 0.13;
        double total = subtotal + impuesto;

        PdfWriter writer = new PdfWriter(rutaArchivo);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("FACTURA #" + ventaFactura.getVentaId())
            .setFontSize(20)
            .setTextAlignment(TextAlignment.CENTER));
        
        document.add(new Paragraph("\n"));

        DateTimeFormatter formatter = DateTimeFormatter. ofPattern("dd/MM/yyyy HH:mm");
        document. add(new Paragraph("Fecha: " + ventaFactura. getFecha().format(formatter)));
        document.add(new Paragraph("Cliente: " + ventaFactura.getClienteNombre()));
        document.add(new Paragraph("Cédula: " + ventaFactura.getClienteCedula()));
        document.add(new Paragraph("Dirección: " + ventaFactura.getClienteDireccion()));
        document. add(new Paragraph("Teléfono: " + ventaFactura.getClienteTelefono()));
        document.add(new Paragraph("\n"));
 
        Table table = new Table(new float[]{4, 2, 2, 2});
        table.setWidth(500);
        
        table.addHeaderCell("Producto");
        table.addHeaderCell("Cantidad");
        table.addHeaderCell("Precio Unit.");
        table.addHeaderCell("Subtotal");
        
        for (DetalleVentaFacturaDTO detalle : ventaFactura.getDetalles()) {
            double subtotalDetalle = detalle.getCantidad() * detalle.getPrecioUnitario();
            
            table.addCell(detalle.getProductoNombre());
            table.addCell(String. valueOf(detalle.getCantidad()));
            table.addCell(String.format("¢%.2f", detalle.getPrecioUnitario()));
            table.addCell(String.format("¢%.2f", subtotalDetalle));
        }
        
        document.add(table);
        document.add(new Paragraph("\n"));

        document. add(new Paragraph("Subtotal: ¢" + String.format("%.2f", subtotal))
            .setTextAlignment(TextAlignment.RIGHT));
        
        document.add(new Paragraph("IVA (13%): ¢" + String.format("%.2f", impuesto))
            . setTextAlignment(TextAlignment.RIGHT));
 
        document.add(new Paragraph("TOTAL: ¢" + String.format("%.2f", total))
                .setTextAlignment(TextAlignment.RIGHT)
                .setFontSize(14));

        document.close();
    }

    private double calcularSubtotal(VentaFacturaDTO ventaFactura) {
        double subtotal = 0.0;
        for (DetalleVentaFacturaDTO detalle : ventaFactura.getDetalles()) {
            subtotal += detalle.getCantidad() * detalle.getPrecioUnitario();
        }
        return subtotal;
    }

}
