package Entity;
// Generated 24/03/2017 14:13:38 by Hibernate Tools 5.1.2.Final

/**
 * PerfilRecursoId generated by hbm2java
 */
public class PerfilRecursoId implements java.io.Serializable {

	private int padeId;
	private int spoPadeId;

	public PerfilRecursoId() {
	}

	public PerfilRecursoId(int padeId, int spoPadeId) {
		this.padeId = padeId;
		this.spoPadeId = spoPadeId;
	}

	public int getPadeId() {
		return this.padeId;
	}

	public void setPadeId(int padeId) {
		this.padeId = padeId;
	}

	public int getSpoPadeId() {
		return this.spoPadeId;
	}

	public void setSpoPadeId(int spoPadeId) {
		this.spoPadeId = spoPadeId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PerfilRecursoId))
			return false;
		PerfilRecursoId castOther = (PerfilRecursoId) other;

		return (this.getPadeId() == castOther.getPadeId()) && (this.getSpoPadeId() == castOther.getSpoPadeId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getPadeId();
		result = 37 * result + this.getSpoPadeId();
		return result;
	}

}
