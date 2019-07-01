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
    public static boolean agregar(Usuario usuario) {
        boolean valor = false;
        String cadena_activo = usuario.isActivo() ? "TRUE" : "FALSE";

        if (Conexion.conectar()) {
            String sql = "INSERT INTO usuarios(nombre_apellido, cedula, "
                    + "correo_electronico, nick_usuario, password, activo, id_rol_sistema)"
                    + "values('" + usuario.getNombre() + "'"
                    + "," + usuario.getCedula()
                    + ",'" + usuario.getEmail() + "'"
                    + ",'" + usuario.getUsuario() + "'"
                    + ",'" + Utiles.md5(Utiles.quitarGuiones(usuario.getPassword())) + "'"
                    + ",'" + cadena_activo + "'"
                    + ",'" + usuario.getRol_sistema() + "'"
                    + ")";

            System.out.println(sql);

            try {
                Conexion.getSt().executeUpdate(sql);
                valor = true;
            } catch (SQLException ex) {
                System.err.println("Error :" + ex.getMessage());
            }
        }
        Conexion.cerrar();
        return valor;
    }//fin del metodo agregar

    public static Usuario buscarId(Usuario usuario) {
        if (Conexion.conectar()) {
            String sql = "SELECT * FROM usuarios WHERE cedula = '" + usuario.getCedula() + "'";
            try {
                ResultSet rs = Conexion.getSt().executeQuery(sql);
                if (rs.next()) {
                    usuario.setNombre(rs.getString("nombre_apellido"));
                    usuario.setCedula(Integer.parseInt(rs.getString("cedula")));
                    usuario.setEmail(rs.getString("correo_electronico"));
                    usuario.setUsuario(rs.getString("nick_usuario"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setActivo(rs.getBoolean("activo"));
                    usuario.setRol_sistema(rs.getInt("id_rol_sistema"));
                } else {
                    usuario.setCedula(0);
                    usuario.setNombre("");

                }
            } catch (SQLException ex) {
                System.err.println("Error :" + ex);
            }
        }
        Conexion.cerrar();
        return usuario;

    }   
    
    public static Usuario buscarUsuario(int id_usuario) {
        
        Usuario usuario = new Usuario();
        
        if (Conexion.conectar()) {
            String sql = "SELECT * FROM usuarios WHERE id_usuario = '" + id_usuario+ "'";
            try {
                ResultSet rs = Conexion.getSt().executeQuery(sql);
                if (rs.next()) {
                    usuario.setNombre(rs.getString("nombre_apellido"));
                    usuario.setCedula(Integer.parseInt(rs.getString("cedula")));
                    usuario.setEmail(rs.getString("correo_electronico"));
                    usuario.setUsuario(rs.getString("nick_usuario"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setActivo(rs.getBoolean("activo"));
                } else {
                    usuario.setCedula(0);
                    usuario.setNombre("");

                }
            } catch (SQLException ex) {
                System.err.println("Error :" + ex);
            }
        }
        Conexion.cerrar();
        return usuario;

    }

    public static String buscarNombre(String nombre, int pagina) {
        int offset = (pagina - 1) * Utiles.REGISTRO_PAGINA;
        String valor = "";

        if (Conexion.conectar()) {

            try {
                String sql = "SELECT * FROM usuarios WHERE "
                        + "UPPER(nombre_apellido) LIKE '%"
                        + nombre.toUpperCase() + "%'" + "ORDER BY id_usuario offset "
                        + offset + "LIMIT "
                        + Utiles.REGISTRO_PAGINA;
                System.out.println("----->" + sql);
                try (PreparedStatement ps = Conexion.getConn().prepareStatement(sql)) {
                    ResultSet rs = ps.executeQuery();
                    String tabla = "";

                    while (rs.next()) {
                        tabla += "<tr>"
                                + "<td>" + rs.getInt("cedula") + "</td>"
                                + "<td>" + rs.getString("nombre_apellido") + "</td>"
                                + "<td>" + rs.getString("nick_usuario") + "</td>"
                                + "<td>" + rs.getString("correo_electronico") + "</td>"
                                + "<td>" + rs.getString("password") + "</td>"
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

    public static boolean modificar(Usuario usuario, int id_usuario) {
        boolean valor = false;
        String cadena_activo = usuario.isActivo() ? "TRUE" : "FALSE";

        if (Conexion.conectar()) {

            String sql = "UPDATE usuarios SET "
                    + "nombre_apellido = '" + usuario.getNombre() + "',"
                    + "cedula = " + usuario.getCedula() + ","
                    + "correo_electronico = '" + usuario.getEmail() + "',"
                    + "nick_usuario = '" + usuario.getUsuario() + "',"
                    + "password = '" + Utiles.md5(Utiles.quitarGuiones(usuario.getPassword())) + "',"
                    + "id_rol_sistema = '" + usuario.getRol_sistema() + "',"
                    + "activo = '" + cadena_activo + "'"
                    + "WHERE cedula= '" + id_usuario + "'";

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

    public static boolean eliminar(Usuario usuario) {
        boolean valor = false;

        if (Conexion.conectar()) {

            String sql = "UPDATE usuarios SET activo = 'false' WHERE cedula = '" + usuario.getCedula() + "'";
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

    public static Usuario validarAcceso(Usuario usuario, HttpServletRequest request) {
        System.out.println("login:" + usuario.getUsuario());
        System.out.println("password:" + usuario.getPassword());
        System.out.println("password md5:"
                + Utiles.md5(Utiles.quitarGuiones(usuario.getPassword())));
        if (Conexion.conectar()) {
            try {
                String sql = "SELECT * FROM usuarios WHERE nick_usuario = ? "
                        + "AND password = ? AND activo = 'TRUE'";

                try (PreparedStatement ps = Conexion.getConn().prepareStatement(sql)) {
                    ps.setString(1, Utiles.quitarGuiones(usuario.getUsuario()));
                    ps.setString(2, Utiles.md5(Utiles.quitarGuiones(usuario.getPassword())));

                    ResultSet rs = ps.executeQuery();
                    System.out.println("---------->" + sql);

                    if (rs.next()) {
                        HttpSession sesion = request.getSession(true);
                        usuario = new Usuario();
                        usuario.setId_usuario(rs.getInt("id_usuario"));
                        usuario.setNombre(rs.getString("nombre_apellido"));
                        usuario.setUsuario(rs.getString("nick_usuario"));
                        usuario.setPassword(rs.getString("password"));
                        usuario.setRol_sistema(rs.getInt("id_rol_sistema"));
                        System.out.println(Utiles.md5(rs.getString("password")));

                        sesion.setAttribute("usuarioLogueado", usuario);

                    } else {
                        usuario = null;
                    }
                    ps.close();
                } catch (SQLException e) {
                    System.out.println("===>" + e.getLocalizedMessage());
                }
            } catch (Exception e) {
            }
        }
        Conexion.cerrar();
        return usuario;
    }

    public static ArrayList<Usuario> listarUsuarios(String id_proyecto) {
        ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
        Usuario usuarioTemp = new Usuario();
        if (Conexion.conectar()) {

            try {
                String sql = "SELECT * FROM usuarios "
                        + "WHERE id_usuario NOT IN (SELECT id_usuario FROM roles_usuarios WHERE id_proyecto = '"+id_proyecto+"') "
                        + "AND activo = 'TRUE' ";

                System.out.println("----->" + sql);
                try (PreparedStatement ps = Conexion.getConn().prepareStatement(sql)) {
                    ResultSet rs = ps.executeQuery();
                    String tabla = "";

                    while (rs.next()) {
                        usuarioTemp = new Usuario(
                                rs.getInt("id_usuario"),
                                rs.getString("nick_usuario"),
                                rs.getString("nombre_apellido"),
                                rs.getInt("cedula"),
                                rs.getString("correo_electronico"),
                                rs.getString("password"),
                                rs.getBoolean("activo")
                        );

                        listaUsuarios.add(usuarioTemp);
                    }
                    ps.close();
                } catch (SQLException e) {
                    System.out.println("Error: " + e);
                }
                Conexion.cerrar();
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
        Conexion.cerrar();

        return listaUsuarios;
    }

    public static void agregarMiembro(String id_proyecto, int id_rol, int id_miembro) {

        if (Conexion.conectar()) {
            String sql = "INSERT INTO roles_usuarios(id_rol, id_usuario, id_proyecto)"
                    + "values(" + id_rol
                    + "," + id_miembro
                    + ",'" + id_proyecto
                    + "')";

            System.out.println(sql);

            try {
                Conexion.getSt().executeUpdate(sql);
            } catch (SQLException ex) {
                System.err.println("Error :" + ex.getMessage());
            }
        }
    }
    
    public static boolean actualizarMiembro(String id_proyecto, int id_rol, int id_miembro) {

        boolean actualizado = false;
        
        if (Conexion.conectar()) {
            String sql = "UPDATE roles_usuarios SET"
                    + " id_rol = " + id_rol
                    + " WHERE id_proyecto = '" + id_proyecto 
                    + "' AND id_usuario = " + id_miembro ;

            System.out.println(sql);

            try {
                Conexion.getSt().executeUpdate(sql);
                actualizado = true;
            } catch (SQLException ex) {
                System.err.println("Error :" + ex.getMessage());
            }
        }
        Conexion.cerrar();
        
        return actualizado;
    }

    public static boolean eliminarMiembro(String id_proyecto, int id_rol, int id_miembro) {

        boolean eliminado = false;
        
        if (Conexion.conectar()) {
            String sql = "DELETE FROM roles_usuarios"
                    + " WHERE id_rol = " + id_rol
                    + " AND id_proyecto = '" + id_proyecto 
                    + "' AND id_usuario = " + id_miembro ;

            System.out.println(sql);

            try {
                Conexion.getSt().executeUpdate(sql);
                eliminado = true;
            } catch (SQLException ex) {
                System.err.println("Error :" + ex.getMessage());
            }
        }
        Conexion.cerrar();
        
        return eliminado;
    }
}
