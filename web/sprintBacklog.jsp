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
    <style>
        #panelSprint{
            width: 950px; 
            margin: auto;
            margin-top: 10px;           
        }
        ul li {
          display:inline-block;         
          vertical-align:top;
          width: 310px;
        }
        ul li table tbody tr td{
            margin-top: 5px;           
    
        }
        ul li form{
          display:inline-block;
        }
        
        .highPrio{
            background: #b92c28;
            color: white;
        }
        .mediumPrio{
            background: orange;
            color: white;
        }
        .lowPrio{
            background: #2aabd2;
            color: white;
        }
        
    </style>
    <body>
        <%
            ArrayList<UserStory> listaUs = (ArrayList<UserStory>) request.getAttribute("listaUs");
            String id_proyecto = request.getParameter("id_proyecto");
            String estado = "";
            String prioridad = "";
            String clase = "";
            String claseEstado = "";
            String prioridadClass = "";
        %>        
        <!--    PRODUCT BACKLOG-->
        <div id="panelSprintBacklog" class="panel panel-primary">
            <!--            PANEL HEADER-->
            <div class="panel-heading centrado">
                Sprint Backlog | <%=id_proyecto%>
            </div>

            <!--            PANEL BODY-->
            <div class="panel-body">
                <form id="formBuscar">
                    <input type="hidden" id="bpagina" name="bpagina" value="1"/>
                    <div class="row">
                        <div class="col-md-1">
                            <span>Fecha Inicio</span>                        
                        </div>
                        <div class="col-md-4">
                            <input id="bnombre_proyecto" name="bnombre_proyecto" type="text" class="form-control " placeholder="Nombre" readonly/>                        
                        </div>
                        <div class="col-md-1">
                            <span>Fecha Fin</span>                        
                        </div>
                        <div class="col-md-4">
                            <input id="bnombre_proyecto" name="bnombre_proyecto" type="text" class="form-control " placeholder="Nombre" readonly/>                        
                        </div>
                        <div class="col-md-2">
                            <button id="botonBuscar" type="button" class="btn btn-primary btn-sm" >
                                Finalizar Sprint                                
                            </button>                        
                        </div>
                    </div>
                </form>

                <!--        TABLA DE RESULTADOS-->
                <ul id="panelSprint">
                    <li>
                        <table class="table table-bordered table-striped table-hover">
                            <thead>
                                <tr>
                                    <th class="centrado">TO DO</th>
                                </tr>
                            </thead>
                            <tbody id="listaUSSprint">  
                                <%for (UserStory userStory : listaUs) {
                                       if (userStory.getId_prio() == 1) {
                                            prioridad = "HIGH";
                                            prioridadClass = "highPrio";
                                        } else if (userStory.getId_prio() == 2) {
                                            prioridad = "MEDIUM";
                                            prioridadClass = "mediumPrio";
                                        } else {
                                            prioridad = "LOW";
                                            prioridadClass = "lowPrio";
                                        }

                                        if (userStory.getId_estados() == 1) {
                                            estado = "TO DO";
                                            claseEstado = "danger";%>

                                <tr>
                                    <td class="danger">
                                          <b><%=userStory.getId_us()%> - <%=userStory.getNombre()%></b><br>
                                        <span class="<%=prioridadClass%>"><%=prioridad%></span><br>
                                        <%=userStory.getNombre_responsable()%><br>

                                        <form action="MainServlet" method="GET">
                                            <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                                            <input type="hidden" name="id_us" value="<%=userStory.getId_us()%>"/>
                                            <input type="hidden" name="accion" value="editar_us"/>
                                            <input type="hidden" name="accion_redireccion" value="ver_sprint_backlog"/>                                            
                                            <input type="hidden" name="redireccion" value="sprintBacklog.jsp"/>
                                            <button type="submit" class="btn btn-warning btn-sm">
                                                <span class='glyphicon glyphicon-pencil'></span>
                                            </button>       
                                        </form>


                                        <form action="MainServlet" method="GET">
                                            <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                                            <input type="hidden" name="id_us" value="<%=userStory.getId_us()%>"/>
                                            <input type="hidden" name="accion" value="eliminar_us"/>
                                            <input type="hidden" name="accion_redireccion" value="ver_sprint_backlog"/>                                              
                                            <input type="hidden" name="redireccion" value="sprintBacklog.jsp"/>
                                            <button type="submit" class="btn btn-danger btn-sm">
                                                <span class='glyphicon glyphicon-trash'></span>
                                            </button>       
                                        </form>
                                    </td>
                                </tr>

                                <%}%>                            
                                <%}%>                            
                            </tbody>
                        </table>
                    </li>
                    <li>
                        <table class="table table-bordered table-striped table-hover">
                            <thead>
                                <tr>
                                    <th class="centrado">IN PROGRESS</th>
                                </tr>
                            </thead>
                            <tbody id="listaUSSprint">  
                                <%for (UserStory userStory : listaUs) {
                                    if (userStory.getId_prio() == 1) {
                                            prioridad = "HIGH";
                                            prioridadClass = "highPrio";
                                        } else if (userStory.getId_prio() == 2) {
                                            prioridad = "MEDIUM";
                                            prioridadClass = "mediumPrio";
                                        } else {
                                            prioridad = "LOW";
                                            prioridadClass = "lowPrio";
                                        }

                                        if (userStory.getId_estados() == 2) {
                                            estado = "IN PROGRESS";
                                            claseEstado = "danger";%>

                                <tr>                                
                                    <td class="warning">
                                        <b><%=userStory.getId_us()%> - <%=userStory.getNombre()%></b><br>
                                        <span class="<%=prioridadClass%>"><%=prioridad%></span><br>
                                        <%=userStory.getNombre_responsable()%><br>

                                        <form action="MainServlet" method="GET">
                                            <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                                            <input type="hidden" name="id_us" value="<%=userStory.getId_us()%>"/>
                                            <input type="hidden" name="accion" value="editar_us"/>
                                            <input type="hidden" name="accion_redireccion" value="ver_sprint_backlog"/>                                              
                                            <input type="hidden" name="redireccion" value="sprintBacklog.jsp"/>
                                            <button type="submit" class="btn btn-warning btn-sm">
                                                <span class='glyphicon glyphicon-pencil'></span>
                                            </button>       
                                        </form>


                                        <form action="MainServlet" method="GET">
                                            <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                                            <input type="hidden" name="id_us" value="<%=userStory.getId_us()%>"/>
                                            <input type="hidden" name="accion" value="eliminar_us"/>
                                            <input type="hidden" name="accion_redireccion" value="ver_sprint_backlog"/>                                              
                                            <input type="hidden" name="redireccion" value="sprintBacklog.jsp"/>
                                            <button type="submit" class="btn btn-danger btn-sm">
                                                <span class='glyphicon glyphicon-trash'></span>
                                            </button>       
                                        </form>
                                    </td>
                                </tr>

                                <%}%>                            
                                <%}%>                            
                            </tbody>
                        </table>
                    </li>
                    <li>
                        <table class="table table-bordered table-striped table-hover">
                            <thead>
                                <tr>
                                    <th class="centrado">DONE</th>
                                </tr>
                            </thead>
                            <tbody id="listaUSSprint">  
                                <%for (UserStory userStory : listaUs) {
                                        
                                    if (userStory.getId_prio() == 1) {
                                            prioridad = "HIGH";
                                            prioridadClass = "highPrio";
                                        } else if (userStory.getId_prio() == 2) {
                                            prioridad = "MEDIUM";
                                            prioridadClass = "mediumPrio";
                                        } else {
                                            prioridad = "LOW";
                                            prioridadClass = "lowPrio";
                                        }

                                        if (userStory.getId_estados() == 3) {
                                            estado = "DONE";
                                            claseEstado = "danger";
                                %>

                                <tr>                                
                                    <td class="success">
                                        <b><%=userStory.getId_us()%> - <%=userStory.getNombre()%></b><br>
                                        <span class="<%=prioridadClass%>"><%=prioridad%></span><br>
                                        <%=userStory.getNombre_responsable()%><br>

                                        <form action="MainServlet" method="GET">
                                            <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                                            <input type="hidden" name="id_us" value="<%=userStory.getId_us()%>"/>
                                            <input type="hidden" name="accion" value="editar_us"/>
                                            <input type="hidden" name="accion_redireccion" value="ver_sprint_backlog"/>                                              
                                            <input type="hidden" name="redireccion" value="sprintBacklog.jsp"/>
                                            <button type="submit" class="btn btn-warning btn-sm">
                                                <span class='glyphicon glyphicon-pencil'></span>
                                            </button>       
                                        </form>


                                            <form action="MainServlet" method="GET">
                                            <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                                            <input type="hidden" name="id_us" value="<%=userStory.getId_us()%>"/>
                                            <input type="hidden" name="accion" value="eliminar_us"/>
                                            <input type="hidden" name="accion_redireccion" value="ver_sprint_backlog"/>                                              
                                            <input type="hidden" name="redireccion" value="sprintBacklog.jsp"/>
                                            <button type="submit" class="btn btn-danger btn-sm">
                                                <span class='glyphicon glyphicon-trash'></span>
                                            </button>       
                                        </form>
                                    </td>
                                </tr>

                                <%}%>                            
                                <%}%>                            
                            </tbody>
                        </table>
                    </li>
                    <!--                    <nav>
                                            <ul class="pager">
                                                <li id="anterior"><a href="#"><span aria-hidden="true">&larr;</span></a>Anterior</li>
                                                <li id="siguiente"><a href="#">Siguiente<span aria-hidden="true">&rarr;</span></a></li>                        
                                            </ul>
                                        </nav>-->
                </ul>
            </div> 

            <!--            PANEL FOOTER-->
            <div class="panel-footer centrado">
                <button id="botonSalirSprintBacklog" type="button" class="btn btn-primary btn-sm">Salir</button>
            </div>
        </div> 

        <!--    MENSJADES DEL SISTEMA-->
        <div id="mensajesBacklog" class="well-sm centrado">Mensajes del Sistema</div>


        <script src = "js/jquery.min.js" type="text/javascript"></script>
        <script src = "js/bootstrap.min.js" type="text/javascript"></script>
        <script src = "js/funciones.js" type="text/javascript"></script>

        <script>
            verificarSesion();
            // buscarUSSprint();

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

            $("#botonSalirSprintBacklog").on("click", function () {
                $("#panelSprintBacklog").fadeOut("slow");
                $("#panelMisProyectos").fadeIn("slow");
            });

        </script>

    </body>
</html>
