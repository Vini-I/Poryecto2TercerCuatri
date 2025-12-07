package modelo.mappers;

import modelo.Proveedor;
import modelo.dtos.ProveedorDTO;

public class ProveedorMapper {

    private ProveedorMapper() {
        // Utilidad, no instanciable
    }

    public static ProveedorDTO toDto(Proveedor proveedor) {
        if (proveedor == null) {
            return null;
        }

        ProveedorDTO dto = new ProveedorDTO();
        dto.setIdProveedor(proveedor.getIdProveedor());
        dto.setNombre(proveedor.getNombre());
        dto.setContacto(proveedor.getContacto());
        dto.setDireccion(proveedor.getDireccion());
        return dto;
    }

    // DTO -> Entity (ignora cantidadProductos)
    public static Proveedor toEntity(ProveedorDTO dto) {
        if (dto == null) {
            return null;
        }

        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(dto.getIdProveedor());
        proveedor.setNombre(dto.getNombre());
        proveedor.setContacto(dto.getContacto());
        proveedor.setDireccion(dto.getDireccion());
        return proveedor;
    }
}

