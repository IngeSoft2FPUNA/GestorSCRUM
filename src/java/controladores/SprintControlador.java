package controladores;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import modelos.Sprint;
import utiles.Conexion;

public class SprintControlador {

    public static Sprint obtenerSprintActual(String id_proyecto) {
        Sprint sprintActual = new Sprint();

        if (Conexion.conectar()) {
            String sql = "SELECT *"
                    + "FROM sprints "
                    + "WHERE id_proyecto = '" + id_proyecto + "' "
                    + "AND estado_sprint = 7"; //que el estado del sprint sea iniciado
            System.out.println("----->" + sql);
            try (PreparedStatement ps = Conexion.getConn().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    sprintActual.setId_proyecto(rs.getString("id_proyecto"));
                    sprintActual.setId_sprint(rs.getInt("id_sprint"));
                    sprintActual.setFecha_inicio(rs.getString("fecha_inicio"));
                    sprintActual.setFecha_fin_estimada(rs.getString("fecha_fin_estimado"));
                    sprintActual.setFecha_fin_real(rs.getString("fecha_fin_real"));
                    sprintActual.setDuracion_estimada(rs.getInt("duracion_estimada"));
                    sprintActual.setDuracion_real(rs.getInt("duracion_real"));
                    sprintActual.setStory_points_estimados(rs.getInt("story_points_estimados"));
                    sprintActual.setStory_points_realizados(rs.getInt("story_points_realizados"));
                    sprintActual.setEstado_sprint(rs.getInt("estado_sprint"));
                } else {
                    sprintActual.setId_proyecto(rs.getString("id_proyecto"));
                    sprintActual.setId_sprint(0);
                    sprintActual.setFecha_inicio("--/--/--");
                    sprintActual.setFecha_fin_estimada("--/--/--");
                    sprintActual.setFecha_fin_real("--/--/--");
                    sprintActual.setDuracion_estimada(0);
                    sprintActual.setDuracion_real(0);
                    sprintActual.setStory_points_estimados(0);
                    sprintActual.setStory_points_realizados(0);
                    sprintActual.setEstado_sprint(8);
                }

                ps.close();
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }

        }
        Conexion.cerrar();
        return sprintActual;
    }

    public static int obtenerStoryPointsRealizados(String id_proyecto) {
        int story_points_realizados = 0;

        if (Conexion.conectar()) {
            String sql = "SELECT SUM(story_points) AS story_points_realizados FROM userstories "
                    + "WHERE id_sprint = (SELECT "
                    + "CASE WHEN MAX(id_sprint) IS NULL THEN 0 ELSE MAX(id_sprint) END  AS sprint_actual "
                    + "FROM sprints "
                    + "WHERE id_proyecto ='" + id_proyecto + "') "
                    + "AND id_proyecto = '" + id_proyecto + "'"
                    + "AND id_estados = 3 " //que su estado sea DONE
                    + "AND id_estados != 6 "; //que no este eliminado

            System.out.println("----->" + sql);
            try (PreparedStatement ps = Conexion.getConn().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    story_points_realizados = rs.getInt("story_points_realizados");
                }

                ps.close();
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
        Conexion.cerrar();

        return story_points_realizados;
    }

    public static void iniciarSprint(Sprint sprint) {
        if (Conexion.conectar()) {

            String sql = "INSERT INTO sprints (id_sprint,id_proyecto, duracion_estimada, fecha_inicio, fecha_fin_estimado, estado_sprint, story_points_estimados) \n"
                    + "VALUES"
                    + "	((SELECT  "
                    + "		CASE WHEN MAX(id_sprint) IS NULL THEN 0 ELSE MAX(id_sprint) END  AS sprint_actual"
                    + "	FROM sprints"
                    + "	WHERE id_proyecto = '" + sprint.getId_proyecto() + "')+1, "
                    + "	'" + sprint.getId_proyecto() + "', "
                    + "" + sprint.getDuracion_estimada() + ","
                    + "	CURRENT_DATE, "
                    + "	CURRENT_DATE + " + sprint.getDuracion_estimada() + ", "
                    + "	7, "
                    + "(SELECT SUM(story_points) FROM userstories "
                    + "WHERE id_proyecto = '" + sprint.getId_proyecto() + "' "
                    + "AND id_estados_backlog = 4"
                    + "AND id_estados != 6))";

            System.out.println(sql);

            try {
                Conexion.getSt().execute(sql);
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
        Conexion.cerrar();
    }

    public static void asignarSprintUS(Sprint sprint) {
        if (Conexion.conectar()) {
            String sql = "UPDATE userstories SET id_sprint = " + sprint.getId_sprint()
                    + "WHERE id_estados_backlog = 4 "
                    + "AND id_proyecto = '" + sprint.getId_proyecto() + "' "
                    + "AND id_estados != 6 "
                    + "AND id_sprint IS NULL OR id_sprint = 0;";

            System.out.println(sql);

            try {
                Conexion.getSt().executeUpdate(sql);
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
        Conexion.cerrar();
    }

    public static void actualizarSprint(Sprint sprint) {
        if (Conexion.conectar()) {
            String sql = "UPDATE sprints SET "
                    + "duracion_real = (SELECT CURRENT_DATE - fecha_inicio "
                    + "FROM sprints "
                    + "WHERE id_proyecto = '" + sprint.getId_proyecto() + "' "
                    + "AND id_sprint = " + sprint.getId_sprint() + "), "
                    //sumar todos los storypoints de los us que tengan estado sprint (id_estados_backlog=4)                  
                    + "story_points_estimados = (SELECT SUM(story_points) FROM userstories "
                    + "WHERE id_proyecto = '" + sprint.getId_proyecto() + "' "
                    + " AND id_sprint = " + sprint.getId_sprint()
                    + " AND id_estados_backlog = 4"
                    + " AND id_estados != 6), "
                    //sumar todos los storypoints de los us que tengan estado sprint (id_estados_backlog=4)
                    //y su estado en DONE (id_estados = 3) 
                    + "story_points_realizados = (SELECT SUM(story_points) FROM userstories "
                    + "	WHERE id_proyecto = '" + sprint.getId_proyecto() + "' "
                    + "	AND id_sprint = " + sprint.getId_sprint()
                    + "	AND id_estados = 3 "//su estado sea DONE
                    + " AND id_estados != 6)" // que no este eliminado
                    + " WHERE id_sprint = " + sprint.getId_sprint()
                    + " AND id_proyecto = '" + sprint.getId_proyecto() + "'";

            System.out.println(sql);

            try {
                Conexion.getSt().executeUpdate(sql);
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
        Conexion.cerrar();
    }

    public static void finalizarSprint(Sprint sprint) {
        if (Conexion.conectar()) {
            String sql = "UPDATE sprints SET"
                    + " fecha_fin_real = CURRENT_DATE,"
                    + " story_points_realizados =  (SELECT SUM(story_points) FROM userstories "
                    + "	WHERE id_proyecto = '" + sprint.getId_proyecto() + "' "
                    + "	AND id_sprint = " + sprint.getId_sprint()
                    + "	AND id_estados = 3 )," //que el estado sea done
                    + " estado_sprint = 8" //cambiar estado a finalizado
                    + " WHERE id_proyecto = '" + sprint.getId_proyecto() + "' "
                    + " AND id_sprint = " + sprint.getId_sprint();

            System.out.println(sql);

            try {
                Conexion.getSt().executeUpdate(sql);
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
        Conexion.cerrar();
    }

    public static void moverUSBacklog(Sprint sprint) {
        if (Conexion.conectar()) {
            String sql = "UPDATE userstories SET \n"
                    + "id_estados_backlog = 5, \n"
                    + "id_estados = 1, \n" //reset de estado a TO DO
                    + "id_sprint = NULL\n"
                    + "WHERE id_proyecto = '" + sprint.getId_proyecto() + "'\n"
                    + "AND id_sprint = " + sprint.getId_sprint() + "\n"
                    + "AND id_estados != 3\n" //que no tengan estado DONE
                    + "AND id_estados != 6"; //que no esten ELIMINADOS

            System.out.println(sql);

            try {
                Conexion.getSt().executeUpdate(sql);
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
        Conexion.cerrar();
    }

    public static ArrayList<Sprint> getAllProyectoSprints(String id_proyecto) {

        ArrayList<Sprint> listaSprintsProyecto = new ArrayList<Sprint>();
        Sprint sprintTemp = new Sprint();

        if (Conexion.conectar()) {
            String sql = "SELECT * FROM sprints WHERE id_proyecto = '" + id_proyecto + "' ORDER BY id_sprint;";

            System.out.println(sql);

            try (PreparedStatement ps = Conexion.getConn().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    sprintTemp = new Sprint(rs.getInt("id_sprint"),
                            rs.getString("id_proyecto"),
                            rs.getInt("duracion_estimada"),
                            rs.getInt("duracion_real"),
                            rs.getString("fecha_inicio"),
                            rs.getString("fecha_fin_estimado"),
                            rs.getString("fecha_fin_real"),
                            rs.getInt("story_points_estimados"),
                            rs.getInt("story_points_realizados"),
                            rs.getInt("estado_sprint"));
                    listaSprintsProyecto.add(sprintTemp);
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
        Conexion.cerrar();

        return listaSprintsProyecto;

    }

    public static int getNroUSSprint(String id_proyecto, int id_sprint) {

        int cantidad_us = 0;
        if (Conexion.conectar()) {
            String sql = "SELECT COUNT (*) AS cantidad_us\n"
                    + " FROM userstories \n"
                    + " WHERE id_proyecto = '" + id_proyecto + "'\n"
                    + " AND id_sprint = " + id_sprint
                    + " AND id_estados != 6 AND id_estados = 3\n" //que no este eliminado y que su estado sea DONE
                    + " AND id_estados_backlog = 4"; //que este en estado Sprint Backlog

            System.out.println("----->" + sql);
            try (PreparedStatement ps = Conexion.getConn().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    cantidad_us = rs.getInt("cantidad_us");
                }

                ps.close();
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
        Conexion.cerrar();

        return cantidad_us;

    }

    public static int getCantidadUSUsuarios(int id_usuario, String id_proyecto) {

        int cantidad_us = 0;
        if (Conexion.conectar()) {
            String sql = "SELECT count(*) AS cantidad_us FROM userstories  \n"
                    + "WHERE id_proyecto = '" + id_proyecto + "'\n"
                    + "AND id_estados = 3\n" //DONE
                    + "AND id_estados_backlog = 4\n" //SPRINT BACKLOG
                    + "AND id_responsable = " + id_usuario;

            System.out.println("----->" + sql);
            try (PreparedStatement ps = Conexion.getConn().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    cantidad_us = rs.getInt("cantidad_us");
                }

                ps.close();
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
        Conexion.cerrar();

        return cantidad_us;

    }

    public static ArrayList<Integer> burndownChart(String id_proyecto, int id_sprint_actual) {

        ArrayList<Integer> listaBurnDown = new ArrayList<Integer>();
        Array listaTemp = null;
        if (Conexion.conectar()) {
            String sql = "SELECT burndown_chart(id_proyecto,id_sprint) AS burndown "
                    + "FROM sprints "
                    + "WHERE id_proyecto = '" + id_proyecto + "' "
                    + "AND id_sprint = " + id_sprint_actual ;

            System.out.println("----->" + sql);
            try (PreparedStatement ps = Conexion.getConn().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                   
                    listaTemp = rs.getArray("burndown");
                    Integer[] listaBurndownInt = (Integer[])listaTemp.getArray();

                    for (int i = 0; i < listaBurndownInt.length; i++) {
                        System.out.println("dia " + i + ": " + listaBurndownInt[i]);
                        listaBurnDown.add(listaBurndownInt[i]);
                    }

                }

                ps.close();
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
        Conexion.cerrar();

        return listaBurnDown;

    }

}
