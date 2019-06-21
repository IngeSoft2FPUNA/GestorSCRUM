package controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelos.UserStory;
import utiles.Conexion;
import utiles.Utiles;

public class UserstoryControlador {

    public static boolean agregar(UserStory userStory) {

        boolean valor = false;

        if (Conexion.conectar()) {
            String sql = "INSERT INTO userstories (id_proyecto,"
                    + "id_us,"
                    + "nombre,"
                    + "descripcion,"
                    + "tiempo_estimado,"
                    + "tiempo_trabajado,"
                    + "id_estados,"
                    + "id_estados_backlog,"
                    + "id_prio,"
                    + "id_responsable) VALUES ("
                    + "'" + userStory.getId_proyecto() + "',"
                    + "'" + userStory.getId_us() + "',"
                    + "'" + userStory.getNombre() + "',"
                    + "'" + userStory.getDescripcion() + "',"
                    + "'" + userStory.getTiempo_estimado() + "',"
                    + "'" + userStory.getTiempo_trabajado()+ "',"
                    + "'" + userStory.getId_estados()+ "',"
                    + "'" + userStory.getId_estados_backlog()+ "',"
                    + "'" + userStory.getId_prio()+ "',"
                    + "'" + userStory.getId_responsable()+ "')";

            System.out.println(sql);

            try {
                Conexion.getSt().executeUpdate(sql);
                valor = true;
            } catch (Exception e) {
                System.err.println("Error :" + e.getMessage());
            }
        }
        Conexion.cerrar();

        return valor;

    }

    public static UserStory buscarId(UserStory userStory) {
        if (Conexion.conectar()) {
            String sql = "SELECT * FROM userstories WHERE UPPER(id_proyecto)"
                    + " LIKE '%" + userStory.getId_proyecto() + "%'";

            System.out.println("----->" + sql);

            try {
                ResultSet rs = Conexion.getSt().executeQuery(sql);
                if (rs.next()) {
                    userStory.setNombre(rs.getString("nombre"));
                    userStory.setDescripcion(rs.getString("descripcion"));
                    userStory.setId_us(rs.getString("id_us"));
                } else {
                    userStory.setId_proyecto("");
                    userStory.setNombre("");
                    userStory.setId_us("");

                }
            } catch (SQLException ex) {
                System.err.println("Error :" + ex);
            }
        }
        Conexion.cerrar();
        return userStory;

    }
   
    public static UserStory getUS(String id_us, String id_proyecto) {
        
        UserStory userStory = new UserStory();
        
        if (Conexion.conectar()) {
            String sql = "SELECT * FROM userstories "
                    + "WHERE UPPER(id_proyecto) LIKE '%" + id_proyecto + "%'"
                    + "AND UPPER(id_us) LIKE '%" + id_us + "%'";

            System.out.println("----->" + sql);

            try {
                ResultSet rs = Conexion.getSt().executeQuery(sql);
                if (rs.next()) {
                        userStory = new UserStory(rs.getString("id_proyecto"),
                                rs.getString("id_us") , 
                                rs.getString("nombre"), 
                                rs.getString("descripcion"), 
                                rs.getInt("tiempo_estimado"), 
                                rs.getInt("tiempo_trabajado"), 
                                rs.getInt("id_estados"),
                                rs.getInt("id_estados_backlog"),
                                rs.getInt("id_prio"),                        
                                rs.getInt("id_responsable")
                        
                        );
                } else {
                    userStory.setId_proyecto("");
                    userStory.setNombre("");
                    userStory.setId_us("");

                }
            } catch (SQLException ex) {
                System.err.println("Error :" + ex);
            }
        }
        Conexion.cerrar();
        System.out.println("US: "+ userStory);
        return userStory;

    }

    public static String buscarNombre(String nombre, int pagina) {
        int offset = (pagina - 1) * Utiles.REGISTRO_PAGINA;
        String valor = "";

        if (Conexion.conectar()) {

            try {
                String sql = "SELECT * FROM userstories WHERE id_estados != "
                        + "(SELECT id_estados FROM estados "
                        + "             WHERE descripcion LIKE '%ELIMINADO%')"
                        + " AND UPPER(id_proyecto) LIKE '%"
                        //+id del proyecto
                        + "AND UPPER (id_us) LIKE '%"
                        + nombre.toUpperCase() + "%'" + "ORDER BY id_us offset "
                        + offset + "LIMIT "
                        + Utiles.REGISTRO_PAGINA;

                System.out.println("----->" + sql);
                try (PreparedStatement ps = Conexion.getConn().prepareStatement(sql)) {
                    ResultSet rs = ps.executeQuery();
                    String tabla = "";

                    while (rs.next()) {
                        tabla += "<tr>"
                                + "<td>" + rs.getString("id_proyecto") + "</td>"
                                + "<td>" + rs.getString("nombre") + "</td>"
                                + "<td>" + rs.getString("descripcion") + "</td>"
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
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
        Conexion.cerrar();

        System.out.println(valor);
        System.out.println("estoy aca 2");
        return valor;
    }

    public static boolean modificar(UserStory userStory) {
        boolean valor = false;

        if (Conexion.conectar()) {

            String sql = "UPDATE userstories SET "                  
                    + "nombre = '" + userStory.getNombre() + "', "
                    + "descripcion = '" + userStory.getDescripcion() + "', "
                    + "tiempo_estimado = " + userStory.getTiempo_estimado() + ","
                    + "tiempo_trabajado = " + userStory.getTiempo_trabajado()+ ","
                    + "id_prio = " + userStory.getId_prio()+ ","
                    + "id_estados_backlog = " + userStory.getId_estados_backlog()+ ","
                    + "id_estados = " + userStory.getId_estados() + ","
                    + "id_responsable = " + userStory.getId_responsable()
                    + "WHERE UPPER(id_proyecto) LIKE '%" + userStory.getId_proyecto() + "%' "
                    + "AND UPPER (id_us) LIKE '%" + userStory.getId_us() + "%' ";

            System.out.println(sql);

            try {
                Conexion.getSt().executeUpdate(sql);
                valor = true;
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
        Conexion.cerrar();
        return valor;
    }

    public static boolean eliminar(UserStory userStory) {
        boolean valor = false;

        if (Conexion.conectar()) {

            String sql = "UPDATE userstories "
                    + "SET id_estados = 6 "
                    + "WHERE UPPER(id_proyecto) LIKE '%" + userStory.getId_proyecto() + "%' "
                    + "AND UPPER(id_us) LIKE '%" + userStory.getId_us() + "%'";
            System.out.println(sql);
            try {
                Conexion.getSt().executeUpdate(sql);
                valor = true;
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
        Conexion.cerrar();
        return valor;
    }

    public static ArrayList<UserStory> buscarUSBacklog(String id_proyecto) {
        //int offset = (pagina - 1) * Utiles.REGISTRO_PAGINA;
        String valor = "";
        ArrayList<UserStory> listaUS = new ArrayList<UserStory>();
        UserStory userStoryTemp = new UserStory();
        if (Conexion.conectar()) {

            try {
                String sql = "SELECT US.id_us AS id_us,"
                        + "US.nombre AS nombre, "
                        + "US.descripcion AS descripcion, "
                        + "US.tiempo_estimado AS tiempo_estimado, "
                        + "US.tiempo_trabajado AS tiempo_trabajado, "
                        + "US.id_estados AS id_estados, "
                        + "US.id_estados_backlog AS id_estados_backlog, "
                        + "US.id_responsable AS id_responsable, "                        
                        + "US.id_prio AS id_prio, "
                        + "E.descripcion AS estado, "
                        + "P.descripcion AS prioridad "
                        + "FROM userstories US "
                        + "JOIN prioridades P on US.id_prio = P.id_prio "
                        + "JOIN estados E on US.id_estados_backlog = E.id_estados "
                        + "WHERE UPPER(US.id_proyecto) LIKE '%" + id_proyecto + "%'"
                        + "AND US.id_estados != 6 "
                        + "ORDER BY US.id_prio ";

                System.out.println("----->" + sql);
                try (PreparedStatement ps = Conexion.getConn().prepareStatement(sql)) {
                    ResultSet rs = ps.executeQuery();
                    String tabla = "";

                    while (rs.next()) {
                        userStoryTemp = new UserStory("",
                                rs.getString("id_us") , 
                                rs.getString("nombre"), 
                                rs.getString("descripcion"), 
                                rs.getInt("tiempo_estimado"), 
                                rs.getInt("tiempo_trabajado"), 
                                rs.getInt("id_estados"),
                                rs.getInt("id_estados_backlog"),
                                rs.getInt("id_prio"),                        
                                rs.getInt("id_responsable")
                        
                        );

                        listaUS.add(userStoryTemp);
                        
                        tabla += "<tr>"
                                + "<td>" + rs.getString("id_us") + "</td>"
                                + "<td>" + rs.getString("nombre") + "</td>"
                                + "<td>" + rs.getString("descripcion") + "</td>"
                                + "<td>" + rs.getInt("tiempo_estimado") + "</td>"
                                + "<td>" + rs.getInt("tiempo_trabajado") + "</td>"
                                + "<td>" + rs.getString("estado") + "</td>"
                                + "<td>" + rs.getString("prioridad") + "</td>"
                                + "<td>"
                                + "<button onclick='editarLinea(\"" + rs.getString("id_us") + "\")' class='btn btn-warning btn-sm'>"
                                + "<span class='glyphicon glyphicon-pencil'></span>"
                                + "</button>"
                                + "</td>"
                                + "<td>"
                                + "<button onclick='eliminarLinea(\"" + rs.getString("id_us") + "\")' class='btn btn-danger btn-sm'>"
                                + "<span class='glyphicon glyphicon-trash'></span>"
                                + "</button>"
                                + "</td>"
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
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
        Conexion.cerrar();

        System.out.println(valor);
        return listaUS;
    }

    public static ArrayList<UserStory> buscarUSSprint(String id_proyecto) {
        //int offset = (pagina - 1) * Utiles.REGISTRO_PAGINA;
        String valor = "";
        ArrayList<UserStory> listaUS = new ArrayList<UserStory>();
        UserStory userStoryTemp = new UserStory();
        
        if (Conexion.conectar()) {

            try {
                String sql = "SELECT US.id_us AS id_us,"
                        + "US.nombre AS nombre, "
                        + "US.descripcion AS descripcion, "
                        + "P.descripcion AS prioridad, "
                        + "US.tiempo_estimado AS tiempo_estimado, "
                        + "US.tiempo_trabajado AS tiempo_trabajado, "
                        + "US.id_estados AS id_estados, "
                        + "US.id_estados_backlog AS id_estados_backlog, "
                        + "US.id_responsable AS id_responsable, "
                        + "US.id_prio AS id_prio, "
                        + "E.descripcion AS estado "
                        + "FROM userstories US "
                        + "JOIN prioridades P on US.id_prio = P.id_prio "
                        + "JOIN estados E on US.id_estados = E.id_estados "
                        + "WHERE UPPER(US.id_proyecto) LIKE '%"
                        + id_proyecto + "%' "
                        + "AND US.id_estados != 6 "
                        + "AND US.id_estados_backlog = 4"
                        + " ORDER BY id_us ";

                System.out.println("----->" + sql);
                try (PreparedStatement ps = Conexion.getConn().prepareStatement(sql)) {
                    ResultSet rs = ps.executeQuery();
                    String tabla = "";
                    
                    while (rs.next()) {
                        userStoryTemp = new UserStory("",
                                rs.getString("id_us") , 
                                rs.getString("nombre"), 
                                rs.getString("descripcion"), 
                                rs.getInt("tiempo_estimado"), 
                                rs.getInt("tiempo_trabajado"), 
                                rs.getInt("id_estados"),
                                rs.getInt("id_estados_backlog"),
                                rs.getInt("id_prio"),                        
                                rs.getInt("id_responsable"));     
                                                
                        listaUS.add(userStoryTemp);
                        
                        tabla += "<tr>"
                                + "<td>" + rs.getString("id_us") + "</td>"
                                + "<td>" + rs.getString("nombre") + "</td>"
                                + "<td>" + rs.getString("prioridad") + "</td>"
                                + "<td>" + rs.getInt("tiempo_estimado") + "</td>"
                                + "<td>" + rs.getInt("tiempo_trabajado") + "</td>"
                                + "<td>" + rs.getString("estado") + "</td>"
                                + "<td>"
                                + "<button onclick='editarLinea(\"" + rs.getString("id_us") + "\")' class='btn btn-warning btn-sm'>"
                                + "<span class='glyphicon glyphicon-pencil'></span>"
                                + "</button>"
                                + "</td>"
                                + "<td>"
                                + "<button onclick='eliminarLinea(\"" + rs.getString("id_us") + "\")' class='btn btn-danger btn-sm'>"
                                + "<span class='glyphicon glyphicon-trash'></span>"
                                + "</button>"
                                + "</td>"
                                + "</tr>";
                    }

                    if (tabla.equals("")) {
                        tabla = "<tr><td colspan = 8>No existen Registros...</td></tr>";
                    }
                    ps.close();
                    valor = tabla;
                } catch (SQLException e) {
                    System.out.println("Error: " + e);
                }               
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
        Conexion.cerrar();

        System.out.println(listaUS);
        return listaUS;
    }

}
