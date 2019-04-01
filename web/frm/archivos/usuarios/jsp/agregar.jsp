<%@page import="controladores.UsuarioControlador" %>
<%@page import="modelos.Usuario" %>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="java.sql.ResultSet" %>


<%
    //se capturan los datos del formulario, creo
    int id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
    String nombre_usuario = request.getParameter("nombre_usuario");
    int cedula = Integer.parseInt(request.getParameter("cedula"));
    String correo_electronico = request.getParameter("correo_electronico");
    String nick_usuario = request.getParameter("nick_usuario");
    String password_usuario = request.getParameter("password_usuario");
    
    //se definen variables para el tipo de mensaje y el mensaje
    String tipo = "error";
    String mensaje = "Datos no agregados";
    
    //se instancia un objeto del tipo usuario
    Usuario usuario = new Usuario();
    //usuario.setId_usuario(id_usuario);
    usuario.setNombre(nombre_usuario);
    usuario.setCedula(cedula);
    usuario.setEmail(correo_electronico);
    usuario.setUsuario(nick_usuario);
    usuario.setPassword(password_usuario);
    
    //se intenta agregar un nuevo usuario a la bd
    if (UsuarioControlador.agregar(usuario)) {
            tipo = "success";
            mensaje = "Datos Agregados.";                       
    }
    
    JSONObject obj = new JSONObject();
    obj.put("tipo", tipo);
    obj.put("mensaje", mensaje);
    out.print(obj);
    out.flush();

%>