/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entity.Cabecerafactura;
import Entity.Cita;
import Entity.Medico;
import Entity.Paciente;
import Util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
public class CitaDao extends AbstractDao<Cita> {

    public CitaDao() {
        super(Cita.class);
    }

    @Override
    public List<Cita> findAll() {

        List<Cita> allEntities = new ArrayList<Cita>();
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Criteria criteria = session.createCriteria(Cita.class);
            allEntities = criteria.list();
            for (Cita ca : allEntities) {
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

    public void update(Cita entity) {
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

    public void delete(Cita entity) {
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

    public Cita find(Object id, boolean lock) {

        Cita entity = null;
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            if (lock) {
                entity = (Cita) session.load(Cita.class, (Serializable) id, LockMode.UPGRADE);
            } else {
                entity = (Cita) session.load(Cita.class, (Serializable) id);
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

    public List<Cita> findRange(int[] range) {

        List<Cita> rangeEntities = new ArrayList<Cita>();
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Criteria criteria = session.createCriteria(Paciente.class);
            criteria.setMaxResults(range[1] - range[0]);
            criteria.setFirstResult(range[0]);
            rangeEntities = criteria.list();
            for (Cita c : rangeEntities) {
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
    
    public List<Cita> findAllByUser(Medico currentUser) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            /*Query query = session.createQuery(
                    "select c from Cita c where c.medico = :user")
            		"select c from Cita c ")
                    .setParameter("user", currentUser);*/ //ETA
            Query query = session.createQuery(
            		"select c from Cita c ");
        
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
    
    public List<Cita> findByFecha(Date fecha) {
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    "select c from Cita c where c.fechacita = :fecha")
                    .setParameter("fecha", fecha);
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }
}
