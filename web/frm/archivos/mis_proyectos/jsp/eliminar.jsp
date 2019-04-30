<%@page import="controladores.ProyectoControlador" %>
<%@page import="modelos.Proyecto" %>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="java.sql.ResultSet" %>


<%
    //se capturan los datos del formulario, creo
    String id_proyecto = request.getParameter("id_proyecto");
    String nombre_proyecto = request.getParameter("nombre_proyecto");
    
    //se definen variables para el tipo de mensaje y el mensaje
    String tipo = "error";
    String mensaje = "Datos no eliminados";
    
    //se instancia un objeto del tipo proyecto
    Proyecto proyecto = new Proyecto();
    proyecto.setId_proyecto(id_proyecto);
    proyecto.setNombre_proyecto(nombre_proyecto);
  
    //se intenta agregar un nuevo proyecto a la bd
    if (ProyectoControlador.eliminar(proyecto)) {
            tipo = "success";
            mensaje = "Datos eliminados.";                       
    }
    
    JSONObject obj = new JSONObject();
    obj.put("tipo", tipo);
    obj.put("mensaje", mensaje);
    out.print(obj);
    out.flush();

%>