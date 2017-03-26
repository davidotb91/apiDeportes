package Entity;
// Generated 15-jul-2014 19:36:51 by Hibernate Tools 3.2.1.GA

/**
 * Detalle generated by hbm2java
 */
public class Detalle implements java.io.Serializable {

    private int detalleid;
    private Cabecerafactura cabecerafactura;
    private String concepto;
    private String unidades;
    private String importe;

    public Detalle() {
    }

    public Detalle(int detalleid, Cabecerafactura cabecerafactura, String concepto, String unidades, String importe) {
        this.detalleid = detalleid;
        this.cabecerafactura = cabecerafactura;
        this.concepto = concepto;
        this.unidades = unidades;
        this.importe = importe;
    }

    public int getDetalleid() {
        return this.detalleid;
    }

    public void setDetalleid(int detalleid) {
        this.detalleid = detalleid;
    }

    public Cabecerafactura getCabecerafactura() {
        return this.cabecerafactura;
    }

    public void setCabecerafactura(Cabecerafactura cabecerafactura) {
        this.cabecerafactura = cabecerafactura;
    }

    public String getConcepto() {
        return this.concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getUnidades() {
        return this.unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }

    public String getImporte() {
        return this.importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    @Override
    public String toString() {
        return detalleid + " " + concepto;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Detalle)) {
            return false;
        }
        Detalle detalle = (Detalle) o;
        return (this.detalleid == detalle.detalleid);

    }
}
