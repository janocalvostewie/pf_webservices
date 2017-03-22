
package web_articulos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author janillo
 */
@WebService(serviceName = "articulos")
public class articulos {

    private static final String BBDD = "web_pruebas";
    private static final String USU_BBDD = "janillo";
    private static final String PASS_BBDD = "janillo";
    private static Statement st;

    public static void conectarse() throws ClassNotFoundException, SQLException {

        Connection conexion;
        Class.forName("com.mysql.jdbc.Driver");
        conexion = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.84.132:3306/" + BBDD, USU_BBDD, PASS_BBDD);
        st = (Statement) conexion.createStatement();

    }

   
    
    @WebMethod(operationName = "articulosCategorias")
    public String[][] articulosCategorias() {
        ResultSet rs = null;
        // List<Object> listaValores=null;
        String valores[][] = null;
        try {

            conectarse();
            String sql = "SELECT CATEGORIA, DESCRIPCION FROM WS_ARTICULOS_CATEGORIAS";
            rs = st.executeQuery(sql);
           
           valores=Metodos.creaMatriz(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(articulos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valores;
    }
}
