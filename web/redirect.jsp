<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String accion = (String)request.getAttribute("accion");
            String redireccion = (String)request.getAttribute("redireccion");
            String id_proyecto = (String)request.getAttribute("id_proyecto");
            String id_sprint_actual = (String)request.getAttribute("id_sprint_actual");
            System.out.println("id_sprint_actual redirect :"+ id_sprint_actual);
        %>
        
        <form name="redirect" action="MainServlet" method="GET">
            <input type="hidden" name="accion" value="<%=accion%>">
            <input type="hidden" name="redireccion" value="<%=redireccion%>">
            <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>">
            <input type="hidden" name="id_sprint_actual" value="<%=id_sprint_actual%>">
        </form>
    </body>
    <script>
        document.forms["redirect"].submit();
    </script>
</html>
