package entidades;
// Generated 20-mar-2017 23:14:37 by Hibernate Tools 3.5.0.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class ParametroDetalle.
 * @see entidades.ParametroDetalle
 * @author Hibernate Tools
 */
public class ParametroDetalleHome {

	private static final Log log = LogFactory.getLog(ParametroDetalleHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(ParametroDetalle transientInstance) {
		log.debug("persisting ParametroDetalle instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(ParametroDetalle instance) {
		log.debug("attaching dirty ParametroDetalle instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ParametroDetalle instance) {
		log.debug("attaching clean ParametroDetalle instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(ParametroDetalle persistentInstance) {
		log.debug("deleting ParametroDetalle instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ParametroDetalle merge(ParametroDetalle detachedInstance) {
		log.debug("merging ParametroDetalle instance");
		try {
			ParametroDetalle result = (ParametroDetalle) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ParametroDetalle findById(java.lang.String id) {
		log.debug("getting ParametroDetalle instance with id: " + id);
		try {
			ParametroDetalle instance = (ParametroDetalle) sessionFactory.getCurrentSession()
					.get("entidades.ParametroDetalle", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ParametroDetalle instance) {
		log.debug("finding ParametroDetalle instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("entidades.ParametroDetalle")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
