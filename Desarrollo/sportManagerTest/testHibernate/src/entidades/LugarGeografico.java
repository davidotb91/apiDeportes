package entidades;
// Generated 20-mar-2017 23:16:03 by Hibernate Tools 3.5.0.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * LugarGeografico generated by hbm2java
 */
public class LugarGeografico implements java.io.Serializable {

	private int idLugar;
	private LugarGeografico lugarGeografico;
	private String desCorta;
	private String desLarga;
	private int nivel;
	private int audUsuCrea;
	private int audUsuModi;
	private Date audFechaCrea;
	private Date audFechaModi;
	private Set estudiantes = new HashSet(0);
	private Set lugarGeograficos = new HashSet(0);

	public LugarGeografico() {
	}

	public LugarGeografico(int idLugar, LugarGeografico lugarGeografico, String desCorta, String desLarga, int nivel,
			int audUsuCrea, int audUsuModi, Date audFechaCrea, Date audFechaModi) {
		this.idLugar = idLugar;
		this.lugarGeografico = lugarGeografico;
		this.desCorta = desCorta;
		this.desLarga = desLarga;
		this.nivel = nivel;
		this.audUsuCrea = audUsuCrea;
		this.audUsuModi = audUsuModi;
		this.audFechaCrea = audFechaCrea;
		this.audFechaModi = audFechaModi;
	}

	public LugarGeografico(int idLugar, LugarGeografico lugarGeografico, String desCorta, String desLarga, int nivel,
			int audUsuCrea, int audUsuModi, Date audFechaCrea, Date audFechaModi, Set estudiantes,
			Set lugarGeograficos) {
		this.idLugar = idLugar;
		this.lugarGeografico = lugarGeografico;
		this.desCorta = desCorta;
		this.desLarga = desLarga;
		this.nivel = nivel;
		this.audUsuCrea = audUsuCrea;
		this.audUsuModi = audUsuModi;
		this.audFechaCrea = audFechaCrea;
		this.audFechaModi = audFechaModi;
		this.estudiantes = estudiantes;
		this.lugarGeograficos = lugarGeograficos;
	}

	public int getIdLugar() {
		return this.idLugar;
	}

	public void setIdLugar(int idLugar) {
		this.idLugar = idLugar;
	}

	public LugarGeografico getLugarGeografico() {
		return this.lugarGeografico;
	}

	public void setLugarGeografico(LugarGeografico lugarGeografico) {
		this.lugarGeografico = lugarGeografico;
	}

	public String getDesCorta() {
		return this.desCorta;
	}

	public void setDesCorta(String desCorta) {
		this.desCorta = desCorta;
	}

	public String getDesLarga() {
		return this.desLarga;
	}

	public void setDesLarga(String desLarga) {
		this.desLarga = desLarga;
	}

	public int getNivel() {
		return this.nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
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

	public Set getEstudiantes() {
		return this.estudiantes;
	}

	public void setEstudiantes(Set estudiantes) {
		this.estudiantes = estudiantes;
	}

	public Set getLugarGeograficos() {
		return this.lugarGeograficos;
	}

	public void setLugarGeograficos(Set lugarGeograficos) {
		this.lugarGeograficos = lugarGeograficos;
	}

}