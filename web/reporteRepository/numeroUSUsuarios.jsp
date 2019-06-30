<%@page import="modelos.Miembro"%>
<%@page import="controladores.SprintControlador"%>
<%@page import="java.util.ArrayList"%>
<%@page import="controladores.ProyectoControlador" %>
<%@page import="org.json.simple.JSONObject" %>

<%
    String id_proyecto = request.getParameter("id_proyecto");

    //cantidad de US por Usuario
    ArrayList<Integer> listacantidadUSSprint = new ArrayList<Integer>();
    ArrayList<String> listaNombreUsuarios = new ArrayList<String>();


    ArrayList<Miembro> listaMiembros = ProyectoControlador.listarMiembros(id_proyecto);
    int cantidadTemp = 0;
    
    for (Miembro miembro : listaMiembros) {
        cantidadTemp = SprintControlador.getCantidadUSUsuarios(miembro.getId_usuario(), id_proyecto);
        listacantidadUSSprint.add(cantidadTemp);
        listaNombreUsuarios.add(miembro.getNombre_usuario());
    }
    
    System.out.println(listacantidadUSSprint);
    System.out.println(listaNombreUsuarios);

    JSONObject obj = new JSONObject();
    obj.put("listacantidadUSSprint", listacantidadUSSprint);
    obj.put("listaNombreUsuarios", listaNombreUsuarios);

    out.print(obj);
    out.flush();

%>