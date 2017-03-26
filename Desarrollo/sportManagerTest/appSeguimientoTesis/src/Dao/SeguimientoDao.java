package Dao;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import Util.HibernateUtil;
import Entity.Seguimiento;


public class SeguimientoDao extends AbstractDao<Seguimiento> {

    public SeguimientoDao() {
        super(Seguimiento.class);
    }   
    
   
    
    public List<Seguimiento> findAllByUser() {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    "select c from Seguimiento c");
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }
    
    /*public ParametroDetalle findParametroDetalle(String catalogValue) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        Criteria crit = session.createCriteria(ParametroDetalle.class);
        crit.add(Restrictions.eq("padCodigo", catalogValue));
        ParametroDetalle p = (ParametroDetalle) crit.uniqueResult();
        return p != null ? p : null;
     }*/
    
}

