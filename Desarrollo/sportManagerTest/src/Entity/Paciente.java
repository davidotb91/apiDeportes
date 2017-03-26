package Entity;
// Generated 15-jul-2014 19:36:51 by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Transient;
import org.hibernate.Hibernate;

/**
 * Paciente generated by hbm2java
 */
public class Paciente implements java.io.Serializable {

    private int pacienteid;
    private Medico medico;
    private String apellidopaterno;
    private String cedula;
    private String profesion;
    private String numerohijos;
    private String nombre;
    private String lugartrabajo;
    private String segundonombre;
    private String edad;
    private String telefono;
    private String celular;
    private Date fechanacimiento;
    private String nacionalidad;
    private String email;
    private String genero;
    private String direccion;
    private String estadocivil;
    private String apellidomaterno;
    private String provincia;
    /*private Set informes = new HashSet(0);
    private Set recetas = new HashSet(0);
    private Set historialclinicos = new HashSet(0);
    private Set consultas = new HashSet(0);
    private Set cabecerafacturas = new HashSet(0);
    private Set odontogramas = new HashSet(0);
    private Set citas = new HashSet(0);*/
    @Transient
    private Integer position;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
    @Transient
    private String dateForFilter;

    public String getDateForFilter() {
        return dateForFilter;
    }

    public void setDateForFilter(String dateForFilter) {
        this.dateForFilter = dateForFilter;
    }

    public Paciente() {
        medico = new Medico();
    }

    public Paciente(int pacienteid, Medico medico, String apellidopaterno, String cedula, String profesion, String numerohijos, String nombre, String lugartrabajo, String segundonombre, String edad, String telefono, String celular, Date fechanacimiento, String nacionalidad, String email, String genero, String direccion, String estadocivil, String apellidomaterno, String provincia) {
        this.pacienteid = pacienteid;
        this.medico = medico;
        this.apellidopaterno = apellidopaterno;
        this.cedula = cedula;
        this.profesion = profesion;
        this.numerohijos = numerohijos;
        this.nombre = nombre;
        this.lugartrabajo = lugartrabajo;
        this.segundonombre = segundonombre;
        this.edad = edad;
        this.telefono = telefono;
        this.celular = celular;
        this.fechanacimiento = fechanacimiento;
        this.nacionalidad = nacionalidad;
        this.email = email;
        this.genero = genero;
        this.direccion = direccion;
        this.estadocivil = estadocivil;

        this.apellidomaterno = apellidomaterno;
        this.provincia = provincia;
    }

    public Paciente(int pacienteid, Medico medico, String apellidopaterno, String cedula, String profesion, String numerohijos, String nombre, String lugartrabajo, String segundonombre, String edad, String telefono, String celular, Date fechanacimiento, String nacionalidad, String email, String genero, String direccion, String estadocivil, String apellidomaterno, String provincia, Set informes, Set recetas, Set historialclinicos, Set consultas, Set cabecerafacturas, Set odontogramas, Set citas) {
        this.pacienteid = pacienteid;
        this.medico = medico;
        this.apellidopaterno = apellidopaterno;
        this.cedula = cedula;
        this.profesion = profesion;
        this.numerohijos = numerohijos;
        this.nombre = nombre;
        this.lugartrabajo = lugartrabajo;
        this.segundonombre = segundonombre;
        this.edad = edad;
        this.telefono = telefono;
        this.celular = celular;
        this.fechanacimiento = fechanacimiento;
        this.nacionalidad = nacionalidad;
        this.email = email;
        this.genero = genero;
        this.direccion = direccion;
        this.estadocivil = estadocivil;

        this.apellidomaterno = apellidomaterno;
        this.provincia = provincia;
        /*this.informes = informes;
        this.recetas = recetas;
        this.historialclinicos = historialclinicos;
        this.consultas = consultas;
        this.cabecerafacturas = cabecerafacturas;
        this.odontogramas = odontogramas;
        this.citas = citas;*/
    }

    public int getPacienteid() {
        return this.pacienteid;
    }

    public void setPacienteid(int pacienteid) {
        this.pacienteid = pacienteid;
    }

    public Medico getMedico() {

        Hibernate.initialize(this.medico);
        return this.medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getApellidopaterno() {
        return this.apellidopaterno;
    }

    public void setApellidopaterno(String apellidopaterno) {
        this.apellidopaterno = apellidopaterno;
    }

    public String getCedula() {
        return this.cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getProfesion() {
        return this.profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getNumerohijos() {
        return this.numerohijos;
    }

    public void setNumerohijos(String numerohijos) {
        this.numerohijos = numerohijos;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugartrabajo() {
        return this.lugartrabajo;
    }

    public void setLugartrabajo(String lugartrabajo) {
        this.lugartrabajo = lugartrabajo;
    }

    public String getSegundonombre() {
        return this.segundonombre;
    }

    public void setSegundonombre(String segundonombre) {
        this.segundonombre = segundonombre;
    }

    public String getEdad() {
        return this.edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Date getFechanacimiento() {
        return this.fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getNacionalidad() {
        return this.nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstadocivil() {
        return this.estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }

    public String getApellidomaterno() {
        return this.apellidomaterno;
    }

    public void setApellidomaterno(String apellidomaterno) {
        this.apellidomaterno = apellidomaterno;
    }

    public String getProvincia() {
        return this.provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /*public Set getInformes() {
        return this.informes;
    }

    public void setInformes(Set informes) {
        this.informes = informes;
    }

    public Set getRecetas() {
        return this.recetas;
    }

    public void setRecetas(Set recetas) {
        this.recetas = recetas;
    }

    public Set getHistorialclinicos() {
        return this.historialclinicos;
    }

    public void setHistorialclinicos(Set historialclinicos) {
        this.historialclinicos = historialclinicos;
    }

    public Set getConsultas() {
        return this.consultas;
    }

    public void setConsultas(Set consultas) {
        this.consultas = consultas;
    }

    public Set getCabecerafacturas() {
        return this.cabecerafacturas;
    }

    public void setCabecerafacturas(Set cabecerafacturas) {
        this.cabecerafacturas = cabecerafacturas;
    }

    public Set getOdontogramas() {
        return this.odontogramas;
    }

    public void setOdontogramas(Set odontogramas) {
        this.odontogramas = odontogramas;
    }

    public Set getCitas() {
        return this.citas;
    }

    public void setCitas(Set citas) {
        this.citas = citas;
    }*/

    @Override
    public String toString() {
        return nombre + " " + apellidopaterno;
    }

    /*@Override
     public boolean equals(Object o) {
     if (!(o instanceof Paciente)) {
     return false;
     }
     Paciente paciente = (Paciente) o;
     return (this.pacienteid == paciente.pacienteid);

     }*/
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Paciente)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return pacienteid == ((Paciente) obj).getPacienteid();
    }
}
