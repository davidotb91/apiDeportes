package entidades;
// Generated 20-mar-2017 23:16:03 by Hibernate Tools 3.5.0.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Tema generated by hbm2java
 */
public class Tema implements java.io.Serializable {

	private int teId;
	private ParametroDetalle parametroDetalleByPadeCodigoMaestria;
	private ParametroDetalle parametroDetalleByPadCodigo;
	private Docente docente;
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
	private Date temFechaAprobacion;
	private Set seguimientoTesises = new HashSet(0);

	public Tema() {
	}

	public Tema(int teId, ParametroDetalle parametroDetalleByPadCodigo, Docente docente, Estudiante estudiante,
			String temDescripcion, Date temFechaPresentacion, Date temFechaMaximaAprobacion,
			String temObservarcharciones, double temNotaFinal, int audUsuCrea, int audUsuModi, Date audFechaCrea,
			Date audFechaModi) {
		this.teId = teId;
		this.parametroDetalleByPadCodigo = parametroDetalleByPadCodigo;
		this.docente = docente;
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
	}

	public Tema(int teId, ParametroDetalle parametroDetalleByPadeCodigoMaestria,
			ParametroDetalle parametroDetalleByPadCodigo, Docente docente, Estudiante estudiante, String temDescripcion,
			Date temFechaPresentacion, Date temFechaMaximaAprobacion, String temObservarcharciones, double temNotaFinal,
			int audUsuCrea, int audUsuModi, Date audFechaCrea, Date audFechaModi, Date temFechaAprobacion,
			Set seguimientoTesises) {
		this.teId = teId;
		this.parametroDetalleByPadeCodigoMaestria = parametroDetalleByPadeCodigoMaestria;
		this.parametroDetalleByPadCodigo = parametroDetalleByPadCodigo;
		this.docente = docente;
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
		this.temFechaAprobacion = temFechaAprobacion;
		this.seguimientoTesises = seguimientoTesises;
	}

	public int getTeId() {
		return this.teId;
	}

	public void setTeId(int teId) {
		this.teId = teId;
	}

	public ParametroDetalle getParametroDetalleByPadeCodigoMaestria() {
		return this.parametroDetalleByPadeCodigoMaestria;
	}

	public void setParametroDetalleByPadeCodigoMaestria(ParametroDetalle parametroDetalleByPadeCodigoMaestria) {
		this.parametroDetalleByPadeCodigoMaestria = parametroDetalleByPadeCodigoMaestria;
	}

	public ParametroDetalle getParametroDetalleByPadCodigo() {
		return this.parametroDetalleByPadCodigo;
	}

	public void setParametroDetalleByPadCodigo(ParametroDetalle parametroDetalleByPadCodigo) {
		this.parametroDetalleByPadCodigo = parametroDetalleByPadCodigo;
	}

	public Docente getDocente() {
		return this.docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
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

	public Date getTemFechaAprobacion() {
		return this.temFechaAprobacion;
	}

	public void setTemFechaAprobacion(Date temFechaAprobacion) {
		this.temFechaAprobacion = temFechaAprobacion;
	}

	public Set getSeguimientoTesises() {
		return this.seguimientoTesises;
	}

	public void setSeguimientoTesises(Set seguimientoTesises) {
		this.seguimientoTesises = seguimientoTesises;
	}

}