

<%@page import="org.json.simple.JSONObject"%>
<%@page import="controladores.UsuarioControlador"%>
<%@page import="modelos.Usuario"%>
<%
    String login_usuario = request.getParameter("usuario_usuario");
    String password_usuario = request.getParameter("password_usuario");
    
    String acceso = "false";
    //boolean acceso = UsuariosControlador.validarAcceso(usuario);
    
    Usuario usuario = new Usuario(login_usuario, "", 0, "", password_usuario);
    if (UsuarioControlador.validarAcceso(usuario,request)!=null) {
            acceso = "true";
    }
    
    JSONObject obj = new JSONObject();
    obj.put("acceso",acceso);
    obj.put("usuario",login_usuario);
         
    //obj.put("acceso",String.valueOf(acceso));
    out.print(obj);
    out.flush();
    System.out.println(acceso);
    

%>