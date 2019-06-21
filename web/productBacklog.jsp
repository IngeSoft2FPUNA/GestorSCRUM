<%@page import="java.util.ArrayList"%>
<%@page import="modelos.UserStory"%>
<!DOCTYPE html>

<html>
    <head>
        <title>GESTOR SCRUM</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/bootstrap-theme.css" type="text/css"/>
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/estilos.css" type="text/css">
    </head>
    <body>
        <%
            ArrayList<UserStory> listaUs = (ArrayList<UserStory>) request.getAttribute("listaUs");
            String id_proyecto = request.getParameter("id_proyecto");
            String estado_backlog = "";
            String prioridad = "";
            String clase = "";
        %>
        <!--    PRODUCT BACKLOG-->
        <div id="panelBacklog" class="panel panel-primary">
            <!--            PANEL HEADER-->
            <div class="panel-heading centrado">
                Product Backlog | <%=id_proyecto%>
            </div>

            <!--            PANEL BODY-->
            <div class="panel-body">
                <form id="formBuscar">
                    <input type="hidden" id="bpagina" name="bpagina" value="1"/>
                    <div class="row">
                        <div class="col-md-1">
                            <span>Nombre</span>                        
                        </div>
                        <div class="col-md-8">
                            <input id="bnombre_proyecto" name="bnombre_proyecto" type="text" class="form-control " placeholder="Nombre"/>                        
                        </div>
                        <div class="col-md-1">
                            <button id="botonBuscar" type="button" class="btn btn-primary btn-sm" >
                                <span class='glyphicon glyphicon-search'></span>
                            </button>                        
                        </div>
                        <div class="col-md-1">
                            <form action="MainServlet" method="GET">
                                <input type="hidden" name="accion" value="agregar_us"/>
                                <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                                <button id="botonAgregarUS" type="submit" class="btn btn-success btn-sm">
                                    <span class='glyphicon glyphicon-plus'></span> Agregar US
                                </button>                        
                            </form>               
                        </div>
                    </div>
                </form>


                <!--        TABLA DE RESULTADOS-->
                <div id="resultado">
                    <table class="table table-bordered table-striped table-hover">
                        <thead>
                            <tr>
                                <th>US</th>
                                <th>Nombre</th>
                                <th>Descripcion</th>
                                <th>Tiempo Estimado</th>
                                <th>Tiempo Trabajado</th>
                                <th>Estado Backlog</th>
                                <th>Prioridad</th>
                                <th colspan="2">Accion</th>
                            </tr>
                        </thead>
                        <tbody id="listaUSBacklog">
                            <%for (UserStory userStory : listaUs) {
                                    estado_backlog = userStory.getId_estados_backlog() == 4 ? "SPRINT" : "PRODUCT";

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
                            %>

                            <tr>                                
                                <td><%=userStory.getId_us()%></td>
                                <td><%=userStory.getNombre()%></td>
                                <td><%=userStory.getDescripcion()%></td>
                                <td><%=userStory.getTiempo_estimado()%></td>
                                <td><%=userStory.getTiempo_trabajado()%></td>
                                <td><%=estado_backlog%></td>
                                <td class="<%=clase%>"><%=prioridad%></td>
                                <td>
                                    <form action="MainServlet" method="GET">
                                        <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                                        <input type="hidden" name="id_us" value="<%=userStory.getId_us()%>"/>
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
                    <nav>
                        <ul class="pager">
                            <li id="anterior"><a href="#"><span aria-hidden="true">&larr;</span></a>Anterior</li>
                            <li id="siguiente"><a href="#">Siguiente<span aria-hidden="true">&rarr;</span></a></li>                        
                        </ul>
                    </nav>
                </div>
            </div> 

            <!--            PANEL FOOTER-->
            <div class="panel-footer centrado">
                <button id="botonSalirBacklog" type="button" class="btn btn-primary btn-sm">Salir</button>
            </div>
        </div> 

        <!--    MENSJADES DEL SISTEMA-->
        <div id="mensajes" class="well well-sm centrado">Mensajes del Sistema</div>


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
