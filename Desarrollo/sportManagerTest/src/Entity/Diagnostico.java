package Entity;
// Generated 15-jul-2014 19:36:51 by Hibernate Tools 3.2.1.GA

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Transient;
import org.hibernate.Hibernate;

/**
 * Diagnostico generated by hbm2java
 */
public class Diagnostico implements java.io.Serializable {

    private int diagnosticoid;
    private String titulo;
    private String descripcion;
    private Set consultas = new HashSet(0);
     @Transient
    private Integer position;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
   

    public Diagnostico() {
    }

    public Diagnostico(int diagnosticoid, String titulo, String descripcion) {
        this.diagnosticoid = diagnosticoid;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }
    public Diagnostico(int diagnosticoid, String titulo, String descripcion,Set consultas) {
        this.diagnosticoid = diagnosticoid;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.consultas = consultas;
    }

  

    public int getDiagnosticoid() {
        return this.diagnosticoid;
    }

    public void setDiagnosticoid(int diagnosticoid) {
        this.diagnosticoid = diagnosticoid;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
     public Set getConsultas() {
        return this.consultas;
    }
    
    public void setConsultas(Set consultas) {
        this.consultas = consultas;
    }

    @Override
    public String toString() {
        return titulo;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null || !(o instanceof Diagnostico)) {
            return false;
        }
        if (o == this) {
            return true;
        }

        return diagnosticoid == ((Diagnostico) o).getDiagnosticoid();
    }
}
