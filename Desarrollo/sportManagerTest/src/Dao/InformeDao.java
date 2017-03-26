/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entity.Informe;
import Entity.Informe;
import Entity.Medico;
import Entity.Paciente;
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
public class InformeDao extends AbstractDao<Informe> {

    public InformeDao() {
        super(Informe.class);
    }
    
     @Override
    public List<Informe> findAll() {

        List<Informe> allEntities = new ArrayList<Informe>();
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Criteria criteria = session.createCriteria(Informe.class);
            allEntities = criteria.list();
            for (Informe ca : allEntities) {
                Hibernate.initialize(ca.getPaciente());
                Hibernate.initialize(ca.getMedico());
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            //session.flush();
            //session.close();
        }
        return allEntities;
    }

    public void update(Informe entity) {
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

    public void delete(Informe entity) {
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

    public Informe find(Object id, boolean lock) {

        Informe entity = null;
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            if (lock) {
                entity = (Informe) session.load(Informe.class, (Serializable) id, LockMode.UPGRADE);
            } else {
                entity = (Informe) session.load(Informe.class, (Serializable) id);
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

    public List<Informe> findRange(int[] range) {

        List<Informe> rangeEntities = new ArrayList<Informe>();
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Criteria criteria = session.createCriteria(Informe.class);
            criteria.setMaxResults(range[1] - range[0]);
            criteria.setFirstResult(range[0]);
            rangeEntities = criteria.list();
            for (Informe c : rangeEntities) {
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
    
    public List<Informe> findAllByUser(Medico currentUser) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    "select i from Informe i where i.medico = :user")
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
