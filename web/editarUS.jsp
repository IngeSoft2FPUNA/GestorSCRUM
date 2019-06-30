<%@page import="modelos.Miembro"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelos.UserStory"%>
<!DOCTYPE html>
<%
    UserStory userStory = (UserStory) request.getAttribute("userStory");
    ArrayList<Miembro> listaMiembros = (ArrayList<Miembro>) request.getAttribute("listaMiembros");
    String redireccion = request.getParameter("redireccion");
    String id_sprint_actual = request.getParameter("id_sprint_actual");
    String accion_redireccion = request.getParameter("accion_redireccion");
    System.out.println("id_sprint_actual Editar US :"+ id_sprint_actual);
%>
<html>
    <head>
        <title>Editar US | <%=userStory.getId_us()%></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=2.0">
        <link rel="stylesheet" href="css/bootstrap-theme.css" type="text/css"/>
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/estilos.css" type="text/css">
    </head>
    <body>
        <div id="panelAgregarUS" class="panel panel-primary">
            <div class="panel-heading centrado">
                EDITAR US | <%=userStory.getId_proyecto()%>
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
                                   value="<%=userStory.getId_proyecto()%>" 
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
                                value="<%=userStory.getId_us()%>"
                                type="text" 
                                class="form-control input-sm" 
                                placeholder="ID US" 
                                onkeyup="this.value = this.value.toUpperCase()"
                                readonly>
                        </div>
                    </div>

                    <!--RESPONSABLE-->
                    <div class="row">
                        <div class="col-md-2">
                            <span>Responsable</span>                            
                        </div>
                        <div class="col-md-5">
                            <select class="form-control" name="id_responsable">
                                <%for (Miembro miembro : listaMiembros) {%>
                                <%if (miembro.getId_usuario() == userStory.getId_responsable()) {%>
                                <option value="<%=miembro.getId_usuario()%>" selected><%=miembro.getNombre_usuario()%></option>
                                <%}else{%>
                                <option value="<%=miembro.getId_usuario()%>"><%=miembro.getNombre_usuario()%></option>                                

                                <%}%>
                                <%}%>
                            </select>                           
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
                                   value="<%=userStory.getNombre()%>"
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
                                      onkeyup="this.value = this.value.toUpperCase()"><%=userStory.getDescripcion()%>                                          
                            </textarea>
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
                                   value="<%=userStory.getTiempo_estimado()%>"
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
                                   value="<%=userStory.getTiempo_trabajado()%>"
                                   type="number" 
                                   class="form-control input-sm" 
                                   placeholder="Tiempo Trabajado" 
                                   onkeyup="this.value = this.value.toUpperCase()"
                                   >
                        </div>
                    </div>

                    <!--PRIORIDAD-->             
                    <div class="row">
                        <div class="col-md-2">
                            <span>Prioridad</span>
                        </div>
                        <div class="col-md-5">
                            <select class="form-control" id="id_prioridad" name="id_prioridad"  value="<%=userStory.getId_prio()%>">
                                <% for (int i = 1; i <= 3; i++) {%>
                                <%if (i == 1 && userStory.getId_prio() == i) {%>
                                <option value="1" selected >HIGH</option>
                                <option value="2">MEDIUM</option>
                                <option value="3">LOW</option>
                                <%}%>    
                                <%if (i == 2 && userStory.getId_prio() == i) {%>
                                <option value="1">HIGH</option>
                                <option value="2" selected>MEDIUM</option>
                                <option value="3">LOW</option>
                                <%}%>    
                                <%if (i == 3 && userStory.getId_prio() == i) {%>
                                <option value="1">HIGH</option>
                                <option value="2">MEDIUM</option>
                                <option value="3" selected>LOW</option>
                                <%}%>    
                                <%}%>
                            </select>

                        </div>
                    </div>

                    <!--STORY POINTS-->             
                    <div class="row">
                        <div class="col-md-2">
                            <span>Story Points (Dificultad/Incertidumbre/Esfuerzo)</span>
                        </div>
                        <div class="col-md-5">
                            <select class="form-control" id="id_prioridad" name="story_points">
                                <%if (userStory.getStory_points() == 3) {%>
                                <option value="3" selected >3-Muy Facil</option>
                                <option value="5">5-Facil</option>
                                <option value="8">8-Mas o Menos</option>
                                <option value="13">13-Dificil</option>
                                <option value="21">21-Muy Dificil</option>

                                <%}else if (userStory.getStory_points() == 5) {%>
                                <option value="3">3-Muy Facil</option>
                                <option value="5" selected >5-Facil</option>
                                <option value="8">8-Mas o Menos</option>
                                <option value="13">13-Dificil</option>
                                <option value="21">21-Muy Dificil</option>

                                <%}else if (userStory.getStory_points() == 8) {%>
                                <option value="3">3-Muy Facil</option>
                                <option value="5">5-Facil</option>
                                <option value="8" selected >8-Mas o Menos</option>
                                <option value="13">13-Dificil</option>
                                <option value="21">21-Muy Dificil</option>

                                <%}else if (userStory.getStory_points() == 13) {%>
                                <option value="3">3-Muy Facil</option>
                                <option value="5">5-Facil</option>
                                <option value="8">8-Mas o Menos</option>
                                <option value="13" selected >13-Dificil</option>
                                <option value="21">21-Muy Dificil</option>

                                <%}else if (userStory.getStory_points()== 21) {%>
                                <option value="3">3-Muy Facil</option>
                                <option value="5">5-Facil</option>
                                <option value="8">8-Mas o Menos</option>
                                <option value="13">13-Dificil</option>
                                <option value="21" selected >21-Muy Dificil</option>

                                <%}%>
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
                                <% for (int i = 4; i <= 5; i++) {%>
                                <%if (i == 4 && userStory.getId_estados_backlog() == i) {%>
                                <option value="4" selected >SPRINT</option>
                                <option value="5">PRODUCT</option>
                                <%}%>    
                                <%if (i == 5 && userStory.getId_estados_backlog() == i) {%>
                                <option value="4">SPRINT</option>
                                <option value="5" selected>PRODUCT</option>
                                <%}%>       
                                <%}%>
                            </select>    
                        </div>
                    </div>

                    <!--ESTADO-->                    
                    <div class="row">
                        <div class="col-md-2">
                            <span>ESTADO</span>
                        </div>
                        <div class="col-md-5">
                            <select class="form-control" id="id_estado" name="id_estado">
                                <% for (int i = 1; i <= 3; i++) {%>
                                <%if (i == 1 && userStory.getId_estados() == i) {%>
                                <option value="1" selected >TO DO</option>
                                <option value="2">IN PROGRESS</option>
                                <option value="3">DONE</option>
                                <%}%>    
                                <%if (i == 2 && userStory.getId_estados() == i) {%>
                                <option value="1">TO DO</option>
                                <option value="2" selected>IN PROGRESS</option>
                                <option value="3">DONE</option>
                                <%}%>    
                                <%if (i == 3 && userStory.getId_estados() == i) {%>
                                <option value="1">TO DO</option>
                                <option value="2">IN PROGRESS</option>
                                <option value="3" selected>DONE</option>
                                <%}%>    
                                <%}%>
                            </select>    
                        </div>
                    </div>

                    <!--BOTON GUARFAR CAMBIOS-->
                    <div class="panel-footer centrado">
                        <input type="hidden" name="accion" value="actualizar_us"/>
                        <input type="hidden" name="id_sprint_actual" value="<%=id_sprint_actual%>">
                        <input type="hidden" name="accion_redireccion" value="<%=accion_redireccion%>"/>
                        <input type="hidden" name="redireccion" value="<%=redireccion%>"/>
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
