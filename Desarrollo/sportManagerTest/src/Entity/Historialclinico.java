package Entity;
// Generated 15-jul-2014 19:36:51 by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Historialclinico generated by hbm2java
 */
public class Historialclinico implements java.io.Serializable {

    private int historialclinicoid;
    private Paciente paciente;
    private Date fechahistorial;
    private String padecimiento;
    private String diagnostico;
    private String tratamiento;
    private Set antecedentes = new HashSet(0);
    private Set archivos = new HashSet(0);

    public Historialclinico() {
    }

    public Historialclinico(int historialclinicoid, Paciente paciente, Date fechahistorial, String padecimiento, String diagnostico, String tratamiento) {
        this.historialclinicoid = historialclinicoid;
        this.paciente = paciente;
        this.fechahistorial = fechahistorial;
        this.padecimiento = padecimiento;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
    }

    public Historialclinico(int historialclinicoid, Paciente paciente, Date fechahistorial, String padecimiento, String diagnostico, String tratamiento, Set antecedentes, Set archivos) {
        this.historialclinicoid = historialclinicoid;
        this.paciente = paciente;
        this.fechahistorial = fechahistorial;
        this.padecimiento = padecimiento;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.antecedentes = antecedentes;
        this.archivos = archivos;
    }

    public int getHistorialclinicoid() {
        return this.historialclinicoid;
    }

    public void setHistorialclinicoid(int historialclinicoid) {
        this.historialclinicoid = historialclinicoid;
    }

    public Paciente getPaciente() {
        return this.paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Date getFechahistorial() {
        return this.fechahistorial;
    }

    public void setFechahistorial(Date fechahistorial) {
        this.fechahistorial = fechahistorial;
    }

    public String getPadecimiento() {
        return this.padecimiento;
    }

    public void setPadecimiento(String padecimiento) {
        this.padecimiento = padecimiento;
    }

    public String getDiagnostico() {
        return this.diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return this.tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Set getAntecedentes() {
        return this.antecedentes;
    }

    public void setAntecedentes(Set antecedentes) {
        this.antecedentes = antecedentes;
    }

    public Set getArchivos() {
        return this.archivos;
    }

    public void setArchivos(Set archivos) {
        this.archivos = archivos;
    }

    @Override
    public String toString() {
        return historialclinicoid + " " + paciente.getNombre();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Historialclinico)) {
            return false;
        }
        Historialclinico historialclinico = (Historialclinico) o;
        return (this.historialclinicoid == historialclinico.historialclinicoid);

    }
}
