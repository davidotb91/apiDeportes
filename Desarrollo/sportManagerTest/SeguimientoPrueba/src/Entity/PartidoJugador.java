package Entity;
// Generated 24/03/2017 14:13:38 by Hibernate Tools 5.1.2.Final

import java.util.Date;

/**
 * PartidoJugador generated by hbm2java
 */
public class PartidoJugador implements java.io.Serializable {

	private Integer partId;
	private Fixture fixture;
	private Jugador jugador;
	private ParametroDetalle parametroDetalle;
	private Long partValor;
	private String audUsuCrea;
	private String audUsuModi;
	private Date audFechaModi;
	private Date audFechaCrea;

	public PartidoJugador() {
	}

	public PartidoJugador(Fixture fixture, Jugador jugador, ParametroDetalle parametroDetalle, Long partValor,
			String audUsuCrea, String audUsuModi, Date audFechaModi, Date audFechaCrea) {
		this.fixture = fixture;
		this.jugador = jugador;
		this.parametroDetalle = parametroDetalle;
		this.partValor = partValor;
		this.audUsuCrea = audUsuCrea;
		this.audUsuModi = audUsuModi;
		this.audFechaModi = audFechaModi;
		this.audFechaCrea = audFechaCrea;
	}

	public Integer getPartId() {
		return this.partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public Fixture getFixture() {
		return this.fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	public Jugador getJugador() {
		return this.jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public ParametroDetalle getParametroDetalle() {
		return this.parametroDetalle;
	}

	public void setParametroDetalle(ParametroDetalle parametroDetalle) {
		this.parametroDetalle = parametroDetalle;
	}

	public Long getPartValor() {
		return this.partValor;
	}

	public void setPartValor(Long partValor) {
		this.partValor = partValor;
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

	public Date getAudFechaModi() {
		return this.audFechaModi;
	}

	public void setAudFechaModi(Date audFechaModi) {
		this.audFechaModi = audFechaModi;
	}

	public Date getAudFechaCrea() {
		return this.audFechaCrea;
	}

	public void setAudFechaCrea(Date audFechaCrea) {
		this.audFechaCrea = audFechaCrea;
	}

}
