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
 * Home object for domain model class PerfilRecurso.
 * @see entidades.PerfilRecurso
 * @author Hibernate Tools
 */
public class PerfilRecursoHome {

	private static final Log log = LogFactory.getLog(PerfilRecursoHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(PerfilRecurso transientInstance) {
		log.debug("persisting PerfilRecurso instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(PerfilRecurso instance) {
		log.debug("attaching dirty PerfilRecurso instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PerfilRecurso instance) {
		log.debug("attaching clean PerfilRecurso instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(PerfilRecurso persistentInstance) {
		log.debug("deleting PerfilRecurso instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PerfilRecurso merge(PerfilRecurso detachedInstance) {
		log.debug("merging PerfilRecurso instance");
		try {
			PerfilRecurso result = (PerfilRecurso) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public PerfilRecurso findById(int id) {
		log.debug("getting PerfilRecurso instance with id: " + id);
		try {
			PerfilRecurso instance = (PerfilRecurso) sessionFactory.getCurrentSession().get("entidades.PerfilRecurso",
					id);
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

	public List findByExample(PerfilRecurso instance) {
		log.debug("finding PerfilRecurso instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("entidades.PerfilRecurso")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
