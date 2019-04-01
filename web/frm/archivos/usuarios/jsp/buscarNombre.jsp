<%@page import="controladores.UsuarioControlador" %>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="java.sql.ResultSet" %>

<%
    String nombre_usuario = request.getParameter("bnombre_usuario");
    int pagina = Integer.parseInt(request.getParameter("bpagina"));
    

    String mensaje = "busqueda exitosa.";
    String contenido = UsuarioControlador.buscarNombre(nombre_usuario, pagina);
    

    JSONObject obj = new JSONObject();
    obj.put("mensaje", mensaje);
    obj.put("contenido", contenido);
    
    System.out.println("Estoy aca 1");
    out.print(obj);
    out.flush();

%>