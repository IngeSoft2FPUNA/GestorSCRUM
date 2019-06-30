<%@page import="java.util.ArrayList"%>
<%@page import="modelos.UserStory"%>
<!DOCTYPE html>
<%
    ArrayList<UserStory> listaMiActividad = (ArrayList<UserStory>) request.getAttribute("listaMiActividad");
    int id_rol_sistema = (Integer) request.getAttribute("id_rol_sistema");
    String prioridad = "";
    String clasePrioridad = "";
    String estado = "";
    String claseEstado = "";
%>
<html>
    <head>
        <title>Inicio</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/bootstrap-theme.css" type="text/css"/>
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/estilos.css" type="text/css">
    </head>
    <body>
        <!--    BARRA NAVEGACION-->
        <nav class="navbar navbar-default">
            <div class ="container-fluid">              
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapse" 
                            data-toggle="collapse"
                            data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle Navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>                
                    <a class="navbar-brand" href="#">DASHBOARD</a>                     
                </div>
                <div class="collpase navbar-collapse" id="bs-example-navbar-collapse-1">
                    <%if (id_rol_sistema == 1) {%>
                    <ul class="nav navbar-nav">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle"
                               data-toggle="dropdown" role="button"
                               >Nuevo<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="frm/archivos/usuarios">Usuario</a></li>
                                <li><a href="frm/archivos/proyectos">Proyecto</a></li>
                            </ul>
                        </li>
                    </ul>
                    <%}%>
                    <ul class="nav navbar-nav navbar-right">                        
                        <li class="dropdown">
                            <a href="#" 
                               class="dropdown-toggle" 
                               data-toggle="dropdown"
                               role="button">
                                <span id="susuario_usuario"></span>
                                <span id="id_usuario"></span>
                                <span class="caret">                                       
                                </span>
                            </a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="#"> <span class='glyphicon glyphicon-user'></span>  Mi Perfil</a></li>
                                <li><a href="#"><span class='glyphicon glyphicon-question-sign'></span>  Acerca de</a></li>
                                <li><a href="index.html"><span class='glyphicon glyphicon-log-out'></span>  Cerrar Sesion</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <!--    PANEL MI ACTIVIDAD-->
        <div id="panelMisProyectos" class="panel panel-primary">
            <div class="panel-heading centrado">Mi Actividad</div>
            <div class="panel-body">

                <!--TABLA DE RESULTADOS-->
                <div id="resultado">
                    <table class="table table-bordered table-striped table-hover">
                        <thead>
                            <tr>
                                <th>Proyecto</th>
                                <th>US</th>
                                <th>Prioridad</th>
                                <th>Tiempo Trabajado</th>
                                <th>Estado</th>
                                <th class="centrado" colspan="2">Accion</th>
                            </tr>
                        </thead>
                        <tbody id="listaUSBacklog">
                            <%for (UserStory userStory : listaMiActividad) {
                                    //se evalua el estado de la prioridad
                                    if (userStory.getId_prio() == 1) {
                                        prioridad = "HIGH";
                                        clasePrioridad = "danger";
                                    } else if (userStory.getId_prio() == 2) {
                                        prioridad = "MEDIUM";
                                        clasePrioridad = "warning";
                                    } else {
                                        prioridad = "LOW";
                                        clasePrioridad = "info";

                                    }

                                    //se evalua el estado del usestory
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
                                <td><%=userStory.getId_proyecto()%></td>
                                <td><%=userStory.getId_us()%></td>
                                <td class="<%=clasePrioridad%>"><%=prioridad%></td>
                                <td><%=userStory.getTiempo_trabajado()%></td>
                                <td class="<%=claseEstado%>"><%=estado%></td>
                                <td>
                                    <form action="MainServlet" method="GET">
                                        <input type="hidden" name="id_proyecto" value="<%=userStory.getId_proyecto()%>"/>
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
                                        <input type="hidden" name="id_proyecto" value="<%=userStory.getId_proyecto()%>"/>
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
                </div>
            </div>       
        </div>  

        <br><br>
        <!--    PANEL MIS PROYECTOS-->
        <div id="panelMisProyectos" class="panel panel-primary">
            <div class="panel-heading centrado">Mis Proyectos</div>
            <div class="panel-body">
                <form id="formBuscar">
                    <input type="hidden" id="bpagina" name="bpagina" value="1"/>
                    <div class="row">
                        <div class="col-md-1">
                            <span>Nombre</span>                        
                        </div>
                        <div class="col-md-9">
                            <input id="bnombre_proyecto" name="bnombre_proyecto" type="text" class="form-control input-sm" placeholder="Nombre"/>                        
                        </div>
                        <div class="col-md-2">
                            <button id="botonBuscar" type="button" class="btn btn-primary btn-sm" >
                                <span class='glyphicon glyphicon-search'></span>
                            </button>                        
                        </div>
                    </div>
                </form>

                <!--        TABLA DE RESULTADOS-->
                <div id="resultado">
                    <table class="table table-bordered table-striped table-hover">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Nombre</th>
                                <th>Descripcion</th>
                                <th>Fecha de Entrega</th>
                                <th>Rol</th>
                                <th class="centrado" colspan="4">Accion</th>
                            </tr>
                        </thead>
                        <tbody id="contenidoBusqueda">                            
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
        </div>    

        <!--    MENSJADES DEL SISTEMA-->
        <div id="mensajes" class="well well-sm centrado">Mensajes del Sistema</div>


        <script src = "js/jquery.min.js" type="text/javascript"></script>
        <script src = "js/bootstrap.min.js" type="text/javascript"></script>
        <script src = "js/funciones.js" type="text/javascript"></script>

        <script>
            verificarSesion();
            buscarMisProyecto();

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

        </script>

    </body>
</html>
