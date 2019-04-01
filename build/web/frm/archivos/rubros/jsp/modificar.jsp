<%@page import="controladores.RubrosControlador" %>
<%@page import="modelos.Rubros" %>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="java.sql.ResultSet" %>


<%
    //se capturan los datos del formulario, creo
    int id_rubro = Integer.parseInt(request.getParameter("id_rubro"));
    String nombre_rubro = request.getParameter("nombre_rubro");
    
    //se definen variables para el tipo de mensaje y el mensaje
    String tipo = "error";
    String mensaje = "Datos no Modificados";
    
    //se instancia un objeto del tipo rubro
    Rubros rubro = new Rubros();
    rubro.setId_rubro(id_rubro);
    rubro.setNombreRubro(nombre_rubro);
    
    //se intenta agregar un nuevo rubro a la bd
    if (RubrosControlador.modificar(rubro)) {
            tipo = "success";
            mensaje = "Datos Modificados.";                       
    }
    
    JSONObject obj = new JSONObject();
    obj.put("tipo", tipo);
    obj.put("mensaje", mensaje);
    out.print(obj);
    out.flush();

%>