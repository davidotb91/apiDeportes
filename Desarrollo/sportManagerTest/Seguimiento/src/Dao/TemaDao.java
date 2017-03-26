package Dao;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import Util.Context;
import Util.ControlSesion;
import Util.HibernateUtil;
import Entity.DataReporteEstadistica;
import Entity.Docente;
import Entity.Estudiante;
import Entity.ParametroDetalle;
import Entity.Tema;

public class TemaDao extends AbstractDao<Tema> {

    public TemaDao() {
        super(Tema.class);
    }   
    
   
    public List<Tema> findAllByUser(Docente user) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
        	
        	Query query = null;
        	if (((ControlSesion) Context.getBean("SesionBean")).isDocente == true)
        	{
                query = session.createQuery(
                    "select tema from Tema as tema where tema.parametroDetalle.padCodigo = 'ING' and tema.docente.docId = " + user.getDocId());
        	}
        	else
        	{
        		Estudiante estudiante = ((ControlSesion) Context.getBean("SesionBean")).getUsuarioE();
                query = session.createQuery(
                        "select tema from Tema as tema where tema.estudiante.estId = " + estudiante.getEstId());
        	}
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }
    
    public ParametroDetalle findParametroDetalle(String catalogValue) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        Criteria crit = session.createCriteria(ParametroDetalle.class);
        crit.add(Restrictions.eq("padCodigo", catalogValue));
        ParametroDetalle p = (ParametroDetalle) crit.uniqueResult();
        return p != null ? p : null;
     }

    public List<Tema> obtenerReporteEstadisticas(Date fechaInicial, Date fechaFinal, Estudiante estudiante) {

    	/*select tema.teId, tema.temDescripcion, tema.parametroDetalleMaestria.padDesCorta,
    	tema.parametroDetalle.padDesCorta, tema.temFechaPresentacion ,tema.temFechaMaximaAprobacion
    	from Tema as tema
    	order by tema.temFechaMaximaAprobacion*/
    	
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    "select tema from Tema as tema order by tema.temFechaMaximaAprobacion ");
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }   

    public List<Tema> findAll(Docente user) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    "select tema from Tema as tema where tema.docente.docId = " + user.getDocId());
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }
    
}

