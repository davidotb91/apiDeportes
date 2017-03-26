/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entity.Diagnostico;
import Entity.Medico;
import Util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Leonel
 */
public class DiagnosticoDao extends AbstractDao<Diagnostico> {

    public DiagnosticoDao() {
        super(Diagnostico.class);
    }

    public List<Diagnostico> findAllByUser(Medico currentUser) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    "select c.diagnostico from Consulta c where c.medico = :user")
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
