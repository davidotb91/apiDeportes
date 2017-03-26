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
import Entity.LugarGeografico;
import Entity.Medico;
import Entity.Paciente;
import Entity.ParametroDetalle;

public class LugarGeograficoDao extends AbstractDao<LugarGeografico> {

    public LugarGeograficoDao() {
        super(LugarGeografico.class);
    }
    
   
    public List<LugarGeografico> findAllByUser() {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    "select c from LugarGeografico c");
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }
    

	
}

