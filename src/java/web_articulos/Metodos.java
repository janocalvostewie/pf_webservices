/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web_articulos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author janillo
 */
public class Metodos {
    
    
     public static String[][] creaMatriz(ResultSet rs){
         String valores[][] = null;
        try {
           
            
            int colCount = rs.getMetaData().getColumnCount();
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();
            valores = new String[rowCount][colCount];

            int i = 0;
            while (rs.next()) {
                for (int j = 0; j < colCount; j++) {
                    valores[i][j] = rs.getString(j + 1);
                }
                i++;

            }
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(articulos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valores; 
    }
    
    
    
}
