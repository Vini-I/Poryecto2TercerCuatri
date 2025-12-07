/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.mappers;

import modelo.RolEnum;
import modelo.Usuario;
import modelo.dtos.UsuarioDTO;

/**
 *
 * @author rodol
 */
public class UsuarioMapper {

    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El dto no puede estar vacio");
        }

        return new Usuario(
                dto.getId(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getNombre(),
                dto.getRol() != null ? RolEnum.fromString(dto.getRol()) : null,
                dto.getFechaCreacion(),
                dto.getUltimoAcceso()
        );
    }

    public static UsuarioDTO toDTO(Usuario entity) {
        if (entity == null) {
            throw new IllegalArgumentException("El usuario no puede estar vacio");
        }

        return new UsuarioDTO(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getNombre(),
                entity.getRol() != null ? entity.getRol().name() : null,
                entity.getFechaCreacion(),
                entity.getUltimoAcceso()
        );
    }
}
