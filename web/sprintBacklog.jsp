<%@page import="modelos.Sprint"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelos.UserStory"%>
<!DOCTYPE html>
<%
    ArrayList<UserStory> listaTODO = (ArrayList<UserStory>) request.getAttribute("listaTODO");
    ArrayList<UserStory> listaINPROGRESS = (ArrayList<UserStory>) request.getAttribute("listaINPROGRESS");
    ArrayList<UserStory> listaDONE = (ArrayList<UserStory>) request.getAttribute("listaDONE");
    String id_proyecto = request.getParameter("id_proyecto");
    Sprint sprintActual = (Sprint) request.getAttribute("sprintActual");
    System.out.println("id_sprint_actual tablero kanban :" + sprintActual.getId_sprint());
    String prioridad = "";
    String prioridadClass = "";
%> 
<html>
    <head>
        <title>Kanban Board | <%=id_proyecto%></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/bootstrap-theme.css" type="text/css"/>
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/estilos.css" type="text/css">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"> 
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
            float: right;
            margin-bottom: 0;
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

        .ticket{
            height: 150px;
            padding: 10px;
            margin-top: 5%;                
            box-shadow: 0 4px 8px 4px rgba(0,0,0,0.2);
            transition: 0.3s;
        }

        .ticket:hover{
            -o-transform:rotate(5deg);
            -webkit-transform:rotate(5deg);
            -moz-transform:rotate(5deg);   
            -webkit-transform: scale(1.05);
            -moz-transform: scale(1.05);
            -o-transform: scale(1.05);
        }


        .ticketTODO{      
            background: #ffa8b8;
        }
        .ticketINPROGRESS{
            background: #ffff66;
        }
        .ticketDONE{
            background: #a0d995;
        }

    </style>
    <body>       
        <!--    PRODUCT BACKLOG-->
        <div id="panelSprintBacklog" class="panel panel-primary">
            <!--            PANEL HEADER-->
            <div class="panel-heading centrado">
                Tablero Kanban | <%=id_proyecto%>
            </div>

            <!--            PANEL BODY-->
            <div class="panel-body">
                <form action="MainServlet" method="GET">
                    <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                    <input type="hidden" name="accion" value="ver_burndown_chart"/>
                    <button type="submit" class="btn btn-warning btn-sm" formtarget="_blank">
                        Burndown Chart
                    </button>       
                </form>
                
                    <div class="row">
                        <div class="col-md-6 centrado">
                    <p><b>Fecha Inicio:</b>  <%=sprintActual.getFecha_inicio()%></p>
                            
                        </div>
                        <div class="col-md-6 centrado">
                    <p><b>Fecha Fin Estimada:</b> <%=sprintActual.getFecha_fin_estimada()%></p>
                            
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 centrado">
                    <p><b>Tiempo Restante:</b> <%=sprintActual.getDuracion_estimada() - sprintActual.getDuracion_real()%> días</p>
                            
                        </div>
                        <div class="col-md-6 centrado">
                    <p><b>Story Points Estimados:</b> <%=sprintActual.getStory_points_estimados()%></p>
                            
                        </div>
                    </div>
                    
                    <p><b>Story Points Realizados:</b> <%=sprintActual.getStory_points_realizados()%></p>

                <!--TABLA DE RESULTADOS-->

                <!--TO DO-->
                <ul id="panelSprint">
                    <li>
                        <table class="table">
                            <thead>
                                <tr>
                                    <th class="centrado">TO DO (<%=listaTODO.size()%>)</th>
                                </tr>
                            </thead>
                            <tbody id="listaUSSprint">  
                                <%for (UserStory userStory : listaTODO) {
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
                                %>

                                <tr>
                                    <td>
                                        <div class="w3-card ticket ticketTODO">
                                            <b><%=userStory.getId_us()%> - <%=userStory.getNombre()%></b><br>
                                            <%=userStory.getStory_points()%> | <span class="<%=prioridadClass%>"><%=prioridad%></span><br>
                                            <%=userStory.getNombre_responsable()%><br>

                                            <form action="MainServlet" method="GET">
                                                <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                                                <input type="hidden" name="id_us" value="<%=userStory.getId_us()%>"/>
                                                <input type="hidden" name="id_sprint_actual" value="<%=sprintActual.getId_sprint()%>"/>
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
                                                <input type="hidden" name="id_sprint_actual" value="<%=sprintActual.getId_sprint()%>"/>
                                                <input type="hidden" name="accion" value="eliminar_us"/>
                                                <input type="hidden" name="accion_redireccion" value="ver_sprint_backlog"/>                                              
                                                <input type="hidden" name="redireccion" value="sprintBacklog.jsp"/>
                                                <button type="submit" class="btn btn-danger btn-sm">
                                                    <span class='glyphicon glyphicon-trash'></span>
                                                </button>       
                                            </form>
                                        </div>
                                    </td>
                                </tr>

                                <%}%>                            
                            </tbody>
                        </table>
                    </li>

                    <!--IN PROGRESS-->
                    <li>
                        <table class="table">
                            <thead>
                                <tr>
                                    <th class="centrado">IN PROGRESS (<%=listaINPROGRESS.size()%>)</th>
                                </tr>
                            </thead>
                            <tbody id="listaUSSprint">  
                                <%for (UserStory userStory : listaINPROGRESS) {
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
                                %>

                                <tr>                                
                                    <td>
                                        <div class="w3-card ticket ticketINPROGRESS">
                                            <b><%=userStory.getId_us()%> - <%=userStory.getNombre()%></b><br>
                                            <%=userStory.getStory_points()%> | <span class="<%=prioridadClass%>"><%=prioridad%></span><br>
                                            <%=userStory.getNombre_responsable()%><br>

                                            <form action="MainServlet" method="GET">
                                                <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                                                <input type="hidden" name="id_us" value="<%=userStory.getId_us()%>"/>
                                                <input type="hidden" name="id_sprint_actual" value="<%=sprintActual.getId_sprint()%>"/>
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
                                                <input type="hidden" name="id_sprint_actual" value="<%=sprintActual.getId_sprint()%>"/>
                                                <input type="hidden" name="accion" value="eliminar_us"/>
                                                <input type="hidden" name="accion_redireccion" value="ver_sprint_backlog"/>                                              
                                                <input type="hidden" name="redireccion" value="sprintBacklog.jsp"/>
                                                <button type="submit" class="btn btn-danger btn-sm">
                                                    <span class='glyphicon glyphicon-trash'></span>
                                                </button>       
                                            </form>
                                        </div>
                                    </td>
                                </tr>

                                <%}%>                            
                            </tbody>
                        </table>
                    </li>

                    <!--DONE-->
                    <li>
                        <table class="table">
                            <thead>
                                <tr>
                                    <th class="centrado">DONE (<%=listaDONE.size()%>)</th>
                                </tr>
                            </thead>
                            <tbody id="listaUSSprint">  
                                <%for (UserStory userStory : listaDONE) {

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
                                %>

                                <tr>                                
                                    <td>
                                        <div class="w3-card ticket ticketDONE">
                                            <b><%=userStory.getId_us()%> - <%=userStory.getNombre()%></b><br>
                                            <%=userStory.getStory_points()%> | <span class="<%=prioridadClass%>"><%=prioridad%></span><br>
                                            <%=userStory.getNombre_responsable()%><br>

                                            <form action="MainServlet" method="GET">
                                                <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                                                <input type="hidden" name="id_us" value="<%=userStory.getId_us()%>"/>
                                                <input type="hidden" name="id_sprint_actual" value="<%=sprintActual.getId_sprint()%>"/>
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
                                                <input type="hidden" name="id_sprint_actual" value="<%=sprintActual.getId_sprint()%>"/>
                                                <input type="hidden" name="accion" value="eliminar_us"/>
                                                <input type="hidden" name="accion_redireccion" value="ver_sprint_backlog"/>                                              
                                                <input type="hidden" name="redireccion" value="sprintBacklog.jsp"/>
                                                <button type="submit" class="btn btn-danger btn-sm">
                                                    <span class='glyphicon glyphicon-trash'></span>
                                                </button>       
                                            </form>
                                        </div>
                                    </td>
                                </tr>        
                                <%}%>                            
                            </tbody>
                        </table>
                    </li>
                </ul>
            </div> 

            <!--            PANEL FOOTER-->
            <form  action="MainServlet" method="GET">
                <div class="panel-footer centrado">
                    <input type="hidden" name="accion" value="home">
                    <button type="submit" class="btn btn-primary btn-sm">Salir</button>
                </div>
            </form>
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
