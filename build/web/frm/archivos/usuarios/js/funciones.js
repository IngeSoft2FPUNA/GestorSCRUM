

function buscarIdUsuario(){
    var datosFormulario = $("#formPrograma").serialize();
     $.ajax({
        type:'POST',
        url: 'jsp/buscarId.jsp',
        data: datosFormulario,
        dataType: 'json',
       
        beforeSend: function(objeto){
            $("#mensajes").html("Enviando datos al servidor...");
        },
        success: function(json){
            $("#mensajes").html(json.mensaje);
           $("#id_usuario").val(json.id_usuario);
            $("#nombre_usuario").val(json.nombre_usuario);
            $("#cedula").val(json.cedula);
            $("#correo_electronico").val(json.correo);
            $("#nick_usuario").val(json.login_usuario);
            $("#password_usuario").val(json.password_usuario);

            
            if (json.nuevo === "true") {
                $("#botonAgregar").prop('disabled',false);
                $("#botonModificar").prop('disabled',true);
                $("#botonEliminar").prop('disabled',true);
                //siguienteCampo("#nombre_usuario","#botonAgregar",true);
            }else{
                $("#botonAgregar").prop('disabled',true);
                $("#botonModificar").prop('disabled',false);
                $("#botonEliminar").prop('disabled',false);
                //siguienteCampo("#nombre_usuario","#botonAgregar",true);            
            }            
        },
        error: function(e){
            $("#mensajes").html("No se pudo recuperars los datos.");
        },
        complete:function(objeto,exito,error){
            if (exito === 'success') {
                
            }
        }
    });   
    
}

function agregarUsuario(){

    var datosFormulario = $("#formPrograma").serialize();
    $.ajax({
        type:'POST',
        url: 'jsp/agregar.jsp',
        data: datosFormulario,
        dataType: 'json',
        beforeSend: function(objeto){
            $("#mensajes").html("Enviando datos al servidor...");
        },
        success: function(json){
            $("#mensajes").html(json.mensaje);
            limpiarFormulario();
//            $("#id_usuario").focus();
//            $("#id_usuario").select();
        },
        error: function(e){
            $("#mensajes").html("No se pudieron agregar los datos.");
        },
        complete:function(objeto,exito,error){
            //$("#id_usuario").focus();
        }
    });
}

function modificarUsuario(){
    
    var datosFormulario = $("#formPrograma").serialize();

    $.ajax({
       type: 'POST',
       url: 'jsp/modificar.jsp',
       data: datosFormulario,
       dataType: 'json',
        beforeSend: function (objeto) {
            $("#mensajes").html("Enviando datos al servidor...");
        },
        success: function (json) {
            $("#mensajes").html(json.mensaje);
            limpiarFormulario();
            $("#id_usuario").focus();
            $("#id_usuario").select();
        },
        error: function (e) {
            $("#mensajes").html("No se pudo modificar los datos");
        },
        complete: function (objeto,exito,error) {
            
        }
    });
    
}//fin de la funcion

function buscarNombreUsuario(){
    var datosFormulario = $("#formBuscar").serialize();

    $.ajax({
       type: 'POST',
       url: 'jsp/buscarNombre.jsp',
       data: datosFormulario,
       dataType: 'json',
        beforeSend: function (objeto) {
            $("#mensajes").html("Enviando datos al servidor...");
            $("#contenidoBusqueda").css("display","none");
        },
        success: function (json) {
            $("#mensajes").html(json.mensaje);
            $("#contenidoBusqueda").html(json.contenido);
            $("#contenidoBusqueda").fadeIn("slow");
            $("tbody tr").on("click",function(){
                var id = $(this).find("td:first").html();
                $("#panelBuscar").html("");
                $("#id_usuario").val(id);
                $("#nombre_usuario").focus();
                buscarIdUsuario();
                $("#buscar").fadeOut("slow");                
                $("#panelPrograma").fadeIn("slow");                
            });
        },
        error: function (e) {
            $("#mensajes").html("No se pudo buscar registros");
        },
        complete: function (objeto,exito,error) {
            if (exito === "success") {
                
            }
        }
    });    
}

function eliminarUsuario(){
    
    var datosFormulario = $("#formPrograma").serialize();
    
    $.ajax({
        
        type: 'POST',
        url: 'jsp/eliminar.jsp',
        data: datosFormulario,
        datatype: 'json',
        beforesend: function(objeto){
            $("#mensajes").html("Enviando datos al servidor...");
        },
        success: function(json){
            $("#mensajes").html(json.mensaje);            
            limpiarFormulario();
            $("#id_usuario").focus();
            $("#id_usuario").select();
        },
        error: function(e){
            $("#mensajes").html("No se pudieron eliminar los datos");            
        },
        complete: function(objeto,exito,error){
            if (exito==="success") {                
            }
        }
    });
    
}

function limpiarFormulario(){
    //se limpian los campos de textos
    $("#id_usuario").val("0");
    $("#nombre_usuario").val("");
    $("#cedula").val("");
    $("#correo_electronico").val("");
    $("#nick_usuario").val("");
    $("#password_usuario").val("");
    
}//fin de la funcion

function validarFormulario(){
    var valor = true;
    //se verfica que el campo de texto no este vacio
    if ($("#nombre_usuario").val().trim() === "") {
        valor = false;
        $("#mensajes").html("Nombre no puede estar vacio.");
        $("#nombre_usuario").focus();
    }
    return valor;
}//fin de la funcion