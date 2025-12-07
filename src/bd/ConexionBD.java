/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author rodol
 */
public class ConexionBD {
    private static ConexionBD instance;
    private Connection cn;
    private final String url;
    private final String user;
    private final String password;

    private ConexionBD() {
        this.url = "jdbc:mysql://localhost:3307/tienda_electronica?useSSL=false&serverTimezone=UTC";
        this.user = "root";
        this.password = "root123";
        
        try {
            this.cn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida exitosamente");
        } catch (SQLException ex) {
            System.err. println("Error " + ex);
            this.cn = null;
        }
    }
    
    public static ConexionBD getInstance(){
        if (instance == null){
            instance = new ConexionBD();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try{
            cn = DriverManager.getConnection(url, user, password);
        }catch (SQLException ex){
            System.out.println("Error " + ex);
        }
        return cn;
    }

   
    
}
