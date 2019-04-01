<%@page import="controladores.RubrosControlador" %>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="java.sql.ResultSet" %>

<%
    String nombre_rubro = request.getParameter("bnombre_rubro");
    int pagina = Integer.parseInt(request.getParameter("bpagina"));
    

    String mensaje = "busqueda exitosa.";
    String contenido = RubrosControlador.buscarNombre(nombre_rubro, pagina);
    

    JSONObject obj = new JSONObject();
    obj.put("mensaje", mensaje);
    obj.put("contenido", contenido);
    
    System.out.println("Estoy aca 1");
    out.print(obj);
    out.flush();

%>