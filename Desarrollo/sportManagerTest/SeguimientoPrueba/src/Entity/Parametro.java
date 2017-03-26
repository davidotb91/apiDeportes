package Entity;
// Generated 24/03/2017 14:13:38 by Hibernate Tools 5.1.2.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Parametro generated by hbm2java
 */
public class Parametro implements java.io.Serializable {

	private Integer paraId;
	private String paraDesCorta;
	private String paraDesLarga;
	private String paraNemonico;
	private String audUsuCrea;
	private String audUsuModi;
	private Date audFechaCrear;
	private Date audFechaModi;
	private Set<ParametroDetalle> parametroDetalles = new HashSet<ParametroDetalle>(0);

	public Parametro() {
	}

	public Parametro(String paraDesCorta, String paraDesLarga, String paraNemonico, String audUsuCrea,
			String audUsuModi, Date audFechaCrear, Date audFechaModi, Set<ParametroDetalle> parametroDetalles) {
		this.paraDesCorta = paraDesCorta;
		this.paraDesLarga = paraDesLarga;
		this.paraNemonico = paraNemonico;
		this.audUsuCrea = audUsuCrea;
		this.audUsuModi = audUsuModi;
		this.audFechaCrear = audFechaCrear;
		this.audFechaModi = audFechaModi;
		this.parametroDetalles = parametroDetalles;
	}

	public Integer getParaId() {
		return this.paraId;
	}

	public void setParaId(Integer paraId) {
		this.paraId = paraId;
	}

	public String getParaDesCorta() {
		return this.paraDesCorta;
	}

	public void setParaDesCorta(String paraDesCorta) {
		this.paraDesCorta = paraDesCorta;
	}

	public String getParaDesLarga() {
		return this.paraDesLarga;
	}

	public void setParaDesLarga(String paraDesLarga) {
		this.paraDesLarga = paraDesLarga;
	}

	public String getParaNemonico() {
		return this.paraNemonico;
	}

	public void setParaNemonico(String paraNemonico) {
		this.paraNemonico = paraNemonico;
	}

	public String getAudUsuCrea() {
		return this.audUsuCrea;
	}

	public void setAudUsuCrea(String audUsuCrea) {
		this.audUsuCrea = audUsuCrea;
	}

	public String getAudUsuModi() {
		return this.audUsuModi;
	}

	public void setAudUsuModi(String audUsuModi) {
		this.audUsuModi = audUsuModi;
	}

	public Date getAudFechaCrear() {
		return this.audFechaCrear;
	}

	public void setAudFechaCrear(Date audFechaCrear) {
		this.audFechaCrear = audFechaCrear;
	}

	public Date getAudFechaModi() {
		return this.audFechaModi;
	}

	public void setAudFechaModi(Date audFechaModi) {
		this.audFechaModi = audFechaModi;
	}

	public Set<ParametroDetalle> getParametroDetalles() {
		return this.parametroDetalles;
	}

	public void setParametroDetalles(Set<ParametroDetalle> parametroDetalles) {
		this.parametroDetalles = parametroDetalles;
	}

}