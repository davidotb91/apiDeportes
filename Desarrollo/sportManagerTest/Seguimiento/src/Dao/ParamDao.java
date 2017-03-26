package Dao;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Util.HibernateUtil;
import Entity.Docente;
import Entity.Param;

public class ParamDao extends AbstractDao<Param> {

    public ParamDao() {
        super(Param.class);
    }
    
    public List<Param> obtenerAnio() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction trns = session.beginTransaction();
		try {
			Query query = session.createQuery(
					" select param from Param as param where param.parNemonico = 'PANIO' ");
			//trns.commit();
			return query.list();			
		} catch (Exception e) {
			return null;
		}
	}
    
}