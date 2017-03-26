package Dao;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import Util.HibernateUtil;
import Entity.Docente;
import Entity.Docente;
import Entity.Parametro;

public class DocenteDao extends AbstractDao<Docente> {

    public DocenteDao() {
        super(Docente.class);
    }
 
    public Docente validarEntrada(String nombre, String passWord) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
        	//verificar
        	String hqlQuery = "select m from Docente m "
            					+ "where m.docLogin = '"+ nombre +"'"
            					+ "and m.docPassword = '"+ passWord +"' and m.parametroDetalleByPadCodigo.padCodigo = 'DAC'";
            					
            System.out.println(hqlQuery);					
            Query query = session.createQuery(hqlQuery);
            
            List list = query.list();
            
           	return (list == null || list.isEmpty() ? null : (Docente) list.get(0));
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<Docente> findAll() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction trns = session.beginTransaction();
		try {
			Query query = session.createQuery(
					"select m from Docente m where m.docLogin != 'admin' ");
			return query.list();
		} catch (Exception e) {
			return null;
		}
	}
    
    public Boolean findCedula1(String cedula, Integer id) {
    	try {
		        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		        Transaction trns = session.beginTransaction();
		        Criteria crit = session.createCriteria(Docente.class);
		        crit.add(Restrictions.eq("docCedula", cedula));
		        Docente m = (Docente) crit.uniqueResult();
		        trns.commit();
		        if (id.equals(0)) {
		            return m != null ? true : false;
		        } else {
		            return m != null ? (m.getDocId() != id ? true : false) : false;
		        }
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
        
    }    

    
}