//variables globales

var idUsuario = 0;
var vnickUsuario = "";
var proyectoSeleccionado = "";

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
            $("#id_usuario").html(json.id_usuario);
            $("#mensajes").html(json.mensajes);
            $("#mis_proyectos").css("display", "none");
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

    $.ajax({//REEMPLAZAR ESTA PARTE POR UNA LLAMADA A JSP QUE DEVUELVA LOS PROYECTOS PARA UN USUARIO
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

            //evento: se presiona una fila de la tabla
            $("#botonVerBacklog").on("click", function () {

                //se captura el id que corresponde a la primera columna de cada fila
                var id = $(this).find("tbody tr td:first").html();
                sessionStorage.setItem("proyectoActual", $(this).find("tbody tr td:first")) ;
                proyectoActual = document.getElementById("id_proyecto").innerHTML;
                
                console.log("id proyecto:"+proyectoActual);
                //$("#panelBuscar").html("");

                //se pone el id en el campo de texto y luego se llama a la funcion
                //para buscar por id (ezta parte no hace nada) 
                $("#id_proyecto").val(id);
                $("#nombre_proyecto").focus();
                //buscarIdProyecto();

                //se muestra el panel principal del programa
                $("#panelBacklog").load("backlog.html");
                $("#panelBacklog").fadeIn("slow");
                $("#panelMisProyectos").fadeOut("slow");
            });

            $("#botonVerSprint").on("click", function () {

                //se captura el id que corresponde a la primera columna de cada fila
                var id = $(this).find("tbody tr td:first").html();
               sessionStorage.setItem("proyectoActual", $(this).find("tbody tr td:first"));
                proyectoActual = sessionStorage.getItem("proyectoActual"); 
                console.log("id proyecto:"+proyectoActual);
                //$("#panelBuscar").html("");

                //se pone el id en el campo de texto y luego se llama a la funcion
                //para buscar por id (ezta parte no hace nada) 
                $("#id_proyecto").val(id);
                $("#nombre_proyecto").focus();
                //buscarIdProyecto();

                //se muestra el panel principal del programa
                $("#panelSprintBacklog").load("sprintBacklog.html");
                $("#panelSprintBacklog").fadeIn("slow");
                $("#panelMisProyectos").fadeOut("slow");
            });


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

function buscarUSBacklog() {

    console.log("funcion buscar user stories");
    $("#nombreProyecto").html(proyectoActual);

    //aca se va a hacer la llamada ajax para recuperar la lista de US
    //del proyectoActual WHERE ID_BACKLOG=PRODUCT

}
function buscarUSSprint() {

    console.log("funcion buscar user stories");
    $("#nombreProyecto").html(proyectoActual);

    //aca se va a hacer la llamada ajax para recuperar la lista de US
    //del proyectoActual WHERE ID_BACKLOG=SPRINT

}
