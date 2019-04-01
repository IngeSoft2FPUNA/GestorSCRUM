<%@page import="controladores.RubrosControlador" %>
<%@page import="modelos.Rubros" %>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="java.sql.ResultSet" %>

<%
    //se obtiene el id del formulario
    int id_rubro = Integer.parseInt(request.getParameter("id_rubro"));
    String nombreRubro = request.getParameter("nombre_rubro");

    String tipo = "error";
    String mensaje = "Datos no encontrados";
    String nuevo = "true";

    Rubros rubro = new Rubros();
    rubro.setId_rubro(id_rubro);
    rubro.setNombreRubro(nombreRubro);
    RubrosControlador.buscarId(rubro);
    if ( rubro.getId_rubro() != 0) {
        tipo = "success";
        mensaje = "Datos encontrados";
        nuevo = "false";
    }else{
        rubro = new Rubros();
    }

    JSONObject obj = new JSONObject();
    obj.put("tipo", tipo);
    obj.put("mensaje", mensaje);
    obj.put("nuevo", nuevo);
    obj.put("id_rubro", rubro.getId_rubro());
    obj.put("nombre_rubro", rubro.getNombreRubro());
    
    out.print(obj);
    out.flush();
    


%>
