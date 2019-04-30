<%@page import="controladores.ProyectoControlador" %>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="java.sql.ResultSet" %>

<%
    String nombre_proyecto = request.getParameter("bnombre_proyecto");
    int pagina = Integer.parseInt(request.getParameter("bpagina"));
    

    String mensaje = "busqueda exitosa.";
    String contenido = ProyectoControlador.buscarNombre(nombre_proyecto, pagina);
    

    JSONObject obj = new JSONObject();
    obj.put("mensaje", mensaje);
    obj.put("contenido", contenido);
    
    System.out.println("Estoy aca 1");
    out.print(obj);
    out.flush();

%>