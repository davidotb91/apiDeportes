package Dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


import Entity.Jugador;
import Entity.ParametroDetalle;
import Util.HibernateUtil;

public class JugadorDao extends AbstractDao<Jugador> {

	public JugadorDao() {
		super(Jugador.class);
		
	}
	 public List<Jugador> findAllByUser() {

	        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	        Transaction trns = session.beginTransaction();
	        try {
	            Query query = session.createQuery(
	                    "select c from Jugador c where c.estId not in (select t.Jugador.estId from Tema t)");            
	            return query.list();
	        } catch (Exception e) {
	            return null;
	        }
	    }

	    public List<Jugador> findAll() {

	        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	        Transaction trns = session.beginTransaction();
	        try {
	            Query query = session.createQuery(
	                    "select c from Jugador c ");            
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
	    
	    public Jugador validarEntrada(String nombre, String passWord) {

	        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	        Transaction trns = session.beginTransaction();
	        try {
	        	//verificar
	        	String hqlQuery = " select Jugador from Jugador as Jugador "
	            					+ "where Jugador.estCedula = '"+ nombre +"'";
	            					//+ " and Jugador.estPassword = '"+ passWord +"' and Jugador.parametroDetalle.padCodigo = 'DAC'";
	        	
	            System.out.println(hqlQuery);					
	            Query query = session.createQuery(hqlQuery);
	            
	            List list = query.list();
	            
	           	return (list == null || list.isEmpty() ? null : (Jugador) list.get(0));
	        } catch (Exception e) {
	            return null;
	        }
	    }
	    
	    public Boolean findCedula1(String cedula, Integer id) {
	    	try {
			        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			        Transaction trns = session.beginTransaction();
			        Criteria crit = session.createCriteria(Jugador.class);
			        crit.add(Restrictions.eq("estCedula", cedula));
			        Jugador m = (Jugador) crit.uniqueResult();
			        trns.commit();
			        if (id.equals(0)) {
			            return m != null ? true : false;
			        } else {
			            return m != null ? (m.getJugaId() != id ? true : false) : false;
			        }
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
	        
	    } 
	}
	
	


