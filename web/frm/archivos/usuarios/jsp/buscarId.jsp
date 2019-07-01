<%@page import="controladores.UsuarioControlador" %>
<%@page import="modelos.Usuario" %>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="java.sql.ResultSet" %>

<%
    //se obtiene el id del formulario
    int id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
   /* String nombreUsuario = request.getParameter("nombre_usuario");
    String apellidoUsuario = request.getParameter("apellido_usuario");
    int ciUsuario = Integer.parseInt(request.getParameter("ci_usuario"));
;*/

    String tipo = "error";
    String mensaje = "Datos no encontrados";
    String nuevo = "true";

    Usuario usuario = new Usuario();
    usuario.setCedula(id_usuario);
    String activo = "false";
 /*   usuario.setNombre_usuario(nombreUsuario);
    usuario.setApellido_usuario(apellidoUsuario);
    usuario.setCi_usuario(ciUsuario);
*/
    
    usuario = UsuarioControlador.buscarId(usuario);

    if ( usuario.getCedula()!= 0) {
        tipo = "success";
        mensaje = "Datos encontrados";
        nuevo = "false";
        if(usuario.isActivo()){
            activo = "true";
        }else{
            activo = "false";
        }
    }else{
        usuario = new Usuario();
    }

    JSONObject obj = new JSONObject();
    obj.put("tipo", tipo);
    obj.put("mensaje", mensaje);
    obj.put("nuevo", nuevo);
    
    obj.put("id_usuario", usuario.getCedula());
    obj.put("nombre_usuario", usuario.getNombre());
    obj.put("cedula", usuario.getCedula());
    obj.put("correo", usuario.getEmail());
    obj.put("login_usuario", usuario.getUsuario());
    obj.put("password_usuario", usuario.getPassword());
    obj.put("id_rol_sistema", usuario.getRol_sistema());
    obj.put("activo", activo);
    
    out.print(obj);
    out.flush();
%>
