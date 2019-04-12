

function buscarIdProyecto(){
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
            $("#id_proyecto").val(json.id_proyecto);
            $("#nombre_proyecto").val(json.nombre_proyecto);
            $("#descripcion_proyecto").val(json.nombre_proyecto);
            $("#nombre_proyecto").val(json.nombre_proyecto);
            
            if (json.nuevo === "true") {
                $("#botonAgregar").prop('disabled',false);
                $("#botonModificar").prop('disabled',true);
                $("#botonEliminar").prop('disabled',true);
                //siguienteCampo("#nombre_proyecto","#botonAgregar",true);
            }else{
                $("#botonAgregar").prop('disabled',true);
                $("#botonModificar").prop('disabled',false);
                $("#botonEliminar").prop('disabled',false);
                //siguienteCampo("#nombre_proyecto","#botonAgregar",true);            
            }            
        },
        error: function(e){
            $("#mensajes").html("No se pudo recuperar los datos.");
        },
        complete:function(objeto,exito,error){
            if (exito === 'success') {
                
            }
        }
    });   
    
}

function agregarProyecto(){

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
            $("#id_proyecto").focus();
            $("#id_proyecto").select();
        },
        error: function(e){
            $("#mensajes").html("No se pudieron agregar los datos.");
        },
        complete:function(objeto,exito,error){
            $("#id_proyecto").focus();
        }
    });
}

function modificarProyecto(){
    
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
            $("#id_proyecto").focus();
            $("#id_proyecto").select();
        },
        error: function (e) {
            $("#mensajes").html("No se pudo modificar los datos")
        },
        complete: function (objeto,exito,error) {
            
        }
    });
    
}//fin de la funcion

function buscarNombreProyecto(){
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
                $("#id_proyecto").val(id);
                $("#nombre_proyecto").focus();
                buscarIdProyecto();
                $("#buscar").fadeOut("slow");                
                $("#panelPrograma").fadeIn("slow");                
            });
            
  
        },
        error: function (e) {
            $("#mensajes").html("No se pudo buscar registros")
        },
        complete: function (objeto,exito,error) {
            if (exito === "success") {
                
            }
        }
    });    
}

function eliminarProyecto(){
    
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
            $("#id_proyecto").focus();
            $("#id_proyecto").select();
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
    $("#id_proyecto").val("0");
    $("#nombre_proyecto").val("");
    
}//fin de la funcion

function validarFormulario(){
    var valor = true;
    //se verfica que el campo de texto no este vacio
    if ($("#nombre_proyecto").val().trim() === "") {
        valor = false;
        $("#mensajes").html("Nombre no puede estar vacio.");
        $("#nombre_proyecto").focus();
    }
    return valor;
}//fin de la funcion