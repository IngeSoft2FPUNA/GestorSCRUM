<%@page import="java.util.ArrayList"%>
<%@page import="modelos.UserStory"%>
<%@page import="controladores.UserstoryControlador"%>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="java.sql.ResultSet" %>

<%
    String id_usuario = request.getParameter("id_usuario");
    String id_proyecto = request.getParameter("id_proyecto");
    System.out.println("id_usuario: " + id_usuario + "id_proyecto: " + id_proyecto);

    String mensaje = "busqueda exitosa.";
    ArrayList<UserStory> contenido = UserstoryControlador.buscarUSBacklog(id_usuario, id_proyecto);
    

    JSONObject obj = new JSONObject();
    obj.put("mensaje", mensaje);
    obj.put("contenido", contenido);
    
    System.out.println("Estoy aca 1");
    out.print(obj);
    out.flush();

%>