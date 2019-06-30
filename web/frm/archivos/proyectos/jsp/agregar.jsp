<%@page import="controladores.ProyectoControlador" %>
<%@page import="controladores.UsuarioControlador" %>
<%@page import="modelos.Proyecto" %>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="java.sql.ResultSet" %>


<%
    //se capturan los datos del formulario
    String id_proyecto = request.getParameter("id_proyecto");
    HttpSession sesion = request.getSession();
    int id_usuario = (Integer)sesion.getAttribute("id_usuario");
    String nombre_proyecto = request.getParameter("nombre_proyecto");
    String descripcion_proyecto = request.getParameter("descripcion_proyecto");
    String duedate = request.getParameter("fecha_entrega");
    
    //se definen variables para el tipo de mensaje y el mensaje
    String tipo = "error";
    String mensaje = "Datos no agregados";
    
    //se instancia un objeto del tipo proyecto
    Proyecto proyecto = new Proyecto();
    proyecto.setId_proyecto(id_proyecto);
    proyecto.setNombre_proyecto(nombre_proyecto);
    proyecto.setDescripcion_proyecto(descripcion_proyecto);
    proyecto.setDuedate(duedate);
    
    //se intenta agregar un nuevo proyecto a la bd
    if (ProyectoControlador.agregar(proyecto)) {
            UsuarioControlador.agregarMiembro(id_proyecto, 2, id_usuario);
            tipo = "success";
            mensaje = "Datos Agregados.";                       
    }
    
    JSONObject obj = new JSONObject();
    obj.put("tipo", tipo);
    obj.put("mensaje", mensaje);
    out.print(obj);
    out.flush();

%>