package entidades;
// Generated 20-mar-2017 23:16:03 by Hibernate Tools 3.5.0.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ParametroDetalle generated by hbm2java
 */
public class ParametroDetalle implements java.io.Serializable {

	private String padCodigo;
	private Parametro parametro;
	private String padDesCorta;
	private String padDesLarga;
	private int audUsuCrea;
	private int audUsuModi;
	private Date audFechaCrea;
	private Date audFechaModi;
	private Set docentesForParPadCodigo = new HashSet(0);
	private Set perfilRecursos = new HashSet(0);
	private Set temasForPadCodigo = new HashSet(0);
	private Set temasForPadeCodigoMaestria = new HashSet(0);
	private Set usuariosForParPadCodigo = new HashSet(0);
	private Set usuariosForPadCodigo = new HashSet(0);
	private Set estudiantesForPadCodigoFacultad = new HashSet(0);
	private Set docentesForPadCodigo = new HashSet(0);
	private Set estudiantesForPadCodigoProfesion = new HashSet(0);
	private Set estudiantesForPadCodigo = new HashSet(0);

	public ParametroDetalle() {
	}

	public ParametroDetalle(String padCodigo, Parametro parametro, String padDesCorta, String padDesLarga,
			int audUsuCrea, int audUsuModi, Date audFechaCrea, Date audFechaModi) {
		this.padCodigo = padCodigo;
		this.parametro = parametro;
		this.padDesCorta = padDesCorta;
		this.padDesLarga = padDesLarga;
		this.audUsuCrea = audUsuCrea;
		this.audUsuModi = audUsuModi;
		this.audFechaCrea = audFechaCrea;
		this.audFechaModi = audFechaModi;
	}

	public ParametroDetalle(String padCodigo, Parametro parametro, String padDesCorta, String padDesLarga,
			int audUsuCrea, int audUsuModi, Date audFechaCrea, Date audFechaModi, Set docentesForParPadCodigo,
			Set perfilRecursos, Set temasForPadCodigo, Set temasForPadeCodigoMaestria, Set usuariosForParPadCodigo,
			Set usuariosForPadCodigo, Set estudiantesForPadCodigoFacultad, Set docentesForPadCodigo,
			Set estudiantesForPadCodigoProfesion, Set estudiantesForPadCodigo) {
		this.padCodigo = padCodigo;
		this.parametro = parametro;
		this.padDesCorta = padDesCorta;
		this.padDesLarga = padDesLarga;
		this.audUsuCrea = audUsuCrea;
		this.audUsuModi = audUsuModi;
		this.audFechaCrea = audFechaCrea;
		this.audFechaModi = audFechaModi;
		this.docentesForParPadCodigo = docentesForParPadCodigo;
		this.perfilRecursos = perfilRecursos;
		this.temasForPadCodigo = temasForPadCodigo;
		this.temasForPadeCodigoMaestria = temasForPadeCodigoMaestria;
		this.usuariosForParPadCodigo = usuariosForParPadCodigo;
		this.usuariosForPadCodigo = usuariosForPadCodigo;
		this.estudiantesForPadCodigoFacultad = estudiantesForPadCodigoFacultad;
		this.docentesForPadCodigo = docentesForPadCodigo;
		this.estudiantesForPadCodigoProfesion = estudiantesForPadCodigoProfesion;
		this.estudiantesForPadCodigo = estudiantesForPadCodigo;
	}

	public String getPadCodigo() {
		return this.padCodigo;
	}

	public void setPadCodigo(String padCodigo) {
		this.padCodigo = padCodigo;
	}

	public Parametro getParametro() {
		return this.parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	public String getPadDesCorta() {
		return this.padDesCorta;
	}

	public void setPadDesCorta(String padDesCorta) {
		this.padDesCorta = padDesCorta;
	}

	public String getPadDesLarga() {
		return this.padDesLarga;
	}

	public void setPadDesLarga(String padDesLarga) {
		this.padDesLarga = padDesLarga;
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

	public Set getDocentesForParPadCodigo() {
		return this.docentesForParPadCodigo;
	}

	public void setDocentesForParPadCodigo(Set docentesForParPadCodigo) {
		this.docentesForParPadCodigo = docentesForParPadCodigo;
	}

	public Set getPerfilRecursos() {
		return this.perfilRecursos;
	}

	public void setPerfilRecursos(Set perfilRecursos) {
		this.perfilRecursos = perfilRecursos;
	}

	public Set getTemasForPadCodigo() {
		return this.temasForPadCodigo;
	}

	public void setTemasForPadCodigo(Set temasForPadCodigo) {
		this.temasForPadCodigo = temasForPadCodigo;
	}

	public Set getTemasForPadeCodigoMaestria() {
		return this.temasForPadeCodigoMaestria;
	}

	public void setTemasForPadeCodigoMaestria(Set temasForPadeCodigoMaestria) {
		this.temasForPadeCodigoMaestria = temasForPadeCodigoMaestria;
	}

	public Set getUsuariosForParPadCodigo() {
		return this.usuariosForParPadCodigo;
	}

	public void setUsuariosForParPadCodigo(Set usuariosForParPadCodigo) {
		this.usuariosForParPadCodigo = usuariosForParPadCodigo;
	}

	public Set getUsuariosForPadCodigo() {
		return this.usuariosForPadCodigo;
	}

	public void setUsuariosForPadCodigo(Set usuariosForPadCodigo) {
		this.usuariosForPadCodigo = usuariosForPadCodigo;
	}

	public Set getEstudiantesForPadCodigoFacultad() {
		return this.estudiantesForPadCodigoFacultad;
	}

	public void setEstudiantesForPadCodigoFacultad(Set estudiantesForPadCodigoFacultad) {
		this.estudiantesForPadCodigoFacultad = estudiantesForPadCodigoFacultad;
	}

	public Set getDocentesForPadCodigo() {
		return this.docentesForPadCodigo;
	}

	public void setDocentesForPadCodigo(Set docentesForPadCodigo) {
		this.docentesForPadCodigo = docentesForPadCodigo;
	}

	public Set getEstudiantesForPadCodigoProfesion() {
		return this.estudiantesForPadCodigoProfesion;
	}

	public void setEstudiantesForPadCodigoProfesion(Set estudiantesForPadCodigoProfesion) {
		this.estudiantesForPadCodigoProfesion = estudiantesForPadCodigoProfesion;
	}

	public Set getEstudiantesForPadCodigo() {
		return this.estudiantesForPadCodigo;
	}

	public void setEstudiantesForPadCodigo(Set estudiantesForPadCodigo) {
		this.estudiantesForPadCodigo = estudiantesForPadCodigo;
	}

}
