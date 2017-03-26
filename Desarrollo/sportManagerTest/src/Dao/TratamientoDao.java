/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entity.Medico;
import Entity.Tratamiento;
import Util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Admin
 */
public class TratamientoDao extends AbstractDao<Tratamiento> {

    public TratamientoDao() {
        super(Tratamiento.class);
    }

    public List<Tratamiento> findAllByUser(Medico currentUser) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    "select c.tratamiento from Consulta c where c.medico = :user")
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
