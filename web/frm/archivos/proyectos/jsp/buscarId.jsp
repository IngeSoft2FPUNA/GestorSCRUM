<%@page import="controladores.ProyectoControlador" %>
<%@page import="modelos.Proyecto" %>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="java.sql.ResultSet" %>

<%
    //se obtiene el id del formulario
    int id_proyecto = Integer.parseInt(request.getParameter("id_proyecto"));
    String nombreProyecto = request.getParameter("nombre_proyecto");

    String tipo = "error";
    String mensaje = "Datos no encontrados";
    String nuevo = "true";

    Proyecto proyecto = new Proyecto();
    proyecto.setId_proyecto(id_proyecto);
    proyecto.setNombreProyecto(nombreProyecto);
    ProyectoControlador.buscarId(proyecto);
    if ( proyecto.getId_proyecto() != 0) {
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
    obj.put("nombre_proyecto", proyecto.getNombreProyecto());
    
    out.print(obj);
    out.flush();
    


%>
