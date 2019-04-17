/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelos.Proyecto;
import utiles.Conexion;
import utiles.Utiles;

/**
 *
 * @author user
 */
public class ProyectoControlador {
    
    public static boolean agregar(Proyecto proyecto){
    
        boolean valor = false;
        
        if(Conexion.conectar()){
            String sql = "INSERT INTO proyectos (id_proyecto,nombre, duedate, descripcion) VALUES ("
                    + "'"+proyecto.getId_proyecto()+"',"
                    + "'"+proyecto.getNombre_proyecto()+"',"
                    + "'"+proyecto.getDuedate()+"',"
                    + "'"+proyecto.getDescripcion_proyecto()+"')";
            
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

   public static Proyecto buscarId(Proyecto proyecto){
        if (Conexion.conectar()) {
            String sql = "SELECT * FROM proyectos WHERE UPPER(id_proyecto) LIKE '%"+proyecto.getId_proyecto()+"%'";
            
            System.out.println("----->" + sql);
            
            try {
                ResultSet rs = Conexion.getSt().executeQuery(sql);
                if (rs.next()) {
                    proyecto.setNombre_proyecto(rs.getString("nombre"));
                    proyecto.setDescripcion_proyecto(rs.getString("descripcion"));
                    proyecto.setDuedate(rs.getString("duedate"));            
                }else{
                    proyecto.setId_proyecto("");
                    proyecto.setNombre_proyecto("");
                
                }
            } catch (SQLException ex) {
                System.err.println("Error :"+ex);
            }
        }
        return proyecto;     
        
    }   
    
    public static String buscarNombre(String nombre, int pagina){
        int offset = (pagina-1)*Utiles.REGISTRO_PAGINA;
        String valor = "";
        
        if (Conexion.conectar()) {
            
            try {
                String sql = "SELECT * FROM proyectos WHERE activo = 'TRUE' AND UPPER(nombre) LIKE '%"
                        +nombre.toUpperCase()+"%'" + "ORDER BY id_proyecto offset " + offset + "LIMIT "
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
                                + "<td>"+rs.getString("duedate")+"</td>"
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

    public static boolean modificar(Proyecto proyecto, String id_proyecto){
        boolean valor = false;
        
        if (Conexion.conectar()) {
            
            String sql = "UPDATE proyectos SET "
                    + "id_proyecto = '"+proyecto.getId_proyecto()+"', "
                    + "nombre = '"+proyecto.getNombre_proyecto()+"', "
                    + "descripcion = '"+proyecto.getDescripcion_proyecto()+"', "
                    + "duedate = '"+proyecto.getDuedate()+"' "
                    +"WHERE UPPER(id_proyecto) LIKE '%"+id_proyecto+"%'";
            
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

        
    public static boolean eliminar(Proyecto proyecto){
        boolean valor = false;
        
        if (Conexion.conectar()) {
            
            String sql = "UPDATE proyectos SET activo = 'false' WHERE id_proyecto LIKE '%"+proyecto.getId_proyecto()+"%'";
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
