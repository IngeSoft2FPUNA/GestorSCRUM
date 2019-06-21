/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import controladores.ProyectoControlador;
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
import modelos.Miembro;
import modelos.UserStory;
import modelos.Usuario;

/**
 *
 * @author user
 */
@WebServlet(name = "MainServlet", urlPatterns = {"/MainServlet"})
public class MainServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        String id_proyecto = request.getParameter("id_proyecto");
        String redireccion = request.getParameter("redireccion");

        if (accion.equals("ver_product_backlog")) {

            System.out.println("ver product backlog");
            redireccion = "productBacklog.jsp";
            ArrayList<UserStory> listaUs = UserstoryControlador.buscarUSBacklog(id_proyecto);
            request.setAttribute("listaUs", listaUs);
            RequestDispatcher rd = request.getRequestDispatcher(redireccion);
            rd.forward(request, response);
            
        } else if (accion.equals("ver_sprint_backlog")) {
            System.out.println("ver sprint backlog");
            redireccion = "sprintBacklog.jsp";
            ArrayList<UserStory> listaUs = UserstoryControlador.buscarUSSprint(id_proyecto);
            String nombre_responsable = "";
           
            for (int i = 0; i < listaUs.size(); i++) {
                nombre_responsable = UsuarioControlador.buscarUsuario(listaUs.get(i).getId_responsable()).getNombre();
                
                listaUs.get(i).setNombre_responsable(nombre_responsable);
            }
            
            request.setAttribute("listaUs", listaUs);
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
            RequestDispatcher rd = request.getRequestDispatcher(redireccion);
            rd.forward(request, response);
        
        } else if (accion.equals("editar_us")) {
            System.out.println("editar us");
            redireccion = "editarUS.jsp";
            String id_us = request.getParameter("id_us");
            
            UserStory userStoryTemp = UserstoryControlador.getUS(id_us,id_proyecto);
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
            }else{
                System.out.println("no se ha eliminado el usx");
            
            }
     
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
            }else{
                System.out.println("no se ha modificado el miembro");
            
            }
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

            Usuario usuario = new Usuario(login_usuario, "", 0, "", password_usuario);
            if (UsuarioControlador.validarAcceso(usuario, request) != null) {
                acceso = "true";
                // request.setAttribute("listaUsuarios", listaUsuarios);
                RequestDispatcher rd = request.getRequestDispatcher(redireccion);
                rd.forward(request, response);

            }else{
                System.out.println("credenciales invalidas");
            }
        }
        else if(accion.equals("grabar_miembro")){
            System.out.println("grabar miembro");
            
            int id_rol = Integer.parseInt(request.getParameter("id_rol"));
            int id_miembro = Integer.parseInt(request.getParameter("id_miembro"));
            String id_proyecto = request.getParameter("id_proyecto");

            System.out.println(id_rol);
            System.out.println(id_proyecto);
            System.out.println(id_miembro);
            
            UsuarioControlador.agregarMiembro(id_proyecto,id_rol,id_miembro);
            
            request.setAttribute("accion", "ver_miembros");
            request.setAttribute("id_proyecto", id_proyecto);
            request.setAttribute("redireccion", redireccion);
            RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
            rd.forward(request, response);
            
        }
        else if(accion.equals("grabar_us")){
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
            
            UserStory nuevoUs = new UserStory(id_proyecto, id_us, nombre_us, descripcion_us, tiempo_estimado_us, tiempo_trabajado_us,id_estado,id_estado_backlog, id_prioridad, id_responsable);
            
            UserstoryControlador.agregar(nuevoUs);
            System.out.println(nuevoUs);
            
            request.setAttribute("accion", "ver_product_backlog");
            request.setAttribute("id_proyecto", id_proyecto);
            request.setAttribute("redireccion", redireccion);
            RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
            rd.forward(request, response);
            
        }
        else if(accion.equals("actualizar_us")){
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
            
            UserStory nuevoUs = new UserStory(id_proyecto, id_us, nombre_us, descripcion_us, tiempo_estimado_us, tiempo_trabajado_us,id_estado,id_estado_backlog, id_prioridad, id_responsable);
            
            boolean actualizado = UserstoryControlador.modificar(nuevoUs);
            
            if (actualizado) {
                System.out.println("se ha modificado el us");
            }else{
                System.out.println("no se ha modificado el us");
            
            }
            request.setAttribute("accion", accion_redireccion);
            request.setAttribute("id_proyecto", id_proyecto);
            request.setAttribute("redireccion", redireccion);
            RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
            rd.forward(request, response);     
                        
        }
        else if(accion.equals("actualizar_miembro")){
            System.out.println("actualizar miembro");

            String id_proyecto = request.getParameter("id_proyecto");           
            int id_miembro = Integer.parseInt(request.getParameter("id_miembro"));
            int id_rol = Integer.parseInt(request.getParameter("id_rol"));
            
            
            boolean actualizado = UsuarioControlador.actualizarMiembro(id_proyecto, id_rol, id_miembro);
            
            if (actualizado) {
                System.out.println("se ha eliminado el miembro");
            }else{
                System.out.println("no se ha eliminado el miembro");            
            }
            request.setAttribute("accion", "ver_miembros");
            request.setAttribute("id_proyecto", id_proyecto);
            request.setAttribute("redireccion", redireccion);
            RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
            rd.forward(request, response);            
            
                        
        }
    }

}
