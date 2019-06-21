//variables globales

var idUsuario = 0;
var nickUsuario = "";
var proyectoActual = "";

function siguienteCampo(actual, siguiente, preventDefault) {
    $(actual).keydown(function (event) {
        if (event.which === 13) {
            if (preventDefault) {
                event.preventExtensions();
            }
            $(siguiente).focus();
            $(siguiente).select();
        }
    });
}

function enterCampo(actual, ejecutar) {
    $(actual).keydown(function (event) {
        if (event.which === 13) {
            eval(ejecutar);
        }
    });
}

function validarAcceso() {
    $("mensajes").html("Mensajes del sistema");

    if ($("usuario_usuario").val() === "") {
        $("#mensajes").html("Usuario no debe estar vacio");
    } else if ($("password_usuario").val() === "") {
        $("#mensajes").html("Mensajes del sistema");
        setTimeout('location.reload()', 1500);
    } else {
        validarAccesoAjax();
    }
}

function validarAccesoAjax() {

    var datosFormulario = $("#formAcceso").serialize();

    //alert(datosFormulario);

    $.ajax({
        type: "POST",
        url: "jsp/validarAcceso.jsp",
        data: datosFormulario,
        dataType: "json",
        beforeSend: function (objeto) {
            $("#mensajes").html("Enviando datos al servidor");
        },
        success: function (json) {
            if (json.acceso === "true") {

                if (json.usuario === "ADMIN") {
                    console.log("USUARIO ADMINISTRADOR");
                    location.href = "menu.html";
                } else
                    location.href = "menu1.html";
                console.log("USUARIO NO ADMINISTRADOR");
                $("#mis_proyectos").css("display", "none");
            } else {
                $("#mensajes").html("Credencial invalida");
                setTimeout('location.reload()', 1500);
            }
        },
        error: function (e) {
            $("#mensajes").html("No se pudo conectar con el servidor Error: " + e.status);
        },
        complete: function (objeto, exito, error) {
            if (exito === "success") {

            }
        }
    });
}

function verificarSesion(programa) {
    console.log("funcion verificar Sesion");
    var url = 'jsp/verificarSesion.jsp';
    if (programa) {
        url = "../../../jsp/verificarSesion.jsp";
    }

    var datosFormulario = $("#formAcceso").serialize();

    $.ajax({
        type: "POST",
        url: url,
        data: datosFormulario,
        async: false,
        dataType: "json",

        beforeSend: function (objeto) {
            $("#mensajes").html("Enviando datos al servidor");
        },
        success: function (json) {
            if (json.activo === "false") {
                if (programa) {
                    location.href = "/../index.html";
                } else {
                    location.href = "/../index.html";
                }
                console.log("por favor inicie sesión");
            }
            $("#snombre_empresa").html(json.nombre_empresa);
            $("#susuario_usuario").html(json.login_usuario);
            $("#mensajes").html(json.mensajes);
            setDatosSesion(json.id_usuario, json.login_usuario);

        },
        error: function (e) {
            $("#mensajes").html("ERROR: No se pudo recuperar la sesion.");

        },
        complete: function (objeto, exito, error) {
            if (exito === "success") {

            }
        }
    });

}

function cerrarSesion() {
    var datosFormulario = $("#formAcceso").serialize();

    $.ajax({
        type: "POST",
        url: "jsp/cerrarSesion.jsp",
        data: datosFormulario,
        dataType: "json",
        beforeSend: function (objeto) {
            $("#mensajes").html("Enviando datos al servidor");
        },
        success: function (json) {
            $("#mensajes").html("Sesion Cerrada.");
        },
        error: function (e) {
            $("#mensajes").html("ERROR: No se pudo cerrar la sesion.");

        },
        complete: function (objeto, exito, error) {
            if (exito === "success") {

            }
        }
    });

}

function buscarMisProyecto() {
    console.log("funcion buscar Mis Proyectos");
    console.log(idUsuario);

    $.ajax({
        type: 'POST',
        url: 'jsp/misProyectos.jsp',
        data: {id_usuario: idUsuario},
        dataType: 'json',
        beforeSend: function (objeto) {
            $("#mensajes").html("Enviando datos al servidor...");
            $("#contenidoBusqueda").css("display", "none");
        },
        success: function (json) {

            //se muestra el mensaje proveniente del servicio
            $("#mensajes").html(json.mensaje);

            //se carga en la seccion de contenido el resultado proveido por el 
            //servisio
            $("#contenidoBusqueda").html(json.contenido);

            //se despliega el resultado en pantalla
            $("#contenidoBusqueda").fadeIn("slow");

        },
        error: function (e) {
            $("#mensajes").html("No se pudo buscar registros");
        },
        complete: function (objeto, exito, error) {
            if (exito === "success") {

            }
        }
    });
}

function setDatosSesion(id_usuario, login_usuario) {
    console.log("funcion set datos sesion");
    idUsuario = id_usuario;
    nickUsuario = login_usuario;
}

function setDatosProyecto(id_proyecto){
    console.log("funcion set datos proyecto");
    proyectoActual = id_proyecto;
    
}

function buscarUSBacklog(id_proyecto) {

    console.log("funcion buscar user stories backlog");

    //aca se va a hacer la llamada ajax para recuperar la lista de US
    //del proyectoActual WHERE ID_BACKLOG=PRODUCT

    $.ajax({
        type: 'POST',
        url: 'jsp/buscarUSBacklog.jsp',
        data: {id_usuario: idUsuario, id_proyecto: id_proyecto},
        dataType: 'json',
        beforeSend: function (objeto) {
            $("#mensajes").html("Enviando datos al servidor...");
            //$("#contenidoBusqueda").css("display", "none");
            //se muestra el panel principal del programa
            $("#panelMisProyectos").fadeOut("slow");            
            $("#panelBacklog").load("backlog.html");
            $("#panelBacklog").fadeIn("slow");
        },
        success: function (json) {

            //se muestra el mensaje proveniente del servicio
            $("#mensajesBacklog").html(json.mensaje);
            
            //se muestra el nombre del proyecto en el encabezados
            $("#nombreProyecto").html(id_proyecto);
            $("#botonAgregarUS").val(id_proyecto);

            //se carga en la seccion de contenido el resultado proveido por el 
            //servisio
            $("#listaUSBacklog").html(json.contenido);

            //se despliega el resultado en pantalla
            $("#listaUSBacklog").fadeIn("slow");

        },
        error: function (e) {
            $("#mensajes").html("No se pudo buscar registros");
        },
        complete: function (objeto, exito, error) {
            if (exito === "success") {

            }
        }
    });

}

function buscarUSSprint(id_proyecto) {

    console.log("funcion buscar user stories sprint");
    
    $.ajax({
        type: 'GET',
        url: 'jsp/buscarUSSprint.jsp',
        data: {id_usuario: idUsuario, id_proyecto: id_proyecto},
        dataType: 'json',
        beforeSend: function (objeto) {
            $("#mensajes").html("Enviando datos al servidor...");
            //$("#contenidoBusqueda").css("display", "none");
            $("#panelMisProyectos").fadeOut("slow");            
            $("#panelSprintBacklog").load("sprintBacklog.html");
            $("#panelSprintBacklog").fadeIn("slow");            
        },
        success: function (json) {

            //se muestra el mensaje proveniente del servicio
            $("#mensajes").html(json.mensaje);

            //se muestra el nombre del proyecto en el encabezados
            $("#nombreProyectoSprint").html(id_proyecto);

            //se carga en la seccion de contenido el resultado proveido por el 
            //servisio
            $("#listaUSSprint").html(json.contenido);

            //se despliega el resultado en pantalla
            $("#listaUSSprint").fadeIn("slow");

        },
        error: function (e) {
            $("#mensajes").html("No se pudo buscar registros");
        },
        complete: function (objeto, exito, error) {
            if (exito === "success") {

            }
        }
    });
    //aca se va a hacer la llamada ajax para recuperar la lista de US
    //del proyectoActual WHERE ID_BACKLOG=SPRINT

}

function editarLinea(data){
    console.log("funcion editar linea");
    
}

function eliminarLinea(data){
    console.log("funcion eliminar linea");
    
}

function agregarUS(id_proyecto){
    console.log("funcion agregar US");
   // var id_proyecto = document.getElementById("nombreProyecto").innerHTML;
    
    //var datosFormulario = $("formAgregarUS").serialize();
       
    $("#panelBacklog").fadeOut("slow");
    $("#panelAgregarUS").load("agregarUS.html");
    $("#panelAgregarUS").fadeIn("slow");
    $("#nombreProyectoAgregarUS").html(id_proyecto);
    $("#nombre_us").val("holalaaa");
    alert(id_proyecto);
    console.log(id_proyecto);
    
}