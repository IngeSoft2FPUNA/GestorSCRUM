function storyPointsReporte() {

    var datosFormulario = $("#fromReporte").serialize();

    $.ajax({
        type: "POST",
        url: "reporteRepository/storypoints.jsp",
        data: datosFormulario,
        dataType: "json",
        beforeSend: function (objeto) {

        },
        success: function (json) {
            console.log(json.listaIdSprints);
            console.log(json.listaStoryPointsEstimados);
            console.log(json.listaStoryPointsRealizados);
            graficoStoryPoints(json.listaIdSprints, json.listaStoryPointsEstimados, json.listaStoryPointsRealizados);
        },
        error: function (e) {

        },
        complete: function (objeto, exito, error) {
            if (exito === "success") {

            }
        }
    });
}

function graficoStoryPoints(idSprints, storyPointsEstimados, storyPointsRealizados) {
    new Chart(document.getElementById("story-points-chart"), {
        type: 'bar',
        data: {
            labels: idSprints, //array de los id_sprint
            datasets: [
                {
                    label: "Estimados",
                    backgroundColor: "#3e95cd",
                    data: storyPointsEstimados //array de story points estimados
                }, {
                    label: "Realizados",
                    backgroundColor: "#8e5ea2",
                    data: storyPointsRealizados //array de story points realizados
                }
            ]
        },
        options: {
            title: {
                display: true,
                text: 'Story Points Chart'
            },
            responsive: false,
            scales: {
                yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
            }
        }
    });

}

function duracionReporte() {

    var datosFormulario = $("#fromReporte").serialize();

    $.ajax({
        type: "POST",
        url: "reporteRepository/duracion.jsp",
        data: datosFormulario,
        dataType: "json",
        beforeSend: function (objeto) {

        },
        success: function (json) {
            console.log(json.listaIdSprints);
            console.log(json.listaDuracionEstimada);
            console.log(json.listaDuracionReal);
            graficoDuracion(json.listaIdSprints, json.listaDuracionEstimada, json.listaDuracionReal);
        },
        error: function (e) {

        },
        complete: function (objeto, exito, error) {
            if (exito === "success") {

            }
        }
    });
}

function graficoDuracion(idSprints, listaDuracionEstimada, listaDuracionReal) {
    new Chart(document.getElementById("duracion-chart"), {
        type: 'horizontalBar',
        data: {
            labels: idSprints, //array de los id_sprint
            datasets: [
                {
                    label: "Duracion Estimada",
                    backgroundColor: "#3e95cd",
                    data: listaDuracionEstimada //array de story points estimados
                }, {
                    label: "Duracion Real",
                    backgroundColor: "#8e5ea2",
                    data: listaDuracionReal //array de story points realizados
                }
            ]
        },
        options: {
            title: {
                display: true,
                text: 'Duracion de Sprints en dias'
            },
            responsive: false
        }
    });
}

function nroUserSoriesSprint() {

    var datosFormulario = $("#fromReporte").serialize();

    $.ajax({
        type: "POST",
        url: "reporteRepository/numeroUS.jsp",
        data: datosFormulario,
        dataType: "json",
        beforeSend: function (objeto) {

        },
        success: function (json) {
            console.log(json.listaIdSprints);
            console.log(json.cantidadUSSprint);
            graficoNroUs(json.listaIdSprints, json.cantidadUSSprint);
        },
        error: function (e) {

        },
        complete: function (objeto, exito, error) {
            if (exito === "success") {

            }
        }
    });
}

function graficoNroUs(idSprints, cantidadUSSprint) {

    new Chart(document.getElementById("nro-us-chart"), {
        type: 'bar',
        data: {
            labels: idSprints, //array de los id_sprint
            datasets: [
                {
                    label: "US Terminados",
                    backgroundColor: "#3e95cd",
                    data: cantidadUSSprint //cantidad de US por Sprint
                }
            ]
        },
        options: {
            title: {
                display: true,
                text: 'Cantidad de US por Sprint'
            },
            responsive: false,
            scales: {
                yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
            }
        }
    });
}

function nroUserSoriesUsuarios() {

    var datosFormulario = $("#fromReporte").serialize();

    $.ajax({
        type: "POST",
        url: "reporteRepository/numeroUSUsuarios.jsp",
        data: datosFormulario,
        dataType: "json",
        beforeSend: function (objeto) {

        },
        success: function (json) {
            console.log(json.listaNombreUsuarios);
            console.log(json.listacantidadUSSprint);
            graficoNroUsUsuarios(json.listaNombreUsuarios, json.listacantidadUSSprint);
        },
        error: function (e) {

        },
        complete: function (objeto, exito, error) {
            if (exito === "success") {

            }
        }
    });
}

function graficoNroUsUsuarios(listaNombreUsuarios, listacantidadUSSprint) {
    new Chart(document.getElementById("cantidad-us-usuario"), {
        type: 'doughnut',
        data: {
            labels: listaNombreUsuarios,
            datasets: [
                {
                    label: "Cantidad:",
                    backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f", "#e8c3b9", "#c45850"],
                    data: listacantidadUSSprint
                }
            ]
        },
        options: {
            title: {
                display: true,
                text: 'Cantidad de US realizados por cada Miembro'
            },
            responsive: false,
            legend: {
                position: 'left',
                padding: 20
            }
        }
    });


}

function burnDownChartReporte() {

    var datosFormulario = $("#fromReporte").serialize();

    $.ajax({
        type: "POST",
        url: "reporteRepository/burndownchart.jsp",
        data: datosFormulario,
        dataType: "json",
        beforeSend: function (objeto) {

        },
        success: function (json) {
            console.log(json.listaBurnDownChart);
            console.log(json.listaDias);
            console.log(json.cantidadStoryPointsEstimados);
            graficoBurnDown(json.listaBurnDownChart, json.listaDias, json.cantidadStoryPointsEstimados);
        },
        error: function (e) {

        },
        complete: function (objeto, exito, error) {
            if (exito === "success") {

            }
        }
    });
}

function graficoBurnDown(listaBurnDownChart, listaDias, cantidadStoryPointsEstimados) {
    var speedCanvas = document.getElementById("burndown-chart");
    storyPointsEstimados = [];
    storyPointsEstimados[0] = cantidadStoryPointsEstimados; //primer elemento
    storyPointsPerDay = cantidadStoryPointsEstimados / (listaDias.length - 1);

    for (var i = 0; i < listaDias.length; i++) {
        storyPointsEstimados[i] = Math.round(cantidadStoryPointsEstimados - (storyPointsPerDay * i));
    }
    console.log(storyPointsPerDay);
    console.log(storyPointsEstimados);

    //Chart.defaults.global.defaultFontFamily = "Lato";
    Chart.defaults.global.defaultFontSize = 15;

    var dataFirst = {
        label: "Story Points Restantes",
        data: listaBurnDownChart,
        lineTension: 0,
        fill: true,
        borderColor: 'red'
    };

    var dataSecond = {
        label: "Story Points Estimados",
        data: storyPointsEstimados,
        lineTension: 0,
        fill: false,
        borderColor: 'blue'
    };

    var speedData = {
        labels: listaDias,
        datasets: [dataFirst, dataSecond]
    };

    var chartOptions = {
        title: {
            display: true,
            text: 'Burndown Chart'
        },
        legend: {
            display: true,
            position: 'top',
            labels: {
                boxWidth: 80,
                fontColor: 'black'
            }
        },
        responsive: false,
        scales: {
            yAxes: [{
                    ticks: {
                        min: 0
                    }
                }]
        }
    };

    var lineChart = new Chart(speedCanvas, {
        type: 'line',
        data: speedData,
        options: chartOptions
    });


}