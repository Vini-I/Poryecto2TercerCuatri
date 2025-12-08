/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.servicios;

import Exceptions.LogicaNegocioException;
import Exceptions.ValidacionException;
import modelo.daos.VentaDAO;
import modelo.daos.DetalleVentaDAO;
import modelo.daos.ProductoDAO;
import modelo.daos.ClienteDAO;
import modelo. dtos.VentaDTO;
import modelo.dtos.DetalleVentaDTO;
import modelo.dtos.ProductoDTO;
import modelo.dtos. ClienteDTO;
import modelo.Venta;
import modelo.DetalleVenta;
import modelo.Cliente;
import modelo.Producto;
import modelo.mappers.VentaMapper;
import modelo.mappers.DetalleVentaMapper;
import modelo.mappers.ClienteMapper;
import modelo.mappers.ProductoMapper;
import modelo.observer.Observable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
/**
 *
 * @author rodol
 */
public class VentaServicio extends Observable<Venta>{
    
    private static VentaServicio instance;
    private VentaDAO ventaDAO;
    private DetalleVentaDAO detalleDAO;
    private ProductoDAO productoDAO;
    private ClienteDAO clienteDAO;
    
    private VentaServicio() {
        this.ventaDAO = new VentaDAO();
        this.detalleDAO = new DetalleVentaDAO();
        this.productoDAO = new ProductoDAO();
        this.clienteDAO = new ClienteDAO();
    }
    
    public static VentaServicio getInstance(){
        if(instance == null){
            instance = new VentaServicio();
        }
        return instance;
    }
    
    public Venta registrarVenta(Integer cedula, List<DetalleVenta> detalles) 
            throws ValidacionException, LogicaNegocioException {

        validarCedula(cedula);
        validarDetallesVenta(detalles);
        validarCliente(cedula);
        validarStock(detalles);

        VentaDTO ventaDTO = new VentaDTO();
        ventaDTO.setClienteId(cedula);
        ventaDTO.setFecha(LocalDateTime.now());
        ventaDTO.setEstado("COMPLETADA");

        int ventaId = ventaDAO.insertarObtenerId(ventaDTO);
        
        if (ventaId <= 0) {
            throw new LogicaNegocioException("Error al registrar la venta en la base de datos");
        }

        for (DetalleVenta detalle : detalles) {
            DetalleVentaDTO detalleDTO = new DetalleVentaDTO();
            detalleDTO.setVentaId(ventaId);
            detalleDTO.setProductoId(detalle.getProducto().getIdProducto());
            detalleDTO.setCantidad(detalle.getCantidad());
            detalleDTO.setPrecioUnitario(detalle.getPrecioUnitario());
            
            boolean insertado = detalleDAO. insertar(detalleDTO);
            
            if (!insertado) {
                throw new LogicaNegocioException("Error al registrar detalle de venta");
            }
        }

        Venta venta = obtenerVentaCompleta(ventaId);

        notificarAgregar(venta);
        
        System.out.println("Venta registrada exitosamente - ID: " + ventaId);
        
        return venta;
    }

    public boolean actualizarEstadoVenta(int ventaId, String nuevoEstado) 
            throws ValidacionException, LogicaNegocioException {

        if (! nuevoEstado.equals("COMPLETADA") && !nuevoEstado.equals("PENDIENTE") && ! nuevoEstado.equals("ANULADA")) {
            throw new ValidacionException("Estado inválido. Use: COMPLETADA, PENDIENTE o ANULADA");
        }

        VentaDTO ventaDTO = ventaDAO.buscarPorId(ventaId);
        if (ventaDTO == null) {
            throw new LogicaNegocioException("La venta ID " + ventaId + " no existe");
        }

        ventaDTO.setEstado(nuevoEstado);
        boolean actualizado = ventaDAO.actualizar(ventaDTO);
        
        if (actualizado) {
            Venta venta = obtenerVentaCompleta(ventaId);
            notificarActualizar(venta);
        }
        
        return actualizado;
    }
    
  
    public boolean anularVenta(int ventaId) throws LogicaNegocioException {
        VentaDTO ventaDTO = ventaDAO.buscarPorId(ventaId);
        
        if (ventaDTO == null) {
            throw new LogicaNegocioException("La venta ID " + ventaId + " no existe");
        }
        
        if (ventaDTO.getEstado().equals("ANULADA")) {
            throw new LogicaNegocioException("La venta ya está anulada");
        }
        
        ventaDTO. setEstado("ANULADA");
        boolean actualizado = ventaDAO.actualizar(ventaDTO);
        
        if (actualizado) {
            notificarActualizar(VentaMapper.toEntity(ventaDTO, null));
        }
        
        return actualizado;
    }
    

    public boolean eliminarVenta(int ventaId) throws LogicaNegocioException {
        VentaDTO ventaDTO = ventaDAO.buscarPorId(ventaId);
        
        if (ventaDTO == null) {
            throw new LogicaNegocioException("La venta ID " + ventaId + " no existe");
        }
        
        boolean eliminado = ventaDAO.eliminar(ventaId);
        
        if (eliminado) {
            notificarEliminar(ventaId);
        }
        
        return eliminado;
    }
    
    public List<VentaDTO> buscar(String criterio) throws SQLException {
        if (criterio == null || criterio.trim().isEmpty()) {
            return obtenerTodos();
        }
        
        List<VentaDTO> todasLasVentas = ventaDAO.listarTodos();
        List<VentaDTO> resultados = new ArrayList<>();
        String criterioBusqueda = criterio.toLowerCase();
        for (VentaDTO venta : todasLasVentas) {
            boolean coincide = false;
            
            // Buscar por ID
            if (String.valueOf(venta.getId()). contains(criterioBusqueda)) {
                coincide = true;
            }
            
            // Buscar por Cliente ID
            if (String.valueOf(venta.getClienteId()).contains(criterioBusqueda)) {
                coincide = true;
            }
            
            // Buscar por Estado
            if (venta.getEstado() != null &&
                    venta.getEstado().toLowerCase(). contains(criterioBusqueda)) {
                coincide = true;
            }
            
            if (coincide) {
                resultados.add(venta);
            }
        }
        return resultados;
    }

    public List<VentaDTO> listarTodas() {
        return ventaDAO.listarTodos();
    }
    
    public List<VentaDTO> obtenerTodos() {
        return ventaDAO.listarTodos();
    }
 
    public List<VentaDTO> listarPorCliente(Integer cedula) {
        return ventaDAO.listarPorCliente(cedula);
    }

    public List<VentaDTO> listarPorEstado(String estado) {
        return ventaDAO. listarPorEstado(estado);
    }

    public List<VentaDTO> listarVentasDelDia() {
        return ventaDAO.listarVentasDelDia();
    }

    public Venta obtenerVentaCompleta(int ventaId) throws LogicaNegocioException {

        VentaDTO ventaDTO = ventaDAO.buscarPorId(ventaId);
        
        if (ventaDTO == null) {
            throw new LogicaNegocioException("La venta ID " + ventaId + " no existe");
        }

        ClienteDTO clienteDTO = clienteDAO.buscarPorId(ventaDTO.getClienteId());
        Cliente cliente = ClienteMapper.toEntity(clienteDTO);

        Venta venta = VentaMapper.toEntity(ventaDTO, cliente);

        List<DetalleVentaDTO> detallesDTO = detalleDAO.listarPorVenta(ventaId);
        
        for (DetalleVentaDTO detalleDTO : detallesDTO) {
            ProductoDTO productoDTO = productoDAO.buscarPorId(detalleDTO.getProductoId());
            Producto producto = ProductoMapper.toEntity(productoDTO);
            
            DetalleVenta detalle = DetalleVentaMapper.toEntity(detalleDTO, venta, producto);
            venta.agregarDetalle(detalle);
        }
        
        return venta;
    }
    
    private void validarDetallesVenta(List<DetalleVenta> detalles) throws ValidacionException {
        if (detalles == null || detalles.isEmpty()) {
            throw new ValidacionException("La venta debe tener al menos un producto");
        }
        
        for (int i = 0; i < detalles.size(); i++) {
            DetalleVenta detalle = detalles.get(i);
            
            if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
                throw new ValidacionException("La cantidad del producto " + (i+1) + " debe ser mayor a 0");
            }
            
            if (detalle.getPrecioUnitario() == null || detalle.getPrecioUnitario() <= 0) {
                throw new ValidacionException("El precio del producto " + (i + 1) + " debe ser mayor a 0");
            }

            if (detalle.getProducto() == null) {
                throw new ValidacionException("El producto " + (i + 1) + " no es válido");
            }

            if (detalle.getProducto().getIdProducto() <= 0) {
                throw new ValidacionException("El producto " + (i + 1) + " no tiene un ID válido");
            }
        }
    }
    
    private void validarCedula(Integer cedula) throws ValidacionException {
        if (cedula == null) {
            throw new ValidacionException("La cédula del cliente es obligatoria");
        }
        
        if (cedula <= 0) {
            throw new ValidacionException("La cédula del cliente no es válida");
        }
    }
    
  
    private ClienteDTO validarCliente(Integer cedula) throws LogicaNegocioException {
        ClienteDTO cliente = clienteDAO.buscarPorId(cedula);
        
        if (cliente == null) {
            throw new LogicaNegocioException("El cliente con cédula " + cedula + " no existe");
        }
        
        return cliente;
    }

    private void validarStock(List<DetalleVenta> detalles) throws LogicaNegocioException {
        for (DetalleVenta detalle : detalles) {
            ProductoDTO producto = productoDAO.buscarPorId(detalle.getProducto().getIdProducto());
            
            if (producto == null) {
                throw new LogicaNegocioException("El producto ID " + detalle.getProducto().getIdProducto() + " no existe");
            }
            
            if (producto.getStock() < detalle.getCantidad()) {
                throw new LogicaNegocioException(
                    "Stock insuficiente para: " + producto.getNombre() + "\n" +
                    "Disponible: " + producto.getStock() + " unidades\n" +
                    "Solicitado: " + detalle.getCantidad() + " unidades"
                );
            }
        }
    }
}
