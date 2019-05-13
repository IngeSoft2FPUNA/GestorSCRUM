package controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelos.UserStory;
import utiles.Conexion;
import utiles.Utiles;

public class UserstoryControlador {
    
   public static boolean agregar(UserStory userStory){
    
        boolean valor = false;
        
        if(Conexion.conectar()){
            String sql = "INSERT INTO userstories (id_proyecto,nombre, duedate,"
                    + " descripcion) VALUES ("
                    + "'"+userStory.getId_proyecto() +"',"
                    + "'"+userStory.getId_us()+"',"
                    + "'"+userStory.getNombre()+"',"
                    + "'"+userStory.getDescripcion()+"',"
                    + "'"+userStory.getTiempo_estimado()+"',"
                    + "'"+userStory.getTiempo_trabajado()+"')";
            
             System.out.println(sql);
            
            try {
                Conexion.getSt().executeUpdate(sql);
                valor = true;                
            } catch (Exception e) {
                System.err.println("Error :"+e.getMessage());                
            }
        }
        
        return valor;
        
    }

   public static UserStory buscarId(UserStory userStory){
        if (Conexion.conectar()) {
            String sql = "SELECT * FROM userstories WHERE UPPER(id_proyecto)"
                    + " LIKE '%"+userStory.getId_proyecto()+"%'";
            
            System.out.println("----->" + sql);
            
            try {
                ResultSet rs = Conexion.getSt().executeQuery(sql);
                if (rs.next()) {
                    userStory.setNombre(rs.getString("nombre"));
                    userStory.setDescripcion(rs.getString("descripcion"));
                    userStory.setId_us(rs.getString("id_us"));
                }else{
                    userStory.setId_proyecto("");
                    userStory.setNombre("");
                    userStory.setId_us("");
                
                }
            } catch (SQLException ex) {
                System.err.println("Error :"+ex);
            }
        }
        return userStory;     
        
    }   
    
   public static String buscarNombre(String nombre, int pagina){
        int offset = (pagina-1)*Utiles.REGISTRO_PAGINA;
        String valor = "";
        
        if (Conexion.conectar()) {
            
            try {
                String sql = "SELECT * FROM userstories WHERE id_estados != "
                        +           "(SELECT id_estados FROM estados "
                        + "             WHERE descripcion LIKE '%ELIMINADO%')"
                        + " AND UPPER(id_proyecto) LIKE '%"
                        //+id del proyecto
                        + "AND UPPER (id_us) LIKE '%"
                        +nombre.toUpperCase()+"%'" + "ORDER BY id_us offset " 
                        + offset + "LIMIT "
                        + Utiles.REGISTRO_PAGINA;
                
                System.out.println("----->" + sql);
                try(PreparedStatement ps = Conexion.getConn().prepareStatement(sql)) {
                    ResultSet rs = ps.executeQuery();
                    String tabla = "";
                    
                    while(rs.next()){
                        tabla+= "<tr>"
                                + "<td>"+rs.getString("id_proyecto")+"</td>"
                                + "<td>"+rs.getString("nombre")+"</td>"
                                + "<td>"+rs.getString("descripcion")+"</td>"
                                //+ "<td>"+rs.getString("duedate")+"</td>"
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

    public static boolean modificar(UserStory userStory, String id_proyecto){
        boolean valor = false;
        
        if (Conexion.conectar()) {
            
            String sql = "UPDATE userstories SET "
                    + "id_proyecto = '"+userStory.getId_proyecto()+"', "
                    + "id_us = '"+userStory.getId_us()+"', "
                    + "nombre = '"+userStory.getNombre()+"', "
                    + "descripcion = '"+userStory.getDescripcion()+"', "                   
                    + "tiempo_estimado = " +userStory.getTiempo_estimado()+"' "
                    + "WHERE UPPER(id_proyecto) LIKE '%"+id_proyecto+"%' "
                    + "AND UPPER (id_us) LIKE '%"+ userStory.getId_us()+"%' ";
            
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
        
   public static boolean eliminar(UserStory userStory){
        boolean valor = false;
        
        if (Conexion.conectar()) {
            
            String sql = "UPDATE userstories SET estados = "
                    + "(SELECT id_estado FROM estados "
                    + "WHERE UPPER(descripcion) LIKE '%ELIMINADO%') "
                    + "WHERE id_proyecto LIKE '%"+userStory.getId_proyecto()+"% '"
                    + "AND id_us LIKE '%"+userStory.getId_us()+"%'";
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

}
