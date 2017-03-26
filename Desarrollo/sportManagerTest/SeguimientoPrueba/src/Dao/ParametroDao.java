package Dao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import Util.HibernateUtil;
import Entity.Parametro;

public class ParametroDao extends AbstractDao<Parametro> {

    public ParametroDao() {
        super(Parametro.class);
    }
    
    
	public List<Parametro> findAllByUser() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction trns = session.beginTransaction();
		try {
			Query query = session.createQuery(
					"select parametro from Parametro as parametro ");
			return query.list();
		} catch (Exception e) {
			return null;
		}
	}
    
}

