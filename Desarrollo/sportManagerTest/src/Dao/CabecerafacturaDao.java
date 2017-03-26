/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entity.Cabecerafactura;
import Util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Leonel
 */
public class CabecerafacturaDao extends AbstractDao<Cabecerafactura> {

    public CabecerafacturaDao() {
        super(Cabecerafactura.class);
    }

    @Override
    public List<Cabecerafactura> findAll() {

        List<Cabecerafactura> allEntities = new ArrayList<Cabecerafactura>();
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            trns = session.beginTransaction();
            Criteria criteria = session.createCriteria(Cabecerafactura.class);
            allEntities = criteria.list();
            for (Cabecerafactura ca : allEntities) {
                Hibernate.initialize(ca.getPaciente());
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            //session.flush();
            //session.close();
        }
        return allEntities;
    }
}
