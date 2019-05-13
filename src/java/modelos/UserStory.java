package modelos;

public class UserStory {
    
    private String id_proyecto;
    private String id_us;
    private String nombre;
    private String descripcion;
    private int tiempo_estimado;
    private int tiempo_trabajado;
    private int id_estados;
    private int id_estados_backlog;
    private String id_sprint;
    private int id_prio;

    public UserStory(String id_proyecto, String id_us, String nombre, 
            String descripcion, int tiempo_estimado, int id_estados_backlog) {
        this.id_proyecto = id_proyecto;
        this.id_us = id_us;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tiempo_estimado = tiempo_estimado;
        this.tiempo_trabajado = 0;              //se inicializa en 0
        this.id_estados_backlog = 5;            //Product Backlog 
        this.id_estados = 0;                    //se inicializa en 0
        this.id_sprint = null;                  //se inicializa en NULL
        this.id_prio = 0;                       //se inicializa en 0
    }

    public UserStory() {
    }

    public String getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(String id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public String getId_us() {
        return id_us;
    }

    public void setId_us(String id_us) {
        this.id_us = id_us;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTiempo_estimado() {
        return tiempo_estimado;
    }

    public void setTiempo_estimado(int tiempo_estimado) {
        this.tiempo_estimado = tiempo_estimado;
    }

    public int getTiempo_trabajado() {
        return tiempo_trabajado;
    }

    public void setTiempo_trabajado(int tiempo_trabajado) {
        this.tiempo_trabajado = tiempo_trabajado;
    }

    /*
    Estados en la base de datos
    1.  TO-DO
    2.  DOING
    3.  DONE
    6.  ELIMINADO
    */
    public int getId_estados() {
        return id_estados;
    }

    public void setId_estados(int id_estados) {
        this.id_estados = id_estados;
    }

    /*
    Estados en la base de datos
    4.  SPRINT
    5.  PRODUCT
    */
    public int getId_estados_backlog() {
        return id_estados_backlog;
    }
    
    public void setId_estados_backlog(int id_estado_backlog){
        this.id_estados_backlog = id_estado_backlog;
    }

    public String getId_sprint() {
        return id_sprint;
    }

    public void setId_sprint(String id_sprint) {
        this.id_sprint = id_sprint;
    }

    /*
    Prioridades en la base de datos
    1.  HIGH PRIO
    2.  MEDIUM PRIO
    3.  LOW PRIO
    */
    public int getId_prio() {
        return id_prio;
    }

    public void setId_prio(int id_prio) {
        this.id_prio = id_prio;
        this.id_estados = 1;
        this.id_estados_backlog = 4;
    }

    @Override
    public String toString() {
        return "UserStory{" + "id_proyecto=" + id_proyecto 
                + ", id_us=" + id_us + ", nombre=" + nombre
                + ", descripcion=" + descripcion + ", tiempo_estimado="
                + tiempo_estimado + ", tiempo_trabajado=" + tiempo_trabajado
                + ", id_estados=" + id_estados + ", id_estados_backlog="
                + id_estados_backlog + ", id_sprint=" + id_sprint
                +", id_prio=" + id_prio + '}';
    }
   
}