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
public class Proyecto {
    //atributos
    String id_proyecto;
    String nombre_proyecto;
    String descripcion_proyecto;
    String duedate;

    public Proyecto() {
    }
    
    public Proyecto(String id_proyecto, String nombre_proyecto, String descripcion_proyecto, String duedate) {
        this.id_proyecto = id_proyecto;
        this.nombre_proyecto = nombre_proyecto;
        this.descripcion_proyecto = descripcion_proyecto;
        this.duedate = duedate;
    }
    
     public String getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(String id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public String getNombre_proyecto() {
        return nombre_proyecto;
    }

    public void setNombre_proyecto(String nombre_proyecto) {
        this.nombre_proyecto = nombre_proyecto;
    }

    public String getDescripcion_proyecto() {
        return descripcion_proyecto;
    }

    public void setDescripcion_proyecto(String descripcion_proyecto) {
        this.descripcion_proyecto = descripcion_proyecto;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    
    
    @Override
    public String toString() {
        return "Proyecto{" + "id_proyecto=" + id_proyecto + ", nombre_proyecto=" + nombre_proyecto + ", descripcion_proyecto=" + descripcion_proyecto + '}';
    }
            
}//fin de la clase
