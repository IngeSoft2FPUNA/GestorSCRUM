
package modelos;

public class Sprint {
    
    int id_sprint;
    String id_proyecto;
    int duracion_estimada;
    int duracion_real;
    String fecha_inicio;
    String fecha_fin_estimada;
    String fecha_fin_real;
    int story_points_estimados;
    int story_points_realizados;    
    int estado_sprint;

    public Sprint() {
    }

    public Sprint(int id_sprint, String id_proyecto, int duracion_estimada, int duracion_real, String fecha_inicio, String fecha_fin_estimada, String fecha_fin_real, int story_points_estimados, int story_points_realizados, int estado_sprint) {
        this.id_sprint = id_sprint;
        this.id_proyecto = id_proyecto;
        this.duracion_estimada = duracion_estimada;
        this.duracion_real = duracion_real;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin_estimada = fecha_fin_estimada;
        this.fecha_fin_real = fecha_fin_real;
        this.story_points_estimados = story_points_estimados;
        this.story_points_realizados = story_points_realizados;
        this.estado_sprint = estado_sprint;
    }

    public int getId_sprint() {
        return id_sprint;
    }

    public void setId_sprint(int id_sprint) {
        this.id_sprint = id_sprint;
    }

    public String getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(String id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public int getDuracion_estimada() {
        return duracion_estimada;
    }

    public void setDuracion_estimada(int duracion_estimada) {
        this.duracion_estimada = duracion_estimada;
    }

    public int getDuracion_real() {
        return duracion_real;
    }

    public void setDuracion_real(int duracion_real) {
        this.duracion_real = duracion_real;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin_estimada() {
        return fecha_fin_estimada;
    }

    public void setFecha_fin_estimada(String fecha_fin_estimada) {
        this.fecha_fin_estimada = fecha_fin_estimada;
    }

    public String getFecha_fin_real() {
        return fecha_fin_real;
    }

    public void setFecha_fin_real(String fecha_fin_real) {
        this.fecha_fin_real = fecha_fin_real;
    }

    public int getStory_points_estimados() {
        return story_points_estimados;
    }

    public void setStory_points_estimados(int story_points_estimados) {
        this.story_points_estimados = story_points_estimados;
    }

    public int getStory_points_realizados() {
        return story_points_realizados;
    }

    public void setStory_points_realizados(int story_points_realizados) {
        this.story_points_realizados = story_points_realizados;
    }

    public int getEstado_sprint() {
        return estado_sprint;
    }

    public void setEstado_sprint(int estado_sprint) {
        this.estado_sprint = estado_sprint;
    }

    @Override
    public String toString() {
        return "Sprint{" + "id_sprint=" + id_sprint + ", id_proyecto=" + id_proyecto + ", duracion_estimada=" + duracion_estimada + ", duracion_real=" + duracion_real + ", fecha_inicio=" + fecha_inicio + ", fecha_fin_estimada=" + fecha_fin_estimada + ", fecha_fin_real=" + fecha_fin_real + ", story_points_estimados=" + story_points_estimados + ", story_points_realizados=" + story_points_realizados + ", estado_sprint=" + estado_sprint + '}';
    }
    
    
    
    
}
