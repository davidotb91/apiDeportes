/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entity.Medico;
import Entity.Receta;
import Entity.Paciente;
import Entity.Receta;
import Entity.Receta;
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
public class RecetaDao extends AbstractDao<Receta> {

    public RecetaDao() {
        super(Receta.class);
    }

    @Override
    public List<Receta> findAll() {

        List<Receta> allEntities = new ArrayList<Receta>();
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Criteria criteria = session.createCriteria(Receta.class);
            allEntities = criteria.list();
            for (Receta rc : allEntities) {
                Hibernate.initialize(rc.getMedico());
                Hibernate.initialize(rc.getPaciente());
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            //session.flush();
            //session.close();
        }
        return allEntities;
    }

    public void update(Receta entity) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Hibernate.initialize(entity.getPaciente());
            Hibernate.initialize(entity.getMedico());
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

    public void delete(Receta entity) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Hibernate.initialize(entity.getPaciente());
            Hibernate.initialize(entity.getMedico());
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

    public Receta find(Object id, boolean lock) {

        Receta entity = null;
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            if (lock) {
                entity = (Receta) session.load(Receta.class, (Serializable) id, LockMode.UPGRADE);
            } else {
                entity = (Receta) session.load(Receta.class, (Serializable) id);
            }
            Hibernate.initialize(entity.getPaciente());
            Hibernate.initialize(entity.getMedico());
            trns.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            //session.flush();
            //session.close();
        }

        return entity;
    }

    public List<Receta> findRange(int[] range) {

        List<Receta> rangeEntities = new ArrayList<Receta>();
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Criteria criteria = session.createCriteria(Receta.class);
            criteria.setMaxResults(range[1] - range[0]);
            criteria.setFirstResult(range[0]);
            rangeEntities = criteria.list();
            for (Receta c : rangeEntities) {
                Hibernate.initialize(c.getPaciente());
                Hibernate.initialize(c.getMedico());
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            // session.flush();
            //session.close();
        }
        return rangeEntities;
    }

    public List<Receta> findAllByUser(Medico currentUser) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    "select r from Receta r where r.medico = :user")
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
