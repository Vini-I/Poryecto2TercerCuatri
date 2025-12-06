/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.daos;

import java.util.List;
import modelo.dtos.UsuarioDTO;

/**
 *
 * @author rodol
 */
public interface IUsuarioDao extends IDao {

    UsuarioDTO buscarPorUsername(String username);

    List<UsuarioDTO> listarPorRol(String rol);

    boolean existeUsername(String username);
}
