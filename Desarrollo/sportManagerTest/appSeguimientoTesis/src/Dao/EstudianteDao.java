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
import Entity.Cita;
import Entity.Consulta;
import Entity.Estudiante;
import Entity.Medico;
import Entity.Paciente;
import Entity.ParametroDetalle;

public class EstudianteDao extends AbstractDao<Estudiante> {

    public EstudianteDao() {
        super(Estudiante.class);
    }      
    
    
    public List<Estudiante> findAllByUser() {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    "select c from Estudiante c");            
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }
    
    public ParametroDetalle findParametroDetalle(String catalogValue) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        Criteria crit = session.createCriteria(ParametroDetalle.class);
        crit.add(Restrictions.eq("padCodigo", catalogValue));
        ParametroDetalle p = (ParametroDetalle) crit.uniqueResult();
        return p != null ? p : null;
     }
    
}

