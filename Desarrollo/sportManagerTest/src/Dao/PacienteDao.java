/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entity.Cita;
import Entity.Consulta;
import Entity.Informe;
import Entity.Medico;
import Entity.Odontograma;
import Entity.Paciente;
import Entity.Receta;
import Util.HibernateUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * 
 * @author Leonel
 */
public class PacienteDao extends AbstractDao<Paciente> {

	public PacienteDao() {
		super(Paciente.class);
	}

	@Override
	public List<Paciente> findAll() {

		List<Paciente> allEntities = new ArrayList<Paciente>();
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			trns = session.beginTransaction();
			Criteria criteria = session.createCriteria(Paciente.class)
					.setFetchMode("medico", FetchMode.JOIN);
			;
			allEntities = criteria.list();
			for (Paciente pa : allEntities) {
				Hibernate.initialize(pa.getMedico());
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			// session.flush();
			// session.close();
		}
		return allEntities;
	}

	public void update(Paciente entity) {
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			trns = session.beginTransaction();
			Hibernate.initialize(entity.getMedico());
			session.update(entity);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			// session.flush();
			// session.close();
		}
	}

	public void delete(Paciente entity) {
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			trns = session.beginTransaction();
			Hibernate.initialize(entity.getMedico());
			session.delete(entity);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			// session.flush();
			// session.close();
		}
	}

	public Paciente find(Object id, boolean lock) {

		Paciente entity = null;
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			trns = session.beginTransaction();
			if (lock) {
				entity = (Paciente) session.load(Paciente.class,
						(Serializable) id, LockMode.UPGRADE);
			} else {
				entity = (Paciente) session.load(Paciente.class,
						(Serializable) id);
			}
			Hibernate.initialize(entity.getMedico());
			trns.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			// session.flush();
			// session.close();
		}

		return entity;
	}

	public List<Paciente> findRange(int[] range) {

		List<Paciente> rangeEntities = new ArrayList<Paciente>();
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			trns = session.beginTransaction();
			Criteria criteria = session.createCriteria(Paciente.class);
			criteria.setMaxResults(range[1] - range[0]);
			criteria.setFirstResult(range[0]);
			rangeEntities = criteria.list();
			for (Paciente pa : rangeEntities) {
				Hibernate.initialize(pa.getMedico());
			}

		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			// session.flush();
			// session.close();
		}
		return rangeEntities;
	}

	public Boolean findCedula1(String ceduka, Integer id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction trns = session.beginTransaction();
		Criteria crit = session.createCriteria(Paciente.class);
		crit.add(Restrictions.eq("cedula", ceduka));
		Paciente m = (Paciente) crit.uniqueResult();
		if (id.equals(0)) {
			return m != null ? true : false;
		} else {
			return m != null ? (m.getPacienteid() != id ? true : false) : false;
		}

	}

	public Boolean findEmail1(String email, Integer id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction trns = session.beginTransaction();
		Criteria crit = session.createCriteria(Paciente.class);
		crit.add(Restrictions.eq("email", email));
		Paciente m = (Paciente) crit.uniqueResult();
		if (id.equals(0)) {
			return m != null ? true : false;
		} else {
			return m != null ? (m.getPacienteid() != id ? true : false) : false;
		}

	}

	public List<Paciente> findAllByUser(Medico currentUser) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction trns = session.beginTransaction();
		try {
			Query query = session.createQuery(
					"select p from Paciente p where p.medico = :user")
					.setParameter("user", currentUser);
			return query.list();
		} catch (Exception e) {
			return null;
		}
	}

	public int countByUser(Medico currentUser) {

		try {
			return this.findAllByUser(currentUser).size();
		} catch (Exception e) {
			return 0;
		}
	}

	public Boolean ConsultaAsociado(Paciente p) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction trns = session.beginTransaction();
		Criteria crit = session.createCriteria(Consulta.class);

		crit.add(Restrictions.eq("paciente", p));
		List<Consulta> pad = crit.list();

		return pad.size() > 0 ? true : false;

	}

	public Boolean CitasAsociado(Paciente p) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction trns = session.beginTransaction();
		Criteria crit = session.createCriteria(Cita.class);

		crit.add(Restrictions.eq("paciente", p));
		List<Cita> pad = crit.list();

		return pad.size() > 0 ? true : false;

	}

	public Boolean RecetaAsociado(Paciente p) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction trns = session.beginTransaction();
		Criteria crit = session.createCriteria(Receta.class);

		crit.add(Restrictions.eq("paciente", p));
		List<Receta> pad = crit.list();

		return pad.size() > 0 ? true : false;

	}

	public Boolean OdontogramaAsociado(Paciente p) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction trns = session.beginTransaction();
		Criteria crit = session.createCriteria(Odontograma.class);

		crit.add(Restrictions.eq("paciente", p));

		List<Odontograma> pad = crit.list();

		return pad.size() > 0 ? true : false;

	}
	
	public Boolean InformeAsociado(Paciente p) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction trns = session.beginTransaction();
		Criteria crit = session.createCriteria(Informe.class);

		crit.add(Restrictions.eq("paciente", p));

		List<Informe> pad = crit.list();

		return pad.size() > 0 ? true : false;

	}
}
