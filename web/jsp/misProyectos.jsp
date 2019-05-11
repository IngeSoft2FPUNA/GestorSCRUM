<%@page import="controladores.ProyectoControlador" %>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="java.sql.ResultSet" %>

<%
    int id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
   // String nombre_proyecto = request.getParameter("nombre_proyecto");
    System.out.println("datos: " + id_usuario );

    String mensaje = "busqueda exitosa.";
    String contenido = ProyectoControlador.buscarMisProyectos(id_usuario);
    

    JSONObject obj = new JSONObject();
    obj.put("mensaje", mensaje);
    obj.put("contenido", contenido);
    
    System.out.println("Estoy aca 1");
    out.print(obj);
    out.flush();

%>
