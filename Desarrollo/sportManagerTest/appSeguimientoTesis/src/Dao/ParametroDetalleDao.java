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

public class ParametroDetalleDao extends AbstractDao<ParametroDetalle> {

    public ParametroDetalleDao() {
        super(ParametroDetalle.class);
    }
    
    public List<ParametroDetalle> findAllByUser(String catalogo) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    "select c from ParametroDetalle c where c.parametro.parSimbolo = '"+ catalogo +"'");
            //Query query = session.createQuery(
              //      "select c from ParametroDetalle c ");
            
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }

    
    public List<ParametroDetalle> findAllCatalog() {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    "select c from ParametroDetalle c ");
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }    
    
	public Boolean verificaEstudiante(ParametroDetalle p) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction trns = session.beginTransaction();
		Criteria crit = session.createCriteria(Estudiante.class);

		crit.add(Restrictions.eq("estudiante", p));
		List<Estudiante> pad = crit.list();

		return pad.size() > 0 ? true : false;
	}
	
}

