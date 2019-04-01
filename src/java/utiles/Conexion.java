
package utiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Luis Zarza
 */
public class Conexion {

    //atributos
    public static String driver = "org.postgresql.Driver";
    public static String servidor = "localhost";
    public static String puerto = "5432";
    public static String BaseDato = "gestorScrum";
    public static String usuario = "postgres";
    public static String clave = "postgres";
    public static Connection conn;
    public static Statement st;
    
    
    public static boolean conectar(){
        boolean valor = false;
        
        try{
            Class.forName(driver);
            String url = "jdbc:postgresql://"+servidor+":"+puerto+"/"+BaseDato;
            conn = DriverManager.getConnection(url, usuario, clave);
            st = conn.createStatement();
            valor = true;           
        }catch(ClassNotFoundException ex){
            System.err.println("Error: "+ ex);
        }catch(SQLException ex){
            System.err.println("Error: "+ ex);
        }
        
        return valor;
        
    }//fin de el metodo conectar
    
    public static boolean cerrar(){
        boolean valor = false;
        
        try {
            st.close();
            conn.close();
        } catch (SQLException ex) {
            System.err.println("Error: "+ ex);
        }
        
        return valor;
       
    }//fin del metodo cerrar

    public static Connection getConn() {
        return conn;
    }

    public static Statement getSt() {
        return st;
    }

}//fin de la clase
