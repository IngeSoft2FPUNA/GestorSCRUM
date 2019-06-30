<%@page import="org.json.simple.JSONArray"%>
<%@page import="controladores.SprintControlador"%>
<%@page import="modelos.Sprint"%>
<%@page import="java.util.ArrayList"%>
<%@page import="controladores.ProyectoControlador" %>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="java.sql.ResultSet" %>

<%
    String id_proyecto = request.getParameter("id_proyecto");

    //obtener todos los sprints de un proyecto
    ArrayList<Sprint> listaSprintsProyecto = SprintControlador.getAllProyectoSprints(id_proyecto);
    ArrayList<String> listaIdSprints = new ArrayList<String>();
    ArrayList<Integer> listaDuracionEstimada = new ArrayList<Integer>();
    ArrayList<Integer> listaDuracionReal = new ArrayList<Integer>();

    for (Sprint sprintTemp : listaSprintsProyecto) {
        listaIdSprints.add("Sprint "+sprintTemp.getId_sprint());
        listaDuracionEstimada.add(sprintTemp.getDuracion_estimada());
        listaDuracionReal.add(sprintTemp.getDuracion_real());
    }

    System.out.println(listaDuracionEstimada);
    System.out.println(listaDuracionReal);
    
    
    JSONObject obj = new JSONObject();
    obj.put("listaIdSprints", listaIdSprints);
    obj.put("listaDuracionEstimada", listaDuracionEstimada);
    obj.put("listaDuracionReal", listaDuracionReal);

    out.print(obj);
    out.flush();

%>