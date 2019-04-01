<%@page import="controladores.UsuarioControlador" %>
<%@page import="modelos.Usuario" %>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="java.sql.ResultSet" %>


<%
    //se capturan los datos del formulario, creo
    int id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
    String nombre_usuario = request.getParameter("nick_usuario");
    
    //se definen variables para el tipo de mensaje y el mensaje
    String tipo = "error";
    String mensaje = "Datos no eliminados";
    
    //se instancia un objeto del tipo usuario
    Usuario usuario = new Usuario();
    usuario.setCedula(id_usuario);
    usuario.setUsuario(nombre_usuario);
    
    //se intenta agregar un nuevo usuario a la bd
    if (UsuarioControlador.eliminar(usuario)) {
            tipo = "success";
            mensaje = "Datos eliminados.";                       
    }
    
    JSONObject obj = new JSONObject();
    obj.put("tipo", tipo);
    obj.put("mensaje", mensaje);
    out.print(obj);
    out.flush();

%>