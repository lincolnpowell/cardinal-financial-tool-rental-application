package org.cardinalfinancial.toolrentalapplication.util.connection;

import java.util.Properties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.cardinalfinancial.toolrentalapplication.entity.RentalCharge;
import org.cardinalfinancial.toolrentalapplication.entity.Tool;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.SessionFactory;

/*
The More You Know -> Explore eager and lazy initialization implementations in concurrent applications and dig deeper
into the combined technique known as the Lazy Initialization Holder Class Idiom!
 */

public class HibernateSession {
    private static final Logger LOGGER = LogManager.getLogger(HibernateSession.class);

    private SessionFactory sessionFactory;

    private HibernateSession() {
        LOGGER.traceEntry("Initializing Hibernate Session configuration");
        try {
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "org.h2.Driver");
            properties.put(Environment.URL, "jdbc:h2:mem:tool-rental-application;DB_CLOSE_DELAY=-1");
            properties.put(Environment.USER, "");
            properties.put(Environment.PASS, "");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
            properties.put(Environment.SHOW_SQL, "false");
            properties.put(Environment.FORMAT_SQL, "true");
            properties.put(Environment.HBM2DDL_AUTO, "create-drop");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            Configuration configuration = new Configuration()
                    .setProperties(properties)
                    .addAnnotatedClass(RentalCharge.class)
                    .addAnnotatedClass(Tool.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            LOGGER.fatal("", e);
        } finally {
            LOGGER.traceExit("Hibernate Session configuration initialization completed");
        }
    }

    private static class HibernateSessionHelper {
        private static final HibernateSession HIBERNATE_SESSION = new HibernateSession();
    }

    public static SessionFactory getInstance() {
        LOGGER.trace("Retrieving SessionFactory instance");
        return HibernateSessionHelper.HIBERNATE_SESSION.sessionFactory;
    }
}
