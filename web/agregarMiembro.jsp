<%@page import="java.util.ArrayList"%>
<%@page import="modelos.Usuario"%>
<!DOCTYPE html>

<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=2.0">
        <link rel="stylesheet" href="css/bootstrap-theme.css" type="text/css"/>
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/estilos.css" type="text/css">
    </head>
    <body>
        <%
            String id_proyecto = request.getParameter("id_proyecto");
            ArrayList<Usuario> listaUsuarios = (ArrayList<Usuario>)request.getAttribute("listaUsuarios");
        %>
        <div id="panelAgregarUS" class="panel panel-primary">
            <div class="panel-heading centrado">
                AGREGAR MIEMBRO | <%=id_proyecto%>
            </div>
            <div class="panel-body">
                <form action="MainServlet" method="POST">                   
                    <!--LISTA DE USUARIOS-->                    
                    <div class="row">
                        <div class="col-md-2">
                            <span>USUARIO</span>
                        </div>
                        <div class="col-md-5">
                            <select class="form-control" name="id_miembro">
                                <%for (Usuario usuario : listaUsuarios) {%>
                                    <option value="<%=usuario.getId_usuario()%>"><%=usuario.getNombre()%></option>                                
                                <%}%>
                            </select>    
                        </div>
                    </div>
                    
                    <!--ROL-->                    
                    <div class="row">
                        <div class="col-md-2">
                            <span>ROL</span>
                        </div>
                        <div class="col-md-5">
                            <select class="form-control" name="id_rol">
                                <option value="2">PRODUCT OWNER</option>
                                <option value="3">SCRUM MASTER</option>
                                <option value="4">DEVELOPER</option>
                            </select>    
                        </div>
                    </div>
                    <div class="panel-footer centrado">
                        <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>"/>
                        <input type="hidden" name="accion" value="grabar_miembro"/>
                        <input type="hidden" name="redireccion" value="verMiembros.jsp"/>
                        <button id="botonAgregarUS" type="submit" class="btn btn-primary btn-sm">Agregar</button>                
                    </div>
                </form>
            </div>
        </div>

        <!--    MENSAJES DEL SISTEMA-->
        <div id="mensajes" class="well well-sm centrado">Mensajes del Sistema.</div>


        <!--    CONFIRMAR ELIMINAR-->
        <div class=" modal fade" id="confirmarEliminar" tabindex="-2" role="dialog" aria-labelledby="MyModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header centrado">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                        <h4 class="modal-title" id="myModalLabel">Mensaje del Sistema.</h4>
                    </div>
                    <div class="modal-body">
                        Está seguro de eliminar estos datos?
                    </div>
                    <div class="modal-footer centrado">
                        <button id="botonEliminarAlert" type="button" class="btn btn-primary btn-sm">Eliminar</button>
                        <button type="button" class="btn btn-default btn-sm" data-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>

        <script src="js/jquery.min.js" type="text/javascript"></script>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
        <script src="js/funciones.js" type="text/javascript"></script>
        <script>

            //boton agregar
            $("#botonAgregarUS").on('click', function () {
                var datosFormulario = $("formAgregarUS").serialize();
                console.log(datosFormulario);

            });

            //boton modificar
            $("#botonModificarUS").on('click', function () {
                if (validarFormulario()) {
                    modificarUsuario();
                }
            });

            //boton eliminar
            $("#botonEliminarAlertUS").on('click', function () {
                eliminarUsuario();
                $('#confirmarEliminar').modal('hide');

            });

            //boton salir
            $("#botonSalirAgregarUS").on('click', function () {
                $("#panelAgregarUS").fadeOut("slow");
                $("#panelBacklog").fadeIn("slow");
                ;
            });

        </script> 
    </body>
</html>
