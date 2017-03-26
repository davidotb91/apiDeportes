/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entity.Usuario;
import Util.HibernateUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Leonel
 */
public abstract class AbstractDao<T> {

    private Class<T> entityClass;
    

   
    public AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
        
    }

    public void create(T entity) {
        Transaction trns = null;
       
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            //session.refresh(entity); //ojo
            trns = session.beginTransaction();
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

    public void update(T entity) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {
            //Session session = HibernateUtil.buildSessionFactory().getCurrentSession();
        	//session.refresh(entity); //ojo
            trns = session.beginTransaction();
            
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
    
    public void update(T entity, Usuario entityAux) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            //Session session = HibernateUtil.buildSessionFactory().getCurrentSession();
        	//session.refresh(entity); //ojo
            trns = session.beginTransaction();
            if (entityAux != null)
            	session.update(entityAux);
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

   
    
    RuntimeException errorAbstract = null;
    
    
    public RuntimeException getErrorAbstract() {
		return errorAbstract;
	}

	public void setErrorAbstract(RuntimeException errorAbstract) {
		this.errorAbstract = errorAbstract;
	}

	public void delete(T entity) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            //Session session = HibernateUtil.buildSessionFactory().getCurrentSession();
            trns = session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
            this.setErrorAbstract(e);
        } finally {
            //session.flush();
            //session.close();
        }
    }

    public T find(Object id, boolean lock) {

        T entity = null;
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            if (lock) {
                entity = (T) session.load(entityClass, (Serializable) id, LockMode.UPGRADE);
            } else {
                entity = (T) session.load(entityClass, (Serializable) id);
            }
            //trns.commit(); //aqui cambie...
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            //session.flush();
            //session.close();
        }

        return entity;
    }

    public List<T> findAll() {

        List<T> allEntities = new ArrayList<T>();
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Criteria criteria = session.createCriteria(entityClass);
            allEntities = criteria.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            //session.flush();
            //session.close();
        }
        return allEntities;
    }

    public List<T> findRange(int[] range) {

        List<T> rangeEntities = new ArrayList<T>();
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Criteria criteria = session.createCriteria(entityClass);
            criteria.setMaxResults(range[1] - range[0]);
            criteria.setFirstResult(range[0]);
            rangeEntities = criteria.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
           // session.flush();
            //session.close();
        }
        return rangeEntities;
    }

    public int count() {
        List<T> allEntities = findAll();
        return allEntities != null ? allEntities.size() : 0;
    }
}
