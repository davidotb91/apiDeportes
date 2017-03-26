package Dao;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import Util.HibernateUtil;
import Entity.Docente;
import Entity.Estudiante;
import Entity.ParametroDetalle;

public class EstudianteDao extends AbstractDao<Estudiante> {

    public EstudianteDao() {
        super(Estudiante.class);
    }      
    
    
    public List<Estudiante> findAllByUser() {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    "select c from Estudiante c where c.estId not in (select t.estudiante.estId from Tema t)");            
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Estudiante> findAll() {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    "select c from Estudiante c ");            
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
    
    public Estudiante validarEntrada(String nombre, String passWord) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
        	//verificar
        	String hqlQuery = " select estudiante from Estudiante as estudiante "
            					+ "where estudiante.estCedula = '"+ nombre +"'";
            					//+ " and estudiante.estPassword = '"+ passWord +"' and estudiante.parametroDetalle.padCodigo = 'DAC'";
        	
            System.out.println(hqlQuery);					
            Query query = session.createQuery(hqlQuery);
            
            List list = query.list();
            
           	return (list == null || list.isEmpty() ? null : (Estudiante) list.get(0));
        } catch (Exception e) {
            return null;
        }
    }
    
    public Boolean findCedula1(String cedula, Integer id) {
    	try {
		        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		        Transaction trns = session.beginTransaction();
		        Criteria crit = session.createCriteria(Estudiante.class);
		        crit.add(Restrictions.eq("estCedula", cedula));
		        Estudiante m = (Estudiante) crit.uniqueResult();
		        trns.commit();
		        if (id.equals(0)) {
		            return m != null ? true : false;
		        } else {
		            return m != null ? (m.getEstId() != id ? true : false) : false;
		        }
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
        
    } 
}

