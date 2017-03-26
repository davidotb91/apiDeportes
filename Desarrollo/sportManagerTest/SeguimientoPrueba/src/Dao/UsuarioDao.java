package Dao;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import Util.HibernateUtil;
import Entity.Usuario;
import Entity.Usuario;
import Entity.Parametro;

public class UsuarioDao extends AbstractDao<Usuario> {

    public UsuarioDao() {
        super(Usuario.class);
    }
 
    public Usuario validarEntrada(String nombre, String passWord) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
        	//verificar
        	
        	//select m from Usuario m where m.usuaLogin = 'admin'
        	 // and m.usuaContrasena = 'admin' 
        	String hqlQuery = "select m from Usuario m "
            					+ "where m.usuaLogin = '"+ nombre +"'"
            					+ "and m.usuaContrasena = '"+ passWord +"'";
            					
            System.out.println(hqlQuery);					
            Query query = session.createQuery(hqlQuery);
            
            List list = query.list();
            
           	return (list == null || list.isEmpty() ? null : (Usuario) list.get(0));
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<Usuario> findAll() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction trns = session.beginTransaction();
		try {
			Query query = session.createQuery(
					"select m from Usuario m where m.usuaLogin != 'admin' ");
			return query.list();
		} catch (Exception e) {
			return null;
		}
	}
    
    public Boolean findCedula1(String cedula, Integer id) {
    	try {
		        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		        Transaction trns = session.beginTransaction();
		        Criteria crit = session.createCriteria(Usuario.class);
		        crit.add(Restrictions.eq("docCedula", cedula));
		        Usuario m = (Usuario) crit.uniqueResult();
		        trns.commit();
		        if (id.equals(0)) {
		            return m != null ? true : false;
		        } else {
		            return m != null ? (m.getUsuaId() != id ? true : false) : false;
		        }
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
        
    }    

    
}