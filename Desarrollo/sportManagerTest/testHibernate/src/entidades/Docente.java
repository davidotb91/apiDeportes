package entidades;
// Generated 20-mar-2017 23:16:03 by Hibernate Tools 3.5.0.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Docente generated by hbm2java
 */
public class Docente implements java.io.Serializable {

	private int docId;
	private ParametroDetalle parametroDetalleByParPadCodigo;
	private ParametroDetalle parametroDetalleByPadCodigo;
	private String docNombres;
	private String docApellidos;
	private String docCedula;
	private String docCelular;
	private String docTelefono;
	private String docDireccion;
	private int audUsuCrea;
	private int audUsuModi;
	private Date audFechaCrea;
	private Date audFechaModi;
	private String docLogin;
	private String docPassword;
	private Set usuarios = new HashSet(0);
	private Set temas = new HashSet(0);
	private Set seguimientoTesises = new HashSet(0);

	public Docente() {
	}

	public Docente(int docId, ParametroDetalle parametroDetalleByParPadCodigo,
			ParametroDetalle parametroDetalleByPadCodigo, String docNombres, String docApellidos, String docCedula,
			String docCelular, String docTelefono, String docDireccion, int audUsuCrea, int audUsuModi,
			Date audFechaCrea, Date audFechaModi) {
		this.docId = docId;
		this.parametroDetalleByParPadCodigo = parametroDetalleByParPadCodigo;
		this.parametroDetalleByPadCodigo = parametroDetalleByPadCodigo;
		this.docNombres = docNombres;
		this.docApellidos = docApellidos;
		this.docCedula = docCedula;
		this.docCelular = docCelular;
		this.docTelefono = docTelefono;
		this.docDireccion = docDireccion;
		this.audUsuCrea = audUsuCrea;
		this.audUsuModi = audUsuModi;
		this.audFechaCrea = audFechaCrea;
		this.audFechaModi = audFechaModi;
	}

	public Docente(int docId, ParametroDetalle parametroDetalleByParPadCodigo,
			ParametroDetalle parametroDetalleByPadCodigo, String docNombres, String docApellidos, String docCedula,
			String docCelular, String docTelefono, String docDireccion, int audUsuCrea, int audUsuModi,
			Date audFechaCrea, Date audFechaModi, String docLogin, String docPassword, Set usuarios, Set temas,
			Set seguimientoTesises) {
		this.docId = docId;
		this.parametroDetalleByParPadCodigo = parametroDetalleByParPadCodigo;
		this.parametroDetalleByPadCodigo = parametroDetalleByPadCodigo;
		this.docNombres = docNombres;
		this.docApellidos = docApellidos;
		this.docCedula = docCedula;
		this.docCelular = docCelular;
		this.docTelefono = docTelefono;
		this.docDireccion = docDireccion;
		this.audUsuCrea = audUsuCrea;
		this.audUsuModi = audUsuModi;
		this.audFechaCrea = audFechaCrea;
		this.audFechaModi = audFechaModi;
		this.docLogin = docLogin;
		this.docPassword = docPassword;
		this.usuarios = usuarios;
		this.temas = temas;
		this.seguimientoTesises = seguimientoTesises;
	}

	public int getDocId() {
		return this.docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public ParametroDetalle getParametroDetalleByParPadCodigo() {
		return this.parametroDetalleByParPadCodigo;
	}

	public void setParametroDetalleByParPadCodigo(ParametroDetalle parametroDetalleByParPadCodigo) {
		this.parametroDetalleByParPadCodigo = parametroDetalleByParPadCodigo;
	}

	public ParametroDetalle getParametroDetalleByPadCodigo() {
		return this.parametroDetalleByPadCodigo;
	}

	public void setParametroDetalleByPadCodigo(ParametroDetalle parametroDetalleByPadCodigo) {
		this.parametroDetalleByPadCodigo = parametroDetalleByPadCodigo;
	}

	public String getDocNombres() {
		return this.docNombres;
	}

	public void setDocNombres(String docNombres) {
		this.docNombres = docNombres;
	}

	public String getDocApellidos() {
		return this.docApellidos;
	}

	public void setDocApellidos(String docApellidos) {
		this.docApellidos = docApellidos;
	}

	public String getDocCedula() {
		return this.docCedula;
	}

	public void setDocCedula(String docCedula) {
		this.docCedula = docCedula;
	}

	public String getDocCelular() {
		return this.docCelular;
	}

	public void setDocCelular(String docCelular) {
		this.docCelular = docCelular;
	}

	public String getDocTelefono() {
		return this.docTelefono;
	}

	public void setDocTelefono(String docTelefono) {
		this.docTelefono = docTelefono;
	}

	public String getDocDireccion() {
		return this.docDireccion;
	}

	public void setDocDireccion(String docDireccion) {
		this.docDireccion = docDireccion;
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

	public String getDocLogin() {
		return this.docLogin;
	}

	public void setDocLogin(String docLogin) {
		this.docLogin = docLogin;
	}

	public String getDocPassword() {
		return this.docPassword;
	}

	public void setDocPassword(String docPassword) {
		this.docPassword = docPassword;
	}

	public Set getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Set usuarios) {
		this.usuarios = usuarios;
	}

	public Set getTemas() {
		return this.temas;
	}

	public void setTemas(Set temas) {
		this.temas = temas;
	}

	public Set getSeguimientoTesises() {
		return this.seguimientoTesises;
	}

	public void setSeguimientoTesises(Set seguimientoTesises) {
		this.seguimientoTesises = seguimientoTesises;
	}

}
