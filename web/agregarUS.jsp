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
        %>
        <div id="panelAgregarUS" class="panel panel-primary">
            <div class="panel-heading centrado">
                AGREGAR US | <%=id_proyecto%>
            </div>
            <div class="panel-body">
                <form id="formAgregarUS" action="MainServlet" method="POST" >

                    <!--PROYECTO-->
                    <div class="row">
                        <div class="col-md-2">
                            <span>Proyecto</span>                            
                        </div>
                        <div class="col-md-5">
                            <input id="id_proyecto" 
                                   name="id_proyecto" 
                                   value="<%=id_proyecto%>" 
                                   type="text"
                                   class="form-control input-sm" 
                                   placeholder="Proyecto"
                                   readonly>                            
                        </div>                                                                
                    </div>
                    <!--ID US-->
                    <div class="row">
                        <div class="col-md-2">
                            <span>ID-US</span>
                        </div>
                        <div class="col-md-5">
                            <input 
                                id="id_us" 
                                name="id_us" 
                                type="text" 
                                class="form-control input-sm" 
                                placeholder="ID US" 
                                onkeyup="this.value = this.value.toUpperCase()">
                        </div>
                    </div>
                                   
                    <!--RESPONSABLE-->
                    <div class="row">
                        <div class="col-md-2">
                            <span>Responsable</span>                            
                        </div>
                        <div class="col-md-5">
                            <input id="id_responsable" 
                                   name="id_responsable" 
                                   type="text" 
                                   class="form-control input-sm" 
                                   placeholder="Responsable">                            
                        </div>                                                                
                    </div>

                    <!--NOMBRE-->                   
                    <div class="row">
                        <div class="col-md-2">
                            <span>Nombre</span>
                        </div>
                        <div class="col-md-5">
                            <input id="nombre_us" 
                                   name="nombre_us" 
                                   type="text" 
                                   class="form-control input-sm" 
                                   placeholder="Nombre" 
                                   onkeyup="this.value = this.value.toUpperCase()">
                        </div>
                    </div>

                    <!--DESCRIPCION-->                    
                    <div class="row">
                        <div class="col-md-2">
                            <span>Descrpcion</span>
                        </div>
                        <div class="col-md-5">
                            <textarea id="descripcion_us" 
                                      name="descripcion_us" 
                                      class="form-control input-sm" 
                                      placeholder="Descripcion" 
                                      onkeyup="this.value = this.value.toUpperCase()"></textarea>
                        </div>
                    </div>

                    <!--TIEMPO ESTIMADO-->                   
                    <div class="row">
                        <div class="col-md-2">
                            <span>Tiempo Estimado</span>
                        </div>
                        <div class="col-md-5">
                            <input id="tiempo_estimado_us" 
                                   name="tiempo_estimado_us" 
                                   type="number" 
                                   class="form-control input-sm" 
                                   placeholder="Tiempo Estimado" 
                                   onkeyup="this.value = this.value.toUpperCase()"                                   
                                   >
                        </div>
                    </div>

                    <!--TIEMPO TRABAJADO-->                    
                    <div class="row">
                        <div class="col-md-2">
                            <span>Tiempo Trabajado</span>
                        </div>
                        <div class="col-md-5">
                            <input id="tiempo_trabajado_us" 
                                   name="tiempo_trabajado_us"
                                   value ="0"
                                   type="number" 
                                   class="form-control input-sm" 
                                   placeholder="Tiempo Trabajado" 
                                   onkeyup="this.value = this.value.toUpperCase()"
                                   readonly
                                   >
                        </div>
                    </div>

                    <!--PRIORIDAD-->             
                    <div class="row">
                        <div class="col-md-2">
                            <span>Prioridad</span>
                        </div>
                        <div class="col-md-5">
                            <select class="form-control" id="id_prioridad" name="id_prioridad">
                                <option value="1">HIGH</option>
                                <option value="2">MEDIUM</option>
                                <option value="3">LOW</option>
                            </select>

                        </div>
                    </div>

                    <!--BACKLOG-->                    
                    <div class="row">
                        <div class="col-md-2">
                            <span>Backlog</span>
                        </div>
                        <div class="col-md-5">
                            <select class="form-control" id="id_estado_backlog" name="id_estado_backlog">
                                <option value="4">PRODUCT</option>
                                <option value="5">SPRINT</option>
                            </select>    
                        </div>
                    </div>
                    
                    <!--ESTADO-->                    
                    <div class="row">
                        <div class="col-md-2">
                            <span>ESTADO</span>
                        </div>
                        <div class="col-md-5">
                            <select class="form-control" id="id_estado_backlog" name="id_estado">
                                <option value="1">TO DO</option>
                                <option value="2">DOING</option>
                                <option value="3">DONE</option>
                            </select>    
                        </div>
                    </div>
                    
                    <!--BOTON GUARFAR CAMBIOS-->
                    <div class="panel-footer centrado">
                        <input type="hidden" name="accion" value="grabar_us"/>
                        <input type="hidden" name="redireccion" value="productBacklog.jsp"/>
                        <button id="botonAgregarUS" type="submit" class="btn btn-primary btn-sm">Guardar Cambios</button>                
                    </div>
                </form>
            </div>

        </div>

        <!--    MENSAJES DEL SISTEMA-->
        <div id="mensajes" class="well well-sm centrado">Mensajes del Sistema.</div>

        <script src="js/jquery.min.js" type="text/javascript"></script>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
        <script src="js/funciones.js" type="text/javascript"></script>
        <script>

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
