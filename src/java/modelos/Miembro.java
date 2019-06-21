/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

/**
 *
 * @author user
 */
public class Miembro {
    private String id_proyecto;
    private int id_usuario;
    private int id_rol;
    private String nombre_proyecto;
    private String nombre_usuario;
    private String nombre_rol;

    public Miembro() {
    }

    public Miembro(String id_proyecto, int id_usuario, int id_rol,String nombre_proyecto, String nombre_usuario, String nombre_rol) {
        this.id_proyecto = id_proyecto;
        this.id_usuario = id_usuario;
        this.id_rol = id_rol;
        this.nombre_proyecto = nombre_proyecto;
        this.nombre_usuario = nombre_usuario;
        this.nombre_rol = nombre_rol;
    }

    public String getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(String id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public String getNombre_proyecto() {
        return nombre_proyecto;
    }

    public void setNombre_proyecto(String nombre_proyecto) {
        this.nombre_proyecto = nombre_proyecto;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getNombre_rol() {
        return nombre_rol;
    }

    public void setNombre_rol(String nombre_rol) {
        this.nombre_rol = nombre_rol;
    }

    @Override
    public String toString() {
        return "Miembro{" + "id_proyecto=" + id_proyecto + ", id_usuario=" + id_usuario + ", id_rol=" + id_rol + ", nombre_proyecto=" + nombre_proyecto + ", nombre_usuario=" + nombre_usuario + ", nombre_rol=" + nombre_rol + '}';
    }
    
    
    
}
