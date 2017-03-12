/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicio_Usuarios;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author janillo
 */
@WebService(serviceName = "Usuarios")
public class Usuarios {

    
      private static final String BBDD = "web_pruebas";
    private static final String USU_BBDD = "janillo";
    private static final String PASS_BBDD = "janillo";
    private static Statement st;
    
    
    
    public static void conectarse() throws ClassNotFoundException, SQLException {

        Connection conexion;
        Class.forName("com.mysql.jdbc.Driver");
        conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/" + BBDD, USU_BBDD, PASS_BBDD);
        st = (Statement) conexion.createStatement();

    }
    @WebMethod(operationName = "loguearse")
    public String[] loguearse(String nom_usuario, String pass){
    
        ResultSet rs = null;
        String[] valores = new String[5];
        try {

            conectarse();
            String sql = "SELECT u.id_usuario, ud.nombre, ud.apellido1,u.nivel_acceso, una.descripcion FROM ws_usuarios u, ws_usuarios_detalle ud, ws_usuarios_nivel_acceso una where u.id_usuario=ud.id_usuario and u.nivel_acceso=una.id_nivel and u.usuario='" + nom_usuario + "' and u.pass='" + pass + "'";
            rs = st.executeQuery(sql);

            while (rs.next()) {
                valores[0] = String.valueOf(rs.getInt(1));
                valores[1] = rs.getString(2);
                valores[2] = rs.getString(3);
                valores[3] = String.valueOf(rs.getInt(4));
                valores[4] = rs.getString(5);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
              Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
          }
        return valores;
    }
    
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
}
