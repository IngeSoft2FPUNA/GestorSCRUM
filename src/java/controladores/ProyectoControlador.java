package controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelos.Miembro;
import modelos.Proyecto;
import utiles.Conexion;
import utiles.Utiles;

public class ProyectoControlador {

    public static boolean agregar(Proyecto proyecto) {

        boolean valor = false;

        if (Conexion.conectar()) {
            String sql = "INSERT INTO proyectos (id_proyecto,nombre, duedate,"
                    + " descripcion) VALUES ("
                    + "'" + proyecto.getId_proyecto() + "',"
                    + "'" + proyecto.getNombre_proyecto() + "',"
                    + "'" + proyecto.getDuedate() + "',"
                    + "'" + proyecto.getDescripcion_proyecto() + "')";

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

    public static Proyecto buscarId(Proyecto proyecto) {
        if (Conexion.conectar()) {
            String sql = "SELECT * FROM proyectos WHERE UPPER(id_proyecto)"
                    + " LIKE '%" + proyecto.getId_proyecto() + "%'";

            System.out.println("----->" + sql);

            try {
                ResultSet rs = Conexion.getSt().executeQuery(sql);
                if (rs.next()) {
                    proyecto.setNombre_proyecto(rs.getString("nombre"));
                    proyecto.setDescripcion_proyecto(rs.getString("descripcion"));
                    proyecto.setDuedate(rs.getString("duedate"));
                } else {
                    proyecto.setId_proyecto("");
                    proyecto.setNombre_proyecto("");

                }
            } catch (SQLException ex) {
                System.err.println("Error :" + ex);
            }
        }
        return proyecto;

    }

    public static String buscarNombre(String nombre, int pagina) {
        int offset = (pagina - 1) * Utiles.REGISTRO_PAGINA;
        String valor = "";

        if (Conexion.conectar()) {

            try {
                String sql = "SELECT * FROM proyectos WHERE activo = 'TRUE'"
                        + " AND UPPER(nombre) LIKE '%"
                        + nombre.toUpperCase() + "%'" + "ORDER BY id_proyecto offset "
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
                                + "<td>" + rs.getString("duedate") + "</td>"
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

    public static String buscarMisProyectos(int id_usuario) {
        // int offset = (pagina-1)*Utiles.REGISTRO_PAGINA;
        String valor = "";

        if (Conexion.conectar()) {

            try {
                String sql = "SELECT "
                        + "RU.id_proyecto id_proyecto, "
                        + "RU.id_rol id_rol, "
                        + "P.NOMBRE nombre_proyecto, "
                        + "P.DESCRIPCION desc_proyecto, "
                        + "P.duedate fecha_entrega, "
                        + "R.nombre rol "
                        + "FROM roles_usuarios RU "
                        + "JOIN proyectos P ON RU.id_proyecto = P.id_proyecto\n"
                        + "JOIN roles R ON RU.id_rol = R.id_rol\n"
                        + "WHERE RU.id_usuario = " + id_usuario + ";";

                System.out.println("----->" + sql);
                try (PreparedStatement ps = Conexion.getConn().prepareStatement(sql)) {
                    ResultSet rs = ps.executeQuery();
                    String tabla = "";

                    while (rs.next()) {
                        tabla += "<tr>"
                                + "<td>" + rs.getString("id_proyecto") + "</td>"
                                + "<td>" + rs.getString("nombre_proyecto") + "</td>"
                                + "<td>" + rs.getString("desc_proyecto") + "</td>"
                                + "<td>" + rs.getString("fecha_entrega") + "</td>"
                                + "<td>" + rs.getString("rol") + "</td>"
                                + "<td> "
                                + "<form action=\"MainServlet\" method=\"GET\">"
                                + "<input type=\"hidden\" name=\"id_proyecto\" value=\"" + rs.getString("id_proyecto") + "\"/>"
                                + "<input type=\"hidden\" name=\"id_rol\" value=\"" + rs.getString("id_rol") + "\"/>"
                                + "<input type=\"hidden\" name=\"accion\" value=\"ver_product_backlog\"/>"
                                + "<button type=\"submit\" class=\"btn btn-primary btn-sm\" formtarget=\"_blank\">"
                                + "Backlog"
                                + "</button>"
                                + "</form>"
                                + "</td>"
                                + "<td> "
                                + "<form action=\"MainServlet\" method=\"GET\">"
                                + "<input type=\"hidden\" name=\"id_proyecto\" value=\"" + rs.getString("id_proyecto") + "\"/>"
                                + "<input type=\"hidden\" name=\"id_rol\" value=\"" + rs.getString("id_rol") + "\"/>"
                                + "<input type=\"hidden\" name=\"accion\" value=\"ver_sprint_backlog\"/>"
                                + "<button type=\"submit\" class=\"btn btn-primary btn-sm\" formtarget=\"_blank\">"
                                + "Kanban"
                                + "</button>"
                                + "</form>"
                                + "</td>"
                                + "<td> "
                                + "<form action=\"MainServlet\" method=\"GET\">"
                                + "<input type=\"hidden\" name=\"id_proyecto\" value=\"" + rs.getString("id_proyecto") + "\"/>"
                                + "<input type=\"hidden\" name=\"id_rol\" value=\"" + rs.getString("id_rol") + "\"/>"
                                + "<input type=\"hidden\" name=\"accion\" value=\"ver_miembros\"/>"
                                + "<button type=\"submit\" class=\"btn btn-primary btn-sm\" formtarget=\"_blank\">"
                                + "Miembros"
                                + "</button>"
                                + "</form>"
                                + "</td>"
                                + "<td> "
                                + "<form action=\"MainServlet\" method=\"GET\">"
                                + "<input type=\"hidden\" name=\"id_proyecto\" value=\"" + rs.getString("id_proyecto") + "\"/>"
                                + "<input type=\"hidden\" name=\"id_rol\" value=\"" + rs.getString("id_rol") + "\"/>"
                                + "<input type=\"hidden\" name=\"accion\" value=\"ver_estadisticas\"/>"
                                + "<button type=\"submit\" class=\"btn btn-primary btn-sm\" formtarget=\"_blank\">"
                                + "Estadisticas"
                                + "</button>"
                                + "</form>"
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

    public static boolean modificar(Proyecto proyecto, String id_proyecto) {
        boolean valor = false;

        if (Conexion.conectar()) {

            String sql = "UPDATE proyectos SET "
                    + "id_proyecto = '" + proyecto.getId_proyecto() + "', "
                    + "nombre = '" + proyecto.getNombre_proyecto() + "', "
                    + "descripcion = '" + proyecto.getDescripcion_proyecto() + "', "
                    + "duedate = '" + proyecto.getDuedate() + "' "
                    + "WHERE UPPER(id_proyecto) LIKE '%" + id_proyecto + "%'";

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

    public static boolean eliminar(Proyecto proyecto) {
        boolean valor = false;

        if (Conexion.conectar()) {

            String sql = "UPDATE proyectos SET activo = 'false' "
                    + "WHERE id_proyecto LIKE '%" + proyecto.getId_proyecto() + "%'";
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

    public static ArrayList<Miembro> listarMiembros(String id_proyecto) {
        ArrayList<Miembro> listaMiembros = new ArrayList<Miembro>();
        Miembro miembroTemp = new Miembro();

        if (Conexion.conectar()) {
            String sql = "SELECT RU.id_proyecto AS id_proyecto,"
                    + "RU.id_usuario AS id_usuario, "
                    + "RU.id_rol AS id_rol, "
                    + "P.nombre AS nombre_proyecto, "
                    + "U.nombre_apellido AS nombre_usuario, "
                    + "R.nombre AS nombre_rol "
                    + "FROM roles_usuarios RU "
                    + "JOIN usuarios U ON RU.id_usuario = U.id_usuario "
                    + "JOIN proyectos P ON RU.id_proyecto = P.id_proyecto "
                    + "JOIN roles R ON RU.id_rol = R.id_rol "
                    + "WHERE UPPER(RU.id_proyecto) LIKE '%" + id_proyecto + "%'";

            System.out.println("----->" + sql);

            try {
                ResultSet rs = Conexion.getSt().executeQuery(sql);
                while (rs.next()) {
                    miembroTemp = new Miembro(rs.getString("id_proyecto"),
                            rs.getInt("id_usuario"),
                            rs.getInt("id_rol"),
                            rs.getString("nombre_proyecto"),
                            rs.getString("nombre_usuario"),
                            rs.getString("nombre_rol"));
                    
                    listaMiembros.add(miembroTemp);

                } 
            } catch (SQLException ex) {
                System.err.println("Error :" + ex);
            }
        }
        Conexion.cerrar();
        System.out.println("US: " + listaMiembros);
        
        return listaMiembros;
    }

}
