<%@page import="controladores.ProyectoControlador" %>
<%@page import="modelos.Proyecto" %>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="java.sql.ResultSet" %>

<%
    //se obtiene el id del formulario
    String id_proyecto = request.getParameter("id_proyecto");
    String nombreProyecto = request.getParameter("nombre_proyecto");

    String tipo = "error";
    String mensaje = "Datos no encontrados";
    String nuevo = "true";

    Proyecto proyecto = new Proyecto();
    proyecto.setId_proyecto(id_proyecto);
    proyecto.setNombre_proyecto(nombreProyecto);
    
    ProyectoControlador.buscarId(proyecto);
    
    if ( !proyecto.getId_proyecto().equalsIgnoreCase("")) {
        tipo = "success";
        mensaje = "Datos encontrados";
        nuevo = "false";
    }else{
        proyecto = new Proyecto();
    }
    
    JSONObject obj = new JSONObject();
    obj.put("tipo", tipo);
    obj.put("mensaje", mensaje);
    obj.put("nuevo", nuevo);
    obj.put("id_proyecto", proyecto.getId_proyecto());
    obj.put("nombre_proyecto", proyecto.getNombre_proyecto());
    obj.put("descripcion_proyecto", proyecto.getDescripcion_proyecto());
    obj.put("fecha_entrega", proyecto.getDuedate());
    
    out.print(obj);
    out.flush();
    


%>
