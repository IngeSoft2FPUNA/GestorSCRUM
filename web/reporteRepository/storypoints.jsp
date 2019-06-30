<%@page import="controladores.SprintControlador"%>
<%@page import="modelos.Sprint"%>
<%@page import="java.util.ArrayList"%>
<%@page import="controladores.ProyectoControlador" %>
<%@page import="org.json.simple.JSONObject" %>

<%
    String id_proyecto = request.getParameter("id_proyecto");

    //obtener todos los sprints de un proyecto
    ArrayList<Sprint> listaSprintsProyecto = SprintControlador.getAllProyectoSprints(id_proyecto);
    ArrayList<String> listaIdSprints = new ArrayList<String>();
    ArrayList<Integer> listaStoryPointsEstimados = new ArrayList<Integer>();
    ArrayList<Integer> listaStoryPointsRealizados = new ArrayList<Integer>();

    for (Sprint sprintTemp : listaSprintsProyecto) {
        listaIdSprints.add("Sprint "+sprintTemp.getId_sprint());
        listaStoryPointsEstimados.add(sprintTemp.getStory_points_estimados());
        listaStoryPointsRealizados.add(sprintTemp.getStory_points_realizados());
    }

    JSONObject obj = new JSONObject();
    obj.put("listaIdSprints", listaIdSprints);
    obj.put("listaStoryPointsEstimados", listaStoryPointsEstimados);
    obj.put("listaStoryPointsRealizados", listaStoryPointsRealizados);

    out.print(obj);
    out.flush();

%>