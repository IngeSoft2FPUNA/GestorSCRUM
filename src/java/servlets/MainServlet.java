/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import controladores.ProyectoControlador;
import controladores.SprintControlador;
import controladores.UserstoryControlador;
import controladores.UsuarioControlador;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Miembro;
import modelos.Sprint;
import modelos.UserStory;
import modelos.Usuario;

/**
 *
 * @author user
 */
@WebServlet(name = "MainServlet", urlPatterns = {"/MainServlet"})
public class MainServlet extends HttpServlet {
    HttpSession misession = null; 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        String id_proyecto = request.getParameter("id_proyecto");
        String redireccion = request.getParameter("redireccion");

        if (accion.equals("ver_product_backlog")) {

            System.out.println("ver product backlog");
            redireccion = "productBacklog.jsp";
            ArrayList<UserStory> listaUs = UserstoryControlador.buscarUSBacklog(id_proyecto);
            ArrayList<UserStory> listaPRODUCT = new ArrayList<UserStory>();
            ArrayList<UserStory> listaSPRINT = new ArrayList<UserStory>();

            //dividir la lista segun su tipo de backlog
            for (UserStory userStory : listaUs) {
                if (userStory.getId_estados_backlog() == 4) {
                    listaSPRINT.add(userStory);
                } else {
                    listaPRODUCT.add(userStory);
                }
            }

            //obtener el sprint actual
            Sprint sprintActual = SprintControlador.obtenerSprintActual(id_proyecto);

            request.setAttribute("sprintActual", sprintActual);
            request.setAttribute("listaPRODUCT", listaPRODUCT);
            request.setAttribute("listaSPRINT", listaSPRINT);
            RequestDispatcher rd = request.getRequestDispatcher(redireccion);
            rd.forward(request, response);

        } else if (accion.equals("ver_sprint_backlog")) {
            System.out.println("ver sprint backlog");
            redireccion = "sprintBacklog.jsp";
            ArrayList<UserStory> listaUs = UserstoryControlador.buscarUSSprint(id_proyecto);
            ArrayList<UserStory> listaTODO = new ArrayList<UserStory>();
            ArrayList<UserStory> listaINPROGRESS = new ArrayList<UserStory>();
            ArrayList<UserStory> listaDONE = new ArrayList<UserStory>();
            String nombre_responsable = "";

            for (int i = 0; i < listaUs.size(); i++) {
                nombre_responsable = UsuarioControlador.buscarUsuario(listaUs.get(i).getId_responsable()).getNombre();

                listaUs.get(i).setNombre_responsable(nombre_responsable);
            }

            //dividir la lista segun su estado
            for (UserStory userStory : listaUs) {
                if (userStory.getId_estados() == 1) {
                    listaTODO.add(userStory);
                } else if (userStory.getId_estados() == 2) {
                    listaINPROGRESS.add(userStory);
                } else {
                    listaDONE.add(userStory);
                }
            }

            //obtener el sprint actual
            Sprint sprintActual = SprintControlador.obtenerSprintActual(id_proyecto);

            System.out.println("lista to do:" + listaTODO.size());
            System.out.println("lista in progress:" + listaINPROGRESS.size());
            System.out.println("lista done:" + listaDONE.size());

            request.setAttribute("sprintActual", sprintActual);
            request.setAttribute("listaTODO", listaTODO);
            request.setAttribute("listaINPROGRESS", listaINPROGRESS);
            request.setAttribute("listaDONE", listaDONE);
            RequestDispatcher rd = request.getRequestDispatcher(redireccion);
            rd.forward(request, response);

        } else if (accion.equals("ver_miembros")) {
            System.out.println("ver miembros");
            redireccion = "verMiembros.jsp";
            ArrayList<Miembro> listaMiembros = ProyectoControlador.listarMiembros(id_proyecto);
            request.setAttribute("listaMiembros", listaMiembros);
            RequestDispatcher rd = request.getRequestDispatcher(redireccion);
            rd.forward(request, response);

        } else if (accion.equals("agregar_us")) {
            System.out.println("agregar us");
            redireccion = "agregarUS.jsp";
            
            ArrayList<Miembro> listaMiembros = ProyectoControlador.listarMiembros(id_proyecto);

            request.setAttribute("listaMiembros", listaMiembros);
            RequestDispatcher rd = request.getRequestDispatcher(redireccion);
            rd.forward(request, response);

        } else if (accion.equals("editar_us")) {
            System.out.println("editar us");
            redireccion = "editarUS.jsp";
            String id_us = request.getParameter("id_us");

            UserStory userStoryTemp = UserstoryControlador.getUS(id_us, id_proyecto);
            ArrayList<Miembro> listaMiembros = ProyectoControlador.listarMiembros(id_proyecto);
            System.out.println(userStoryTemp);

            request.setAttribute("listaMiembros", listaMiembros);
            request.setAttribute("userStory", userStoryTemp);
            RequestDispatcher rd = request.getRequestDispatcher(redireccion);
            rd.forward(request, response);

        } else if (accion.equals("eliminar_us")) {
            System.out.println("eliminar us");
            String accion_redireccion = request.getParameter("accion_redireccion");
            String id_us = request.getParameter("id_us");
            id_proyecto = request.getParameter("id_proyecto");

            UserStory userStoryTemp = new UserStory();
            userStoryTemp.setId_proyecto(id_proyecto);
            userStoryTemp.setId_us(id_us);
            System.out.println(userStoryTemp);
            boolean eliminado = UserstoryControlador.eliminar(userStoryTemp);

            if (eliminado) {
                System.out.println("se ha eliminado el us");
            } else {
                System.out.println("no se ha eliminado el usx");

            }
            
            //se actualizan los datos del sprint
            int id_sprint_actual = Integer.parseInt(request.getParameter("id_sprint_actual"));
            Sprint sprintActual = new Sprint();
            sprintActual.setId_proyecto(id_proyecto);
            sprintActual.setId_sprint(id_sprint_actual);

            ////se actualiza la duracion y los storypoints estimados
            SprintControlador.actualizarSprint(sprintActual);

            request.setAttribute("accion", accion_redireccion);
            request.setAttribute("id_proyecto", id_proyecto);
            request.setAttribute("redireccion", redireccion);
            RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
            rd.forward(request, response);

        } else if (accion.equals("agregar_miembro")) {
            System.out.println("agregar miembro");
            redireccion = "agregarMiembro.jsp";

            //obtener la lista de todos los usuarios que no formen parte del proyecto
            ArrayList<Usuario> listaUsuarios = UsuarioControlador.listarUsuarios(id_proyecto);

            System.out.println(listaUsuarios);
            request.setAttribute("listaUsuarios", listaUsuarios);
            RequestDispatcher rd = request.getRequestDispatcher(redireccion);
            rd.forward(request, response);

        } else if (accion.equals("editar_miembro")) {
            System.out.println("editar miembro");
            redireccion = "editarMiembro.jsp";

            //obtener la lista de todos los usuarios activos
            //ArrayList<Usuario> listaUsuarios = UsuarioControlador.listarUsuarios();

            //System.out.println(listaUsuarios);
            //request.setAttribute("listaUsuarios", listaUsuarios);
            RequestDispatcher rd = request.getRequestDispatcher(redireccion);
            rd.forward(request, response);
        } else if (accion.equals("eliminar_miembro")) {
            System.out.println("eliminar miembro");

            id_proyecto = request.getParameter("id_proyecto");
            int id_miembro = Integer.parseInt(request.getParameter("id_miembro"));
            int id_rol = Integer.parseInt(request.getParameter("id_rol"));

            boolean eliminado = UsuarioControlador.eliminarMiembro(id_proyecto, id_rol, id_miembro);

            if (eliminado) {
                System.out.println("se ha modificado el miembro");
            } else {
                System.out.println("no se ha modificado el miembro");

            }

        } else if (accion.equals("home")) {
            System.out.println("home");
            // obtener la lista de US para la tabla MI ACTIVIDAD
            int id_usuario = (Integer)misession.getAttribute("id_usuario");
            ArrayList<UserStory> listaMiActividad = UserstoryControlador.listarMiActividad(id_usuario);

            request.setAttribute("listaMiActividad", listaMiActividad);
            RequestDispatcher rd = request.getRequestDispatcher("menu1.jsp");
            rd.forward(request, response);

        } else if (accion.equals("ver_estadisticas")) {
            System.out.println("ver estadisticas");
            RequestDispatcher rd = request.getRequestDispatcher("verReporte.jsp");
            rd.forward(request, response);

        } else if (accion.equals("ver_burndown_chart")) {
            System.out.println("ver burndown chart");
            RequestDispatcher rd = request.getRequestDispatcher("burndownchart.jsp");
            rd.forward(request, response);

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String accion = request.getParameter("accion");
        String redireccion = request.getParameter("redireccion");
        System.out.println(redireccion);

        if (accion.equals("login")) {
            System.out.println(accion);
            String login_usuario = request.getParameter("usuario_usuario");
            String password_usuario = request.getParameter("password_usuario");

            String acceso = "false";
            //boolean acceso = UsuariosControlador.validarAcceso(usuario);

            Usuario usuario = new Usuario();
            usuario.setUsuario(login_usuario);
            usuario.setPassword(password_usuario);
            usuario = UsuarioControlador.validarAcceso(usuario, request);
            if (usuario != null) {
                misession= request.getSession(true);
                misession.setAttribute("id_usuario", usuario.getId_usuario());
                acceso = "true";
                // obtener la lista de US para la tabla MI ACTIVIDAD
                ArrayList<UserStory> listaMiActividad = UserstoryControlador.listarMiActividad(usuario.getId_usuario());

                request.setAttribute("listaMiActividad", listaMiActividad);
                request.setAttribute("id_rol_sistema", usuario.getRol_sistema());
                RequestDispatcher rd = request.getRequestDispatcher(redireccion);
                rd.forward(request, response);

            } else {
                System.out.println("credenciales invalidas");
                RequestDispatcher rd = request.getRequestDispatcher("index.html");
                rd.forward(request, response);
                
            }
        } else if (accion.equals("grabar_miembro")) {
            System.out.println("grabar miembro");

            int id_rol = Integer.parseInt(request.getParameter("id_rol"));
            int id_miembro = Integer.parseInt(request.getParameter("id_miembro"));
            String id_proyecto = request.getParameter("id_proyecto");

            System.out.println(id_rol);
            System.out.println(id_proyecto);
            System.out.println(id_miembro);

            UsuarioControlador.agregarMiembro(id_proyecto, id_rol, id_miembro);

            request.setAttribute("accion", "ver_miembros");
            request.setAttribute("id_proyecto", id_proyecto);
            request.setAttribute("redireccion", redireccion);
            RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
            rd.forward(request, response);

        } else if (accion.equals("grabar_us")) {
            System.out.println("grabar us");

            String id_proyecto = request.getParameter("id_proyecto");
            String id_us = request.getParameter("id_us");
            int id_responsable = Integer.parseInt(request.getParameter("id_responsable"));
            String nombre_us = request.getParameter("nombre_us");
            String descripcion_us = request.getParameter("descripcion_us");
            int tiempo_estimado_us = Integer.parseInt(request.getParameter("tiempo_estimado_us"));
            int tiempo_trabajado_us = 0;
            int id_estado_backlog = Integer.parseInt(request.getParameter("id_estado_backlog"));
            int id_estado = 1;
            int id_prioridad = Integer.parseInt(request.getParameter("id_prioridad"));
            int story_points = Integer.parseInt(request.getParameter("story_points"));

            UserStory nuevoUs = new UserStory(id_proyecto, id_us, nombre_us, descripcion_us, tiempo_estimado_us, tiempo_trabajado_us, id_estado, id_estado_backlog, id_prioridad, id_responsable, story_points);

            //se agrega el nuevo user story
            UserstoryControlador.agregar(nuevoUs);
            System.out.println(nuevoUs);

            //se actualizan los datos del sprint
            int id_sprint_actual = Integer.parseInt(request.getParameter("id_sprint_actual"));
            Sprint sprintActual = new Sprint();
            sprintActual.setId_proyecto(id_proyecto);
            sprintActual.setId_sprint(id_sprint_actual);

            //se asignan los datos del sprint al us
            SprintControlador.asignarSprintUS(sprintActual);

            ////se actualiza la duracion y los storypoints estimados
            SprintControlador.actualizarSprint(sprintActual);

            request.setAttribute("accion", "ver_product_backlog");
            request.setAttribute("id_proyecto", id_proyecto);
            request.setAttribute("redireccion", redireccion);
            RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
            rd.forward(request, response);

        } else if (accion.equals("actualizar_us")) {
            String accion_redireccion = request.getParameter("accion_redireccion");
            System.out.println(accion_redireccion);
            System.out.println("actualizar us");

            String id_proyecto = request.getParameter("id_proyecto");
            String id_us = request.getParameter("id_us");
            int id_responsable = Integer.parseInt(request.getParameter("id_responsable"));
            String nombre_us = request.getParameter("nombre_us");
            String descripcion_us = request.getParameter("descripcion_us");
            int tiempo_estimado_us = Integer.parseInt(request.getParameter("tiempo_estimado_us"));
            int tiempo_trabajado_us = Integer.parseInt(request.getParameter("tiempo_trabajado_us"));
            int id_estado_backlog = Integer.parseInt(request.getParameter("id_estado_backlog"));
            int id_estado = Integer.parseInt(request.getParameter("id_estado"));
            int id_prioridad = Integer.parseInt(request.getParameter("id_prioridad"));
            int story_points = Integer.parseInt(request.getParameter("story_points"));

            UserStory nuevoUs = new UserStory(id_proyecto, id_us, nombre_us, descripcion_us, tiempo_estimado_us, tiempo_trabajado_us, id_estado, id_estado_backlog, id_prioridad, id_responsable, story_points);

            boolean actualizado = UserstoryControlador.modificar(nuevoUs);

            if (actualizado) {
                System.out.println("se ha modificado el us");
            } else {
                System.out.println("no se ha modificado el us");

            }

            //Se actualizan los datos del sprint
            String id_sprint_actual = request.getParameter("id_sprint_actual");
            System.out.println("id_sprint_actual MainServlet :" + id_sprint_actual);
            Sprint sprintActual = new Sprint();
            sprintActual.setId_proyecto(id_proyecto);
            sprintActual.setId_sprint(Integer.parseInt(id_sprint_actual));

            //se asigna el sprint a los US que no estan asignados
            SprintControlador.asignarSprintUS(sprintActual);

            //se actualiza la duracion y los storypoints estimados
            SprintControlador.actualizarSprint(sprintActual);

            request.setAttribute("accion", accion_redireccion);
            request.setAttribute("id_proyecto", id_proyecto);
            request.setAttribute("redireccion", redireccion);
            request.setAttribute("id_sprint_actual", id_sprint_actual);
            RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
            rd.forward(request, response);

        } else if (accion.equals("actualizar_miembro")) {
            System.out.println("actualizar miembro");

            String id_proyecto = request.getParameter("id_proyecto");
            int id_miembro = Integer.parseInt(request.getParameter("id_miembro"));
            int id_rol = Integer.parseInt(request.getParameter("id_rol"));

            boolean actualizado = UsuarioControlador.actualizarMiembro(id_proyecto, id_rol, id_miembro);

            if (actualizado) {
                System.out.println("se ha eliminado el miembro");
            } else {
                System.out.println("no se ha eliminado el miembro");
            }
            request.setAttribute("accion", "ver_miembros");
            request.setAttribute("id_proyecto", id_proyecto);
            request.setAttribute("redireccion", redireccion);
            RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
            rd.forward(request, response);

        } else if (accion.equals("iniciar_sprint")) {
            System.out.println("iniciar sprint");

            //se generan los datos para el nuevo sprint
            String id_proyecto = request.getParameter("id_proyecto");
            int duracion_estimada = Integer.parseInt(request.getParameter("duracion_estimada"));
            Sprint nuevoSprint = new Sprint();
            nuevoSprint.setId_proyecto(id_proyecto);
            nuevoSprint.setDuracion_estimada(duracion_estimada);

            //se inicia el nuevo sprint
            SprintControlador.iniciarSprint(nuevoSprint);

            //se obtiene el nuevo sprint
            nuevoSprint = SprintControlador.obtenerSprintActual(id_proyecto);

            //se asigan los us del nuevo sprint
            SprintControlador.asignarSprintUS(nuevoSprint);

            request.setAttribute("accion", "ver_product_backlog");
            request.setAttribute("id_proyecto", id_proyecto);
            request.setAttribute("redireccion", "productBacklog.jsp");
            RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
            rd.forward(request, response);

        } else if (accion.equals("finalizar_sprint")) {
            System.out.println("finalizar sprint");

            //se generan los datos para sprint a finzalizar
            String id_proyecto = request.getParameter("id_proyecto");

            Sprint sprintActual = SprintControlador.obtenerSprintActual(id_proyecto);

            //se mueven todos los US que no se han terminado al sprint backlog
            SprintControlador.moverUSBacklog(sprintActual);

            //se inicia el nuevo sprint
            SprintControlador.finalizarSprint(sprintActual);

            request.setAttribute("accion", "ver_product_backlog");
            request.setAttribute("id_proyecto", id_proyecto);
            request.setAttribute("redireccion", "productBacklog.jsp");
            RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
            rd.forward(request, response);

        }
    }

}
