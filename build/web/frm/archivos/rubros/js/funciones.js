

function buscarIdRubro(){
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
            $("#id_rubro").val(json.id_rubro);
            $("#nombre_rubro").val(json.nombre_rubro);
            
            if (json.nuevo === "true") {
                $("#botonAgregar").prop('disabled',false);
                $("#botonModificar").prop('disabled',true);
                $("#botonEliminar").prop('disabled',true);
                //siguienteCampo("#nombre_rubro","#botonAgregar",true);
            }else{
                $("#botonAgregar").prop('disabled',true);
                $("#botonModificar").prop('disabled',false);
                $("#botonEliminar").prop('disabled',false);
                //siguienteCampo("#nombre_rubro","#botonAgregar",true);            
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

function agregarRubro(){

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
            $("#id_rubro").focus();
            $("#id_rubro").select();
        },
        error: function(e){
            $("#mensajes").html("No se pudieron agregar los datos.");
        },
        complete:function(objeto,exito,error){
            $("#id_rubro").focus();
        }
    });
}

function modificarRubro(){
    
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
            $("#id_rubro").focus();
            $("#id_rubro").select();
        },
        error: function (e) {
            $("#mensajes").html("No se pudo modificar los datos")
        },
        complete: function (objeto,exito,error) {
            
        }
    });
    
}//fin de la funcion

function buscarNombreRubro(){
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
                $("#id_rubro").val(id);
                $("#nombre_rubro").focus();
                buscarIdRubro();
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

function eliminarRubro(){
    
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
            $("#id_rubro").focus();
            $("#id_rubro").select();
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
    $("#id_rubro").val("0");
    $("#nombre_rubro").val("");
    
}//fin de la funcion

function validarFormulario(){
    var valor = true;
    //se verfica que el campo de texto no este vacio
    if ($("#nombre_rubro").val().trim() === "") {
        valor = false;
        $("#mensajes").html("Nombre no puede estar vacio.");
        $("#nombre_rubro").focus();
    }
    return valor;
}//fin de la funcion