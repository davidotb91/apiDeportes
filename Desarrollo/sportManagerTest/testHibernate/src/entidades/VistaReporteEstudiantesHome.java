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
 * Home object for domain model class VistaReporteEstudiantes.
 * @see entidades.VistaReporteEstudiantes
 * @author Hibernate Tools
 */
public class VistaReporteEstudiantesHome {

	private static final Log log = LogFactory.getLog(VistaReporteEstudiantesHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(VistaReporteEstudiantes transientInstance) {
		log.debug("persisting VistaReporteEstudiantes instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(VistaReporteEstudiantes instance) {
		log.debug("attaching dirty VistaReporteEstudiantes instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(VistaReporteEstudiantes instance) {
		log.debug("attaching clean VistaReporteEstudiantes instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(VistaReporteEstudiantes persistentInstance) {
		log.debug("deleting VistaReporteEstudiantes instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public VistaReporteEstudiantes merge(VistaReporteEstudiantes detachedInstance) {
		log.debug("merging VistaReporteEstudiantes instance");
		try {
			VistaReporteEstudiantes result = (VistaReporteEstudiantes) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public VistaReporteEstudiantes findById(entidades.VistaReporteEstudiantesId id) {
		log.debug("getting VistaReporteEstudiantes instance with id: " + id);
		try {
			VistaReporteEstudiantes instance = (VistaReporteEstudiantes) sessionFactory.getCurrentSession()
					.get("entidades.VistaReporteEstudiantes", id);
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

	public List findByExample(VistaReporteEstudiantes instance) {
		log.debug("finding VistaReporteEstudiantes instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("entidades.VistaReporteEstudiantes")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
