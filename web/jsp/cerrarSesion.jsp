<%@page import ="org.json.simple.JSONObject"%>;
<%@page import ="java.sql.ResultSet"%>;

<%
    HttpSession sesion = request.getSession();
    sesion.invalidate();
    
    JSONObject obj = new JSONObject();
    obj.put("mensaje","Sesion de Usuario Cerrada");
    
    out.print(obj);
    out.flush();
%>