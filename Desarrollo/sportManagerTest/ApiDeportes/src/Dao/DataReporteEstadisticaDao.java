package Dao;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import Util.HibernateUtil;
import Entity.DataReporteEstadistica;
import Entity.Docente;
import Entity.Estudiante;
import Entity.ParametroDetalle;
import Entity.DataReporteEstadistica;
import Entity.Tema;

public class DataReporteEstadisticaDao extends AbstractDao<DataReporteEstadistica> {

    public DataReporteEstadisticaDao() {
        super(DataReporteEstadistica.class);
    }   
    
    public List<Object[]> obtenerInformacionEstadisticas(Date fechaInicial, Date fechaFinal, Estudiante estudiante) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    " select tema.parametroDetalle.padDesCorta, count(*) from Tema as tema group by tema.parametroDetalle.padDesCorta ");
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }    
    
    public List<Object[]> obtenerInformacionAbstract(Date fechaInicial, Date fechaFinal, Estudiante estudiante) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trns = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    " select (select p.parValor from Param p where parNemonico = 'PANIO') as anio , "
            		+ " tema.temDescripcion, tema.docente.docNombres, tema.docente.docApellidos, "
            		+ " seguimientoTesis.segDocumento, seguimientoTesis.segPathDocumento, "
            		+ " seguimientoTesis.segObservaciones "
            		+ " from Tema as tema, SeguimientoTesis as seguimientoTesis "            		
            		+ " where tema.teId = seguimientoTesis.tema.teId "
            		+ " order by tema.temDescripcion "            		
            		);            
                        
            return query.list();
            
        } catch (Exception e) {
            return null;
        }
    }    
    
}

