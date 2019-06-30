<%@page import="org.json.simple.JSONArray"%>
<%@page import="controladores.SprintControlador"%>
<%@page import="modelos.Sprint"%>
<%@page import="java.util.ArrayList"%>
<%@page import="controladores.ProyectoControlador" %>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="java.sql.ResultSet" %>

<%
    String id_proyecto = request.getParameter("id_proyecto");
    Sprint sprint_actual = SprintControlador.obtenerSprintActual(id_proyecto);
    System.out.println(sprint_actual.getId_sprint());
    
    //obtener todos los sprints de un proyecto
    ArrayList<Integer> listaBurnDownChart = SprintControlador.burndownChart(id_proyecto,sprint_actual.getId_sprint());
    ArrayList<String> listaDias = new ArrayList<String>(); 
    
    System.out.println(listaBurnDownChart);
    
    for (int i = 0; i <= listaBurnDownChart.size(); i++) {
        listaDias.add("Dia " + i);
    }
    
    JSONObject obj = new JSONObject();
    obj.put("listaBurnDownChart", listaBurnDownChart);
    obj.put("listaDias", listaDias);
    obj.put("cantidadStoryPointsEstimados", sprint_actual.getStory_points_estimados());

    out.print(obj);
    out.flush();

%>