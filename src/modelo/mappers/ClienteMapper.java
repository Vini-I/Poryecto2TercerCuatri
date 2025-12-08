package modelo.mappers;

import modelo.Cliente;
import modelo.dtos.ClienteDTO;

public class ClienteMapper {

    private ClienteMapper() {
        // Utilidad, no instanciable
    }

    public static ClienteDTO toDto(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        ClienteDTO dto = new ClienteDTO();
        dto.setCedula(cliente.getCedula());
        dto.setNombreCompleto(cliente.getNombreCompleto());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        dto.setEmail(cliente.getEmail());
        return dto;
    }

    // DTO -> Entity (ignora numeroCompras)
    public static Cliente toEntity(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setCedula(dto.getCedula());
        cliente.setNombreCompleto(dto.getNombreCompleto());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        cliente.setEmail(dto.getEmail());
        return cliente;
    }
}