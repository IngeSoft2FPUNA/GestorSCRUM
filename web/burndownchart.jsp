<%
    String id_proyecto = (String)request.getParameter("id_proyecto");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Burndown Chart | <%=id_proyecto%></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="stylesheet" href="css/bootstrap-theme.css" type="text/css"/>
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/estilos.css" type="text/css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
        <style>
            #burndown-chart{
                margin: auto;
                margin-top: 5%;                
                box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
                transition: 0.3s;
            }

            ul{
                margin: auto;
                width: 1030px;
            }
            ul li {
                display:inline-block;         
                vertical-align:top;
                padding: 5px;
            }
        </style>
    </head>

    <body>
        <form id="fromReporte">
            <input type="hidden" name="id_proyecto" value="<%=id_proyecto%>">            
        </form>
        <div id="panelMisProyectos" class="panel panel-primary">
            <div class="panel-heading centrado">Burndown Chart</div>
            <div class="panel-body">
                <canvas id="burndown-chart" width="800" height="450"></canvas>
            </div>       
        </div>        



        <script src = "js/jquery.min.js" type="text/javascript"></script>
        <script src = "js/bootstrap.min.js" type="text/javascript"></script>
        <script src = "js/reportes.js" type="text/javascript"></script>
        <script>
            burnDownChartReporte();


        </script>

    </body>
</html>