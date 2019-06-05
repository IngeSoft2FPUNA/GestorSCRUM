package controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelos.UserStory;
import utiles.Conexion;
import utiles.Utiles;

public class UserstoryControlador {

    public static boolean agregar(UserStory userStory) {

        boolean valor = false;

        if (Conexion.conectar()) {
            String sql = "INSERT INTO userstories (id_proyecto,nombre, duedate,"
                    + " descripcion) VALUES ("
                    + "'" + userStory.getId_proyecto() + "',"
                    + "'" + userStory.getId_us() + "',"
                    + "'" + userStory.getNombre() + "',"
                    + "'" + userStory.getDescripcion() + "',"
                    + "'" + userStory.getTiempo_estimado() + "',"
                    + "'" + userStory.getTiempo_trabajado() + "')";

            System.out.println(sql);

            try {
                Conexion.getSt().executeUpdate(sql);
                valor = true;
            } catch (Exception e) {
                System.err.println("Error :" + e.getMessage());
            }
        }

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

    public static boolean modificar(UserStory userStory, String id_proyecto) {
        boolean valor = false;

        if (Conexion.conectar()) {

            String sql = "UPDATE userstories SET "
                    + "id_proyecto = '" + userStory.getId_proyecto() + "', "
                    + "id_us = '" + userStory.getId_us() + "', "
                    + "nombre = '" + userStory.getNombre() + "', "
                    + "descripcion = '" + userStory.getDescripcion() + "', "
                    + "tiempo_estimado = " + userStory.getTiempo_estimado() + "' "
                    + "WHERE UPPER(id_proyecto) LIKE '%" + id_proyecto + "%' "
                    + "AND UPPER (id_us) LIKE '%" + userStory.getId_us() + "%' ";

            System.out.println(sql);

            try {
                Conexion.getSt().executeUpdate(sql);
                valor = true;
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }

        return valor;
    }

    public static boolean eliminar(UserStory userStory) {
        boolean valor = false;

        if (Conexion.conectar()) {

            String sql = "UPDATE userstories SET estados = "
                    + "(SELECT id_estado FROM estados "
                    + "WHERE UPPER(descripcion) LIKE '%ELIMINADO%') "
                    + "WHERE id_proyecto LIKE '%" + userStory.getId_proyecto() + "% '"
                    + "AND id_us LIKE '%" + userStory.getId_us() + "%'";
            System.out.println(sql);
            try {
                Conexion.getSt().executeUpdate(sql);
                valor = true;
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
        return valor;
    }

    public static String buscarUSBacklog(String id_usuario, String id_proyecto) {
        //int offset = (pagina - 1) * Utiles.REGISTRO_PAGINA;
        String valor = "";

        if (Conexion.conectar()) {

            try {
                String sql = "SELECT US.id_us AS id_us,"
                        + "US.nombre AS nombre, "
                        + "US.descripcion AS descripcion, "
                        + "US.tiempo_estimado AS tiempo_estimado, "
                        + "US.tiempo_trabajado AS tiempo_trabajado, "
                        + "E.descripcion AS estado, "
                        + "P.descripcion AS prioridad "
                        + "FROM userstories US "
                        + "JOIN prioridades P on US.id_prio = P.id_prio "
                        + "JOIN estados E on US.id_estados_backlog = E.id_estados "
                        + "WHERE UPPER(US.id_proyecto) LIKE '%"
                        + id_proyecto
                        + "%'ORDER BY id_us ";

                System.out.println("----->" + sql);
                try (PreparedStatement ps = Conexion.getConn().prepareStatement(sql)) {
                    ResultSet rs = ps.executeQuery();
                    String tabla = "";

                    while (rs.next()) {
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

    public static String buscarUSSprint(String id_usuario, String id_proyecto) {
        //int offset = (pagina - 1) * Utiles.REGISTRO_PAGINA;
        String valor = "";

        if (Conexion.conectar()) {

            try {
                String sql = "SELECT US.id_us AS id_us,"
                        + "US.nombre AS nombre, "
                        + "P.descripcion AS prioridad, "
                        + "US.tiempo_estimado AS tiempo_estimado, "
                        + "US.tiempo_trabajado AS tiempo_trabajado, "
                        + "E.descripcion AS estado, "
                        + "U.nombre_apellido AS responsable "
                        + "FROM userstories US "
                        + "JOIN prioridades P on US.id_prio = P.id_prio "
                        + "JOIN estados E on US.id_estados = E.id_estados "
                        + "JOIN usuarios U on US.id_responsable = U.id_usuario "
                        + "WHERE UPPER(US.id_proyecto) LIKE '%"
                        + id_proyecto + "%' "
                        + "AND US.id_estados_backlog = 4"
                        + " ORDER BY id_us ";

                System.out.println("----->" + sql);
                try (PreparedStatement ps = Conexion.getConn().prepareStatement(sql)) {
                    ResultSet rs = ps.executeQuery();
                    String tabla = "";

                    while (rs.next()) {
                        tabla += "<tr>"
                                + "<td>" + rs.getString("id_us") + "</td>"
                                + "<td>" + rs.getString("nombre") + "</td>"
                                + "<td>" + rs.getString("prioridad") + "</td>"
                                + "<td>" + rs.getInt("tiempo_estimado") + "</td>"
                                + "<td>" + rs.getInt("tiempo_trabajado") + "</td>"
                                + "<td>" + rs.getString("estado") + "</td>"
                                + "<td>"+ rs.getString("responsable") +"</td>"
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

}
