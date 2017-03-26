package entidades;
// Generated 20-mar-2017 23:16:03 by Hibernate Tools 3.5.0.Final

/**
 * VistaReporteEstudiantesId generated by hbm2java
 */
public class VistaReporteEstudiantesId implements java.io.Serializable {

	private String apellidos;
	private String nombres;
	private String descripcion;
	private String maestria;
	private String estadoMaestria;

	public VistaReporteEstudiantesId() {
	}

	public VistaReporteEstudiantesId(String apellidos, String nombres, String descripcion, String maestria,
			String estadoMaestria) {
		this.apellidos = apellidos;
		this.nombres = nombres;
		this.descripcion = descripcion;
		this.maestria = maestria;
		this.estadoMaestria = estadoMaestria;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMaestria() {
		return this.maestria;
	}

	public void setMaestria(String maestria) {
		this.maestria = maestria;
	}

	public String getEstadoMaestria() {
		return this.estadoMaestria;
	}

	public void setEstadoMaestria(String estadoMaestria) {
		this.estadoMaestria = estadoMaestria;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VistaReporteEstudiantesId))
			return false;
		VistaReporteEstudiantesId castOther = (VistaReporteEstudiantesId) other;

		return ((this.getApellidos() == castOther.getApellidos()) || (this.getApellidos() != null
				&& castOther.getApellidos() != null && this.getApellidos().equals(castOther.getApellidos())))
				&& ((this.getNombres() == castOther.getNombres()) || (this.getNombres() != null
						&& castOther.getNombres() != null && this.getNombres().equals(castOther.getNombres())))
				&& ((this.getDescripcion() == castOther.getDescripcion())
						|| (this.getDescripcion() != null && castOther.getDescripcion() != null
								&& this.getDescripcion().equals(castOther.getDescripcion())))
				&& ((this.getMaestria() == castOther.getMaestria()) || (this.getMaestria() != null
						&& castOther.getMaestria() != null && this.getMaestria().equals(castOther.getMaestria())))
				&& ((this.getEstadoMaestria() == castOther.getEstadoMaestria())
						|| (this.getEstadoMaestria() != null && castOther.getEstadoMaestria() != null
								&& this.getEstadoMaestria().equals(castOther.getEstadoMaestria())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getApellidos() == null ? 0 : this.getApellidos().hashCode());
		result = 37 * result + (getNombres() == null ? 0 : this.getNombres().hashCode());
		result = 37 * result + (getDescripcion() == null ? 0 : this.getDescripcion().hashCode());
		result = 37 * result + (getMaestria() == null ? 0 : this.getMaestria().hashCode());
		result = 37 * result + (getEstadoMaestria() == null ? 0 : this.getEstadoMaestria().hashCode());
		return result;
	}

}