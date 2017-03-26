package Dao;
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
import Entity.Docente;
import Entity.Estudiante;
import Entity.SeguimientoTesis;


public class SeguimientoTesisDao extends AbstractDao<SeguimientoTesis> {

    public SeguimientoTesisDao() {
        super(SeguimientoTesis.class);
    }   
    
   
    
    public List<SeguimientoTesis> findAllByUser(Docente user) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
        	Query query = null;
        	if (((ControlSesion) Context.getBean("SesionBean")).isDocente == true)
        	{
               query = session.createQuery(
            		"select c from SeguimientoTesis c where c.tema.parametroDetalle.padCodigo != 'APR' and c.docente.docId = " + user.getDocId());
        	}
         	else
         	{
         		Estudiante estudiante = ((ControlSesion) Context.getBean("SesionBean")).getUsuarioE();
                query = session.createQuery(
                		"select c from SeguimientoTesis c where c.tema.estudiante.estId = " + estudiante.getEstId());
         	}
            
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }

    public List<SeguimientoTesis> findAllByUserS(Docente user) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
        	Query query = null;
        	if (((ControlSesion) Context.getBean("SesionBean")).isDocente == true)
        	{
               query = session.createQuery(
            		"select c from SeguimientoTesis c where c.docente.docId = " + user.getDocId());
        	}
         	else
         	{
         		Estudiante estudiante = ((ControlSesion) Context.getBean("SesionBean")).getUsuarioE();
                query = session.createQuery(
                		"select c from SeguimientoTesis c where c.tema.estudiante.estId = " + estudiante.getEstId());
         	}
            
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }    
    
    
}

