/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entity.Consulta;
import Entity.Medico;
import Util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Leonel
 */
public class ConsultaDao extends AbstractDao<Consulta> {

    public ConsultaDao() {
        super(Consulta.class);
    }

    @Override
    public List<Consulta> findAll() {

        List<Consulta> allEntities = new ArrayList<Consulta>();
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Criteria criteria = session.createCriteria(Consulta.class);
            allEntities = criteria.list();
            for (Consulta con : allEntities) {
                Hibernate.initialize(con.getPaciente());
                Hibernate.initialize(con.getMedico());
                Hibernate.initialize(con.getTratamiento());
                Hibernate.initialize(con.getDiagnostico());
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            //session.flush();
            //session.close();
        }
        return allEntities;
    }

    public void update(Consulta entity) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Hibernate.initialize(entity.getPaciente());
            Hibernate.initialize(entity.getMedico());
            Hibernate.initialize(entity.getTratamiento());
            Hibernate.initialize(entity.getDiagnostico());
            session.update(entity);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            //session.flush();
            //session.close();
        }
    }

    public void delete(Consulta entity) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Hibernate.initialize(entity.getPaciente());
            Hibernate.initialize(entity.getMedico());
            Hibernate.initialize(entity.getTratamiento());
            Hibernate.initialize(entity.getDiagnostico());
            session.delete(entity);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            //session.flush();
            //session.close();
        }
    }

    public Consulta find(Object id, boolean lock) {

        Consulta entity = null;
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            if (lock) {
                entity = (Consulta) session.load(Consulta.class, (Serializable) id, LockMode.UPGRADE);
            } else {
                entity = (Consulta) session.load(Consulta.class, (Serializable) id);
            }
            Hibernate.initialize(entity.getPaciente());
            Hibernate.initialize(entity.getMedico());
            Hibernate.initialize(entity.getTratamiento());
            Hibernate.initialize(entity.getDiagnostico());
            trns.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            //session.flush();
            //session.close();
        }

        return entity;
    }

    public List<Consulta> findRange(int[] range) {

        List<Consulta> rangeEntities = new ArrayList<Consulta>();
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Criteria criteria = session.createCriteria(Consulta.class);
            criteria.setMaxResults(range[1] - range[0]);
            criteria.setFirstResult(range[0]);
            rangeEntities = criteria.list();
            for (Consulta c : rangeEntities) {
                Hibernate.initialize(c.getPaciente());
                Hibernate.initialize(c.getMedico());
                Hibernate.initialize(c.getTratamiento());
                Hibernate.initialize(c.getDiagnostico());
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            // session.flush();
            //session.close();
        }
        return rangeEntities;
    }

    public List<Consulta> findAllByUser(Medico currentUser) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    "select c from Consulta c where c.medico = :user")
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
}
