/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entity.Medico;
import Entity.Paciente;
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
 * @author leonel
 */
public class MedicoDao extends AbstractDao<Medico> {

    public MedicoDao() {
        super(Medico.class);
    }

    @Override
    public List<Medico> findAll() {

        List<Medico> allEntities = new ArrayList<Medico>();
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Criteria criteria = session.createCriteria(Medico.class).setFetchMode("categoria", FetchMode.JOIN);
            allEntities = criteria.list();
            for (Medico med : allEntities) {
                Hibernate.initialize(med.getCategoria());
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            //session.flush();
            //session.close();
        }
        return allEntities;
    }

    public void create(Medico entity) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Hibernate.initialize(entity.getCategoria());
            session.save(entity);
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

    public void update(Medico entity) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Hibernate.initialize(entity.getCategoria());
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

    public void delete(Medico entity) {
        Transaction trns = null;
          Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        try {
            trns = session.beginTransaction();
            Hibernate.initialize(entity.getCategoria());
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

    public Medico find(Object id, boolean lock) {

        Medico entity = null;
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            if (lock) {
                entity = (Medico) session.load(Medico.class, (Serializable) id, LockMode.UPGRADE);
            } else {
                entity = (Medico) session.load(Medico.class, (Serializable) id);
            }
            Hibernate.initialize(entity.getCategoria());
            trns.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            //session.flush();
            //session.close();
        }

        return entity;
    }

    public List<Medico> findRange(int[] range) {

        List<Medico> rangeEntities = new ArrayList<Medico>();
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Criteria criteria = session.createCriteria(Medico.class);
            criteria.setMaxResults(range[1] - range[0]);
            criteria.setFirstResult(range[0]);
            rangeEntities = criteria.list();
            for (Medico med : rangeEntities) {
                Hibernate.initialize(med.getCategoria());
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            // session.flush();
            //session.close();
        }
        return rangeEntities;
    }

    public Boolean findCedula1(String ceduka, Integer id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        Criteria crit = session.createCriteria(Medico.class);
        crit.add(Restrictions.eq("cedula", ceduka));
        Medico m = (Medico) crit.uniqueResult();
        if (id.equals(0)) {
            return m != null ? true : false;
        } else {
            return m != null ? (m.getMedicoid() != id ? true : false) : false;
        }



    }

    public Boolean findEmail1(String email, Integer id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        Criteria crit = session.createCriteria(Medico.class);
        crit.add(Restrictions.eq("email", email));
        Medico m = (Medico) crit.uniqueResult();
        if (id.equals(0)) {
            return m != null ? true : false;
        } else {
            return m != null ? (m.getMedicoid() != id ? true : false) : false;
        }

    }

    public Boolean PacienteAsociado(Medico m) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        Criteria crit = session.createCriteria(Paciente.class);
        
        crit.add(Restrictions.eq("medico", m));
        List <Paciente> pad =  crit.list();

        return pad.size()> 0 ? true : false;


    }
    
    public Medico validarEntrada(String nombre, String passWord) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery("select m from Medico m "
                    + "where m.email = :mail and m.contrasena = :contra and m.medicoid != 0 and m.activo = true")
                    .setParameter("mail", nombre)
                    .setParameter("contra", passWord);
            List list = query.list();
            return (list == null || list.isEmpty() ? null : (Medico) list.get(0));
        } catch (Exception e) {
            return null;
        }
    }
}
