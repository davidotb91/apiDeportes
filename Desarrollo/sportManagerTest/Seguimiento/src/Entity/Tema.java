package Entity;
// default package
// Generated 28/12/2014 11:11:15 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.Transient;

/**
 * Tema generated by hbm2java
 */
public class Tema implements java.io.Serializable {

	private int teId;
	private ParametroDetalle parametroDetalle;
	private Estudiante estudiante;
	private String temDescripcion;
	private Date temFechaPresentacion;
	private Date temFechaMaximaAprobacion;
	private String temObservarcharciones;
	private double temNotaFinal;
	private int audUsuCrea;
	private int audUsuModi;
	private Date audFechaCrea;
	private Date audFechaModi;
	private Docente docente;
	private ParametroDetalle parametroDetalleMaestria;
	private Date temFechaAprobacion;

	public Tema() {
	}

	public Tema(int teId, ParametroDetalle parametroDetalle,
			Estudiante estudiante, String temDescripcion,
			Date temFechaPresentacion, Date temFechaMaximaAprobacion,
			String temObservarcharciones, double temNotaFinal, int audUsuCrea,
			int audUsuModi, Date audFechaCrea, Date audFechaModi, Docente docente, ParametroDetalle parametroDetalleMaestria, Date temFechaAprobacion ) {
		this.teId = teId;
		this.parametroDetalle = parametroDetalle;
		this.estudiante = estudiante;
		this.temDescripcion = temDescripcion;
		this.temFechaPresentacion = temFechaPresentacion;
		this.temFechaMaximaAprobacion = temFechaMaximaAprobacion;
		this.temObservarcharciones = temObservarcharciones;
		this.temNotaFinal = temNotaFinal;
		this.audUsuCrea = audUsuCrea;
		this.audUsuModi = audUsuModi;
		this.audFechaCrea = audFechaCrea;
		this.audFechaModi = audFechaModi;
		this.docente = docente;
		this.parametroDetalleMaestria = parametroDetalleMaestria;
		this.temFechaAprobacion = temFechaAprobacion;
	}

	
    public ParametroDetalle getParametroDetalleMaestria() {
		return parametroDetalleMaestria;
	}

	public void setParametroDetalleMaestria(
			ParametroDetalle parametroDetalleMaestria) {
		this.parametroDetalleMaestria = parametroDetalleMaestria;
	}


	@Transient
    private Integer position;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
	public int getTeId() {
		return this.teId;
	}

	public void setTeId(int teId) {
		this.teId = teId;
	}

	public ParametroDetalle getParametroDetalle() {
		return this.parametroDetalle;
	}

	public void setParametroDetalle(ParametroDetalle parametroDetalle) {
		this.parametroDetalle = parametroDetalle;
	}

	public Estudiante getEstudiante() {
		return this.estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public String getTemDescripcion() {
		return this.temDescripcion;
	}

	public void setTemDescripcion(String temDescripcion) {
		this.temDescripcion = temDescripcion;
	}

	public Date getTemFechaPresentacion() {
		return this.temFechaPresentacion;
	}

	public void setTemFechaPresentacion(Date temFechaPresentacion) {
		this.temFechaPresentacion = temFechaPresentacion;
	}

	public Date getTemFechaMaximaAprobacion() {
		return this.temFechaMaximaAprobacion;
	}

	public void setTemFechaMaximaAprobacion(Date temFechaMaximaAprobacion) {
		this.temFechaMaximaAprobacion = temFechaMaximaAprobacion;
	}

	public String getTemObservarcharciones() {
		return this.temObservarcharciones;
	}

	public void setTemObservarcharciones(String temObservarcharciones) {
		this.temObservarcharciones = temObservarcharciones;
	}

	public double getTemNotaFinal() {
		return this.temNotaFinal;
	}

	public void setTemNotaFinal(double temNotaFinal) {
		this.temNotaFinal = temNotaFinal;
	}

	public int getAudUsuCrea() {
		return this.audUsuCrea;
	}

	public void setAudUsuCrea(int audUsuCrea) {
		this.audUsuCrea = audUsuCrea;
	}

	public int getAudUsuModi() {
		return this.audUsuModi;
	}

	public void setAudUsuModi(int audUsuModi) {
		this.audUsuModi = audUsuModi;
	}

	public Date getAudFechaCrea() {
		return this.audFechaCrea;
	}

	public void setAudFechaCrea(Date audFechaCrea) {
		this.audFechaCrea = audFechaCrea;
	}

	public Date getAudFechaModi() {
		return this.audFechaModi;
	}

	public void setAudFechaModi(Date audFechaModi) {
		this.audFechaModi = audFechaModi;
	}
	
	@Override
    public String toString() {
        return this.temDescripcion;
    }
	
	 @Override
	    public boolean equals(Object obj) {
	        if (obj == null || !(obj instanceof Tema)) {
	            return false;
	        }
	        if (obj == this) {
	            return true;
	        }
	        return this.teId == ((Tema) obj).getTeId();
	    }
	 
	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}
	
	public Date getTemFechaAprobacion() {
		return temFechaAprobacion;
	}

	public void setTemFechaAprobacion(Date temFechaAprobacion) {
		this.temFechaAprobacion = temFechaAprobacion;
	}

}
