/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entity.Medico;
import Entity.Odontograma;
import Entity.Receta;
import Util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Leonel
 */
public class OdontogramaDao extends AbstractDao<Odontograma> {

    public OdontogramaDao() {
        super(Odontograma.class);
    }

    public List<Odontograma> findAllByUser(Medico currentUser) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    "select o from Odontograma o where o.medico = :user")
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
