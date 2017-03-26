/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.proxy.HibernateProxy;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author leonel
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    public static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static <T> T unproxy(T entity) {
        if (entity == null) {
            return null;
        }

        if (entity instanceof HibernateProxy) {
            Hibernate.initialize(entity);
            entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
        }

        return entity;
    }
}
