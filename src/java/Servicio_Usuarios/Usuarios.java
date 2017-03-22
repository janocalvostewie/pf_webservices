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
import java.util.ArrayList;
import java.util.List;
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

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valores;
    }
    
    @WebMethod(operationName = "InsertarUsuario")
    public void inserta_usuario(String nombre,String ape1,String ape2,String dni,String direccion,String fecha,String telefono, String sexo) {
    
        ResultSet rs = null;
    
        
        try {

            conectarse();
            
            String insertar="call inserta_usuario( '"+nombre+"', '"+ape1+"', '"+ape2+"', '"+dni+"', '"+direccion+"', '"+fecha+"', '"+telefono+"','"+sexo+"')";
            
            st.executeUpdate(insertar);
            
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
   @WebMethod(operationName = "ConsultarTrabajadores")
    public String[][] consultarTrabajadores(){

        ResultSet rs = null;
       // List<Object> listaValores=null;
        String valores [][] =null;
        try {

            conectarse();
            String sql = "SELECT ID_USUARIO, NOMBRE, APELLIDO1, APELLIDO2, DNI, DIRECCION, FECHA_NACIMIENTO, TELEFONO, SEXO FROM ws_usuarios_detalle";
            rs = st.executeQuery(sql);
 
            int colCount = rs.getMetaData().getColumnCount();
           rs.last();
           int rowCount = rs.getRow();
           rs.beforeFirst();
           valores= new String[rowCount][colCount];
 
           int i =0;
           while(rs.next()){
               for(int j=0;j<colCount;j++){
                  valores[i][j]= rs.getString(j+1);
               }
                i++;
 
           }
            
    
            
     
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valores;
    }
    
      @WebMethod(operationName = "devuelveUsuarioLogueo")
    public String[] devuelveUsuarioLogueo(int id_usuario){

        ResultSet rs = null;
        String[] valores = new String[2];
        try {

            conectarse();
            String sql = "SELECT u.usuario, una.descripcion FROM ws_usuarios u,  ws_usuarios_nivel_acceso una where  u.nivel_acceso=una.id_nivel and u.id_usuario=" + id_usuario ;
            rs = st.executeQuery(sql);

            while (rs.next()) {
                valores[0] = rs.getString(1);
                valores[1] = rs.getString(2);
               

            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valores;
    }

    
    @WebMethod(operationName = "consultarUsuariosTiendas")
    public String[][] consultarUsuariosTiendas(int id_usuario){

        ResultSet rs = null;
      
        String valores [][] =null;
        try {

            conectarse();
            String sql = "SELECT t.codigo_tienda,t.tienda, t.ciudad FROM   WS_USUARIOS_TIENDAS ut, WS_TIENDAS t where   ut.CODIGO_TIENDA=t.CODIGO_TIENDA and ut.id_usuario=" + id_usuario ;
            rs = st.executeQuery(sql);
 
            int colCount = rs.getMetaData().getColumnCount();
           rs.last();
           int rowCount = rs.getRow();
           rs.beforeFirst();
           valores= new String[rowCount][colCount];
 
           int i =0;
           while(rs.next()){
               for(int j=0;j<colCount;j++){
                  valores[i][j]= rs.getString(j+1);
               }
                i++;
 
           }
            
    
            
     
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valores;
    }
    
      @WebMethod(operationName = "ConsultarTrabajadoresObjeto")
    public List consultarTrabajadoresObjeto(){
List lista=null;
        
        try {
            
            ResultSet rs = null;
            // List<Object> listaValores=null;
            String valores [][] =null;
            
            conectarse();
            String sql = "SELECT ID_USUARIO, NOMBRE, APELLIDO1, APELLIDO2, DNI, DIRECCION, FECHA_NACIMIENTO, TELEFONO, SEXO FROM ws_usuarios_detalle";
            rs = st.executeQuery(sql);
            
            
            while(rs.next()){
                Usuario u=new Usuario(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9));
                lista.add(u);
            }
           
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
         return lista;
        }
    
}
