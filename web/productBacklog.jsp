<%@page import="modelos.Sprint"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelos.UserStory"%>
<!DOCTYPE html>
<%
    ArrayList<UserStory> listaPRODUCT = (ArrayList<UserStory>) request.getAttribute("listaPRODUCT");
    ArrayList<UserStory> listaSPRINT = (ArrayList<UserStory>) request.getAttribute("listaSPRINT");
    Sprint sprintActual = (Sprint) request.getAttribute("sprintActual");
    String id_proyecto = request.getParameter("id_proyecto");
    String prioridad = "";
    String estado = "";
    String clase = "";
    String claseEstado = "";
    String accionSprint = "";
    String accionSprintLabel = "";
    String accionSprintClass = "";
    String id_sprint = "";

    if (sprintActual.getEstado_sprint() == 7) {
        accionSprint = "finalizar_sprint";
        accionSprintClass = "danger";
        accionSprintLabel = "Finalizar Sprint";
        id_sprint = "#" + sprintActual.getId_sprint();
    } else {
        accionSprint = "iniciar_sprint";
        accionSprintClass = "success";
        accionSprintLabel = "Iniciar Sprint";
        id_sprint = "";

    }

%>
<html>
    <head>
        <title>Backlog | <%=id_proyecto%></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/bootstrap-theme.css" type="text/css"/>
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/estilos.css" type="text/css">
    </head>
    <body>

        <!--    PRODUCT BACKLOG-->
        <div id="panelBacklog" class="panel panel-primary">
            <!--            PANEL HEADER-->
            <div class="panel-heading centrado">
                Backlog | <%=id_proyecto%>
            </div>

            <!--            PANEL BODY-->
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-10">
                        <h1>Product</h1>                        </div>
                    <div class="col-md-2">
                        <form action="MainServlet" method="GET">
                            <input type="hidden" name="accion" value="agregar_us"/>
                            <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                            <input type="hidden" name="id_sprint_actual" value="<%=sprintActual.getId_sprint()%>"/>
                            <button id="botonAgregarUS" type="submit" class="btn btn-success btn-sm">
                                <span class='glyphicon glyphicon-pushpin'></span> Agregar US
                            </button>                        
                        </form>               
                    </div>
                </div>


                <!--        TABLA DE RESULTADOS-->
                <div>

                    <table class="table table-bordered table-striped table-hover">
                        <thead>
                            <tr>
                                <th>US</th>
                                <th>Nombre</th>
                                <th>Descripcion</th>
                                <th>Tiempo Estimado</th>
                                <th>Tiempo Trabajado</th>
                                <th>Prioridad</th>
                                <th colspan="2">Accion</th>
                            </tr>
                        </thead>
                        <tbody id="listaUSBacklog">
                            <%for (UserStory userStory : listaPRODUCT) {
                                    if (userStory.getId_prio() == 1) {
                                        prioridad = "HIGH";
                                        clase = "danger";
                                    } else if (userStory.getId_prio() == 2) {
                                        prioridad = "MEDIUM";
                                        clase = "warning";
                                    } else {
                                        prioridad = "LOW";
                                        clase = "info";

                                    }
                            %>

                            <tr>                                
                                <td><%=userStory.getId_us()%></td>
                                <td><%=userStory.getNombre()%></td>
                                <td><%=userStory.getDescripcion()%></td>
                                <td><%=userStory.getTiempo_estimado()%></td>
                                <td><%=userStory.getTiempo_trabajado()%></td>
                                <td class="<%=clase%>"><%=prioridad%></td>
                                <td>
                                    <form action="MainServlet" method="GET">
                                        <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                                        <input type="hidden" name="id_us" value="<%=userStory.getId_us()%>"/>
                                        <input type="hidden" name="id_sprint_actual" value="<%=sprintActual.getId_sprint()%>"/>
                                        <input type="hidden" name="accion" value="editar_us"/>
                                        <input type="hidden" name="accion_redireccion" value="ver_product_backlog"/>
                                        <input type="hidden" name="redireccion" value="productBacklog.jsp"/>
                                        <button type="submit" class="btn btn-warning btn-sm">
                                            <span class='glyphicon glyphicon-pencil'></span>
                                        </button>       
                                    </form>
                                </td>
                                <td>
                                    <form action="MainServlet" method="GET">
                                        <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                                        <input type="hidden" name="id_us" value="<%=userStory.getId_us()%>"/>
                                        <input type="hidden" name="id_sprint_actual" value="<%=sprintActual.getId_sprint()%>"/>
                                        <input type="hidden" name="accion" value="eliminar_us"/>
                                        <input type="hidden" name="accion_redireccion" value="ver_product_backlog"/>                                              
                                        <input type="hidden" name="redireccion" value="productBacklog.jsp"/>
                                        <button type="submit" class="btn btn-danger btn-sm">
                                            <span class='glyphicon glyphicon-trash'></span>
                                        </button>       
                                    </form>
                                </td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>

                    <div class="row">
                        <div class="col-md-10">
                            <h1>Sprint <%=id_sprint%> </h1>
                        </div>
                        <div class="col-md-2">
                            <form action="MainServlet" method="POST">
                                <%if (sprintActual.getEstado_sprint() != 7) {%>
                                <select class="form-control" name="duracion_estimada">
                                    <option value="8">1 Semana</option>
                                    <option value="15">2 Semanas</option>
                                    <option value="21">3 Semanas</option>
                                    <option value="30">4 Semanas</option>
                                </select>
                                <%}%>
                                <input type="hidden" name="accion" value="<%=accionSprint%>"/>
                                <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                                <button type="submit" class="btn btn-<%=accionSprintClass%> btn-sm">
                                    <%=accionSprintLabel%>
                                </button>                        
                            </form>               
                        </div>
                    </div>
                    <div class="row">
                    </div>                                
                    <table class="table table-bordered table-striped table-hover">
                        <thead>
                            <tr>
                                <th>US</th>
                                <th>Nombre</th>
                                <th>Descripcion</th>
                                <th>Tiempo Estimado</th>
                                <th>Tiempo Trabajado</th>
                                <th>Story Points</th>
                                <th>Prioridad</th>
                                <th>Estado</th>
                                <th colspan="2">Accion</th>
                            </tr>
                        </thead>
                        <tbody id="listaUSBacklog">
                            <%for (UserStory userStory : listaSPRINT) {
                                    if (userStory.getId_prio() == 1) {
                                        prioridad = "HIGH PRIO";
                                        clase = "danger";
                                    } else if (userStory.getId_prio() == 2) {
                                        prioridad = "MEDIUM PRIO";
                                        clase = "warning";
                                    } else {
                                        prioridad = "LOW PRIO";
                                        clase = "info";
                                    }

                                    if (userStory.getId_estados() == 1) {
                                        estado = "TO DO";
                                        claseEstado = "danger";
                                    } else if (userStory.getId_estados() == 2) {
                                        estado = "IN PROGRESS";
                                        claseEstado = "warning";
                                    } else {
                                        estado = "DONE";
                                        claseEstado = "success";
                                    }
                            %>

                            <tr>                                
                                <td><%=userStory.getId_us()%></td>
                                <td><%=userStory.getNombre()%></td>
                                <td><%=userStory.getDescripcion()%></td>
                                <td><%=userStory.getTiempo_estimado()%></td>
                                <td><%=userStory.getTiempo_trabajado()%></td>
                                <td><%=userStory.getStory_points()%></td>
                                <td class="<%=clase%>"><%=prioridad%></td>
                                <td class="<%=claseEstado%>"><%=estado%></td>
                                <td>
                                    <form action="MainServlet" method="GET">
                                        <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                                        <input type="hidden" name="id_us" value="<%=userStory.getId_us()%>"/>
                                        <input type="hidden" name="id_sprint_actual" value="<%=sprintActual.getId_sprint()%>"/>
                                        <input type="hidden" name="accion" value="editar_us"/>
                                        <input type="hidden" name="accion_redireccion" value="ver_product_backlog"/>
                                        <input type="hidden" name="redireccion" value="productBacklog.jsp"/>
                                        <button type="submit" class="btn btn-warning btn-sm">
                                            <span class='glyphicon glyphicon-pencil'></span>
                                        </button>       
                                    </form>
                                </td>
                                <td>
                                    <form action="MainServlet" method="GET">
                                        <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                                        <input type="hidden" name="id_us" value="<%=userStory.getId_us()%>"/>
                                        <input type="hidden" name="accion" value="eliminar_us"/>
                                        <input type="hidden" name="id_sprint_actual" value="<%=sprintActual.getId_sprint()%>"/>
                                        <input type="hidden" name="accion_redireccion" value="ver_product_backlog"/>                                              
                                        <input type="hidden" name="redireccion" value="productBacklog.jsp"/>
                                        <button type="submit" class="btn btn-danger btn-sm">
                                            <span class='glyphicon glyphicon-trash'></span>
                                        </button>       
                                    </form>
                                </td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>                    
                </div>
            </div> 

            <!--            PANEL FOOTER-->
            <form  action="MainServlet" method="GET">
                <div class="panel-footer centrado">
                    <input type="hidden" name="accion" value="home">
                    <button type="submit" class="btn btn-primary btn-sm">Salir</button>
                </div>
            </form>
        </div> 

        <script src = "js/jquery.min.js" type="text/javascript"></script>
        <script src = "js/bootstrap.min.js" type="text/javascript"></script>
        <script src = "js/funciones.js" type="text/javascript"></script>

        <script>
            verificarSesion();
            //buscarUSBacklog();

            $("#bnombre_proyecto").focus();
            //siguienteCampo("#bnombre_proyecto","#botonBuscar",false);

            $("#botonBuscar").on("click", function () {
                $("#contenidoBusqueda").html("");
                $("#bpagina").val("1");
                buscarMisProyecto();
                $("#bnombre_proyecto").focus();
            });

            $("#anterior").on("click", function () {
                $("#contenidoBusqueda").html("");
                var pag = parseInt($("#bpagina").val());
                if (pag > 1) {
                    $("#bpagina").val(pag - 1);
                }
                buscarMisProyecto();
            });

            $("#siguiente").on("click", function () {
                $("#contenidoBusqueda").html("");
                var pag = parseInt($("#bpagina").val());
                $("#bpagina").val(pag + 1);
                buscarMisProyecto();
            });

            $("#botonSalirBacklog").on("click", function () {
                $("#panelBacklog").fadeOut("slow");
                $("#panelMisProyectos").fadeIn("slow");
            });

        </script>

    </body>
</html>
