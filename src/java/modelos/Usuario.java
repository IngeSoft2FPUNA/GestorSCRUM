package modelos;

public class Usuario {

    int id_usuario;
    private String usuario;
    private String nombre;
    private int cedula;
    private String email;
    private String password;
    private int rol_sistema;
    private boolean activo;

    public Usuario(String user, String name, int cedula, 
            String email, String password, int rol_sistema) {
        this.usuario = user;
        this.nombre = name;
        this.cedula = cedula;
        this.email = email;
        this.password = password;
        this.rol_sistema = rol_sistema;
    }

    public Usuario(int id_usuario, String usuario, String nombre, 
            int cedula, String email, String password, boolean activo) {
        this.id_usuario = id_usuario;
        this.usuario = usuario;
        this.nombre = nombre;
        this.cedula = cedula;
        this.email = email;
        this.password = password;
        this.activo = activo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Usuario() {
    }

    public String encryptpass() {
        String pass = null;
        return pass;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String Usuario) {
        this.usuario = Usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRol_sistema() {
        return rol_sistema;
    }

    public void setRol_sistema(int rol_sistema) {
        this.rol_sistema = rol_sistema;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id_usuario=" + id_usuario + ", usuario=" + usuario + ", nombre=" + nombre + ", cedula=" + cedula + ", email=" + email + ", password=" + password + ", rol_sistema=" + rol_sistema + ", activo=" + activo + '}';
    }



}
