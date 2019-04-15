
package controladores;

import java.sql.PreparedStatement;
import modelos.Usuario;
import utiles.Conexion;
import utiles.Utiles;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Luis Zarza
 */
public class UsuarioControlador {
    
    //agregar metodo agregar
    public static boolean agregar(Usuario usuario){
        boolean valor = false;
        
        if (Conexion.conectar()) {
            String sql = "INSERT INTO usuarios(nombre_apellido, cedula, correo_electronico, nick_usuario, password)"
                    + "values('"+usuario.getNombre()+"'"
                    +","+usuario.getCedula()
                    +",'"+usuario.getEmail()+"'"
                    +",'"+usuario.getUsuario()+"'"
                    +",'"+Utiles.md5(Utiles.quitarGuiones(usuario.getPassword()))+"'"
                    +")";
            
            System.out.println(sql);
            
            try {
                Conexion.getSt().executeUpdate(sql);
                valor = true;
            } catch (SQLException ex) {
                System.err.println("Error :"+ex.getMessage());
            }
        }
        return valor;
    }//fin del metodo agregar
    
    public static Usuario buscarId(Usuario usuario){
        if (Conexion.conectar()) {
            String sql = "SELECT * FROM usuarios WHERE cedula = '"+usuario.getCedula()+"'";
            try {
                ResultSet rs = Conexion.getSt().executeQuery(sql);
                if (rs.next()) {
                    usuario.setNombre(rs.getString("nombre_apellido"));
                    usuario.setCedula(Integer.parseInt(rs.getString("cedula")));
                    usuario.setEmail(rs.getString("correo_electronico"));
                    usuario.setUsuario(rs.getString("nick_usuario"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setActivo(rs.getBoolean("activo"));
                }else{
                    usuario.setCedula(0);
                    usuario.setNombre("");
                
                }
            } catch (SQLException ex) {
                System.err.println("Error :"+ex);
            }
        }
        return usuario;     
        
    }
    
    public static String buscarNombre(String nombre, int pagina){
        int offset = (pagina-1)*Utiles.REGISTRO_PAGINA;
        String valor = "";
        
        if (Conexion.conectar()) {
            
            try {
                String sql = "SELECT * FROM usuarios WHERE activo = 'TRUE' AND UPPER(nombre_apellido) LIKE '%"
                        +nombre.toUpperCase()+"%'" + "ORDER BY id_usuario offset " + offset + "LIMIT "
                        + Utiles.REGISTRO_PAGINA;
                System.out.println("----->" + sql);
                try(PreparedStatement ps = Conexion.getConn().prepareStatement(sql)) {
                    ResultSet rs = ps.executeQuery();
                    String tabla = "";
                    
                    while(rs.next()){
                        tabla+= "<tr>"
                                + "<td>"+rs.getInt("cedula")+"</td>"
                                + "<td>"+rs.getString("nombre_apellido")+"</td>"
                                + "<td>"+rs.getString("nick_usuario")+"</td>"
                                + "<td>"+rs.getString("correo_electronico")+"</td>"
                                + "<td>"+rs.getString("password")+"</td>"
                                + "</tr>";                        
                    }
                    
                    if (tabla.equals("")) {
                        tabla = "<tr><td colspan = 2>No existen Registros...</td></tr>";
                    }
                    ps.close();
                    valor = tabla;                    
                } catch (SQLException e) {
                    System.out.println("Error: " + e);
                }
                Conexion.cerrar();
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
        Conexion.cerrar();
        
        System.out.println(valor);
        System.out.println("estoy aca 2");
        return valor;
    }
    
    public static boolean modificar(Usuario usuario, int id_usuario){
        boolean valor = false;
        
        if (Conexion.conectar()) {
            
            String sql = "UPDATE usuarios SET "
                    + "nombre_apellido = '"+usuario.getNombre()+"',"
                    + "cedula = "+usuario.getCedula()+","
                    + "correo_electronico = '"+usuario.getEmail()+"',"
                    + "nick_usuario = '"+usuario.getUsuario()+"',"
                    + "password = '"+ Utiles.md5(Utiles.quitarGuiones(usuario.getPassword()))+"'"
                    +"WHERE cedula= '"+id_usuario+"'";
            
            System.out.println(sql);
            
            try {
                Conexion.getSt().executeUpdate(sql);
                valor = true;
            } catch (SQLException e) {
                System.out.println("Error: "+ e);
            }            
        }
        
        return valor;
    }

    public static boolean eliminar(Usuario usuario){
        boolean valor = false;
        
        if (Conexion.conectar()) {
            
            String sql = "UPDATE usuarios SET activo = 'false' WHERE cedula = '"+usuario.getCedula()+"'";
            System.out.println(sql);
            try {
                Conexion.getSt().executeUpdate(sql);
                valor = true;
            } catch (SQLException e) {
                System.out.println("Error: "+ e);
            }            
        }
        return valor;
    }

    public static Usuario validarAcceso(Usuario usuario, HttpServletRequest request){
     System.out.println("login"+usuario.getUsuario());
      System.out.println("password"+usuario.getPassword());
        if (Conexion.conectar()) {
            try {
                String sql = "SELECT * FROM usuarios WHERE nick_usuario = ? AND password = ? AND activo = 'TRUE'";
                
                try(PreparedStatement ps = Conexion.getConn().prepareStatement(sql)) {
                    ps.setString(1, Utiles.quitarGuiones(usuario.getUsuario()));
                    ps.setString(2, Utiles.md5(Utiles.quitarGuiones(usuario.getPassword())));
                    
                    ResultSet rs = ps.executeQuery();
                    System.out.println("---------->"+sql);

                    if (rs.next()) {
                        HttpSession sesion = request.getSession(true);
                        usuario = new Usuario();
                        //usuario.setId_usuario(rs.getInt("id_usuario"));
                        usuario.setNombre(rs.getString("nombre_apellido"));
                        usuario.setUsuario(rs.getString("nick_usuario"));
                        usuario.setPassword(rs.getString("password"));

                        sesion.setAttribute("usuarioLogueado", usuario);
                        
                    }else{
                        usuario = null;
                    }
                    ps.close();
                } catch (SQLException e) {
                    System.out.println("===>"+e.getLocalizedMessage());
                }                
            } catch (Exception e) {
            }
        }
        Conexion.cerrar();
        return usuario;
    }
    
    /*FALTAN 5 METODOS MAS*/    
}//fin de la clase
