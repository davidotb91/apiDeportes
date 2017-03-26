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
 * Home object for domain model class SeguimientoTesis.
 * @see entidades.SeguimientoTesis
 * @author Hibernate Tools
 */
public class SeguimientoTesisHome {

	private static final Log log = LogFactory.getLog(SeguimientoTesisHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(SeguimientoTesis transientInstance) {
		log.debug("persisting SeguimientoTesis instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(SeguimientoTesis instance) {
		log.debug("attaching dirty SeguimientoTesis instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SeguimientoTesis instance) {
		log.debug("attaching clean SeguimientoTesis instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(SeguimientoTesis persistentInstance) {
		log.debug("deleting SeguimientoTesis instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SeguimientoTesis merge(SeguimientoTesis detachedInstance) {
		log.debug("merging SeguimientoTesis instance");
		try {
			SeguimientoTesis result = (SeguimientoTesis) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SeguimientoTesis findById(int id) {
		log.debug("getting SeguimientoTesis instance with id: " + id);
		try {
			SeguimientoTesis instance = (SeguimientoTesis) sessionFactory.getCurrentSession()
					.get("entidades.SeguimientoTesis", id);
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

	public List findByExample(SeguimientoTesis instance) {
		log.debug("finding SeguimientoTesis instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("entidades.SeguimientoTesis")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
