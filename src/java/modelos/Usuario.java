package modelos;

public class Usuario {
	private String usuario;
	private String nombre;
	private int cedula;
	private String email;
	private String password;
        private boolean activo;

	public Usuario (String user, String name, int cedula, String email, String password){
		this.usuario=user;
		this.nombre=name;
		this.cedula=cedula;
		this.email=email;
		this.password=password;
	}

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

        public Usuario() {
        }
   	
	public String encryptpass () {
		String pass = null;
		return pass;
	}

	/**
	 * @return the Usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param Usuario the Usuario to set
	 */
	public void setUsuario(String Usuario) {
		this.usuario = Usuario;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the cedula
	 */
	public int getCedula() {
		return cedula;
	}

	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

    @Override
    public String toString() {
        return "Usuario{" + "usuario=" + usuario + ", nombre=" + nombre + ", cedula=" + cedula + ", email=" + email + ", password=" + password + '}';
    }
	
	
}
