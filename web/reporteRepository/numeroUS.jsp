<%@page import="controladores.SprintControlador"%>
<%@page import="modelos.Sprint"%>
<%@page import="java.util.ArrayList"%>
<%@page import="controladores.ProyectoControlador" %>
<%@page import="org.json.simple.JSONObject" %>

<%
    String id_proyecto = request.getParameter("id_proyecto");

    //cantidad de US por sprint
    ArrayList<Integer> cantidadUSSprint = new ArrayList<Integer>();
    ArrayList<String> listaIdSprints = new ArrayList<String>();

    //se obtiene el ultimo sprint, cuyo id corresponde a la cantidad de sprints 
    //del proyecto
    int id_sprint_actual = SprintControlador.obtenerSprintActual(id_proyecto).getId_sprint();
    int cantidadTemp = 0;
    
    for (int i = 1; i <= id_sprint_actual; i++) {
        cantidadTemp = SprintControlador.getNroUSSprint(id_proyecto, i);
        cantidadUSSprint.add(cantidadTemp);
        listaIdSprints.add("Sprint" + i);
    }
    
    System.out.println(cantidadUSSprint);
    System.out.println(listaIdSprints);

    JSONObject obj = new JSONObject();
    obj.put("listaIdSprints", listaIdSprints);
    obj.put("cantidadUSSprint", cantidadUSSprint);

    out.print(obj);
    out.flush();

%>