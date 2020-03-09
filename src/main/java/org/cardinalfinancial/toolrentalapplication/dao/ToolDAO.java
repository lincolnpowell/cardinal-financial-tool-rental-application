package org.cardinalfinancial.toolrentalapplication.dao;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.cardinalfinancial.toolrentalapplication.entity.Tool;
import org.cardinalfinancial.toolrentalapplication.util.connection.HibernateSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class ToolDAO {
    private final Logger LOGGER = LogManager.getLogger(ToolDAO.class);

    public Tool getTool(String toolCode) {
        LOGGER.traceEntry("getTool(toolCode={})", toolCode);
        Tool tool = null;
        Session session = HibernateSession.getInstance().getCurrentSession();
        try {
            session.beginTransaction();
            tool = (Tool) session.createQuery("FROM Tool t JOIN FETCH t.rentalCharge WHERE t.toolCode = :toolCode")
                    .setParameter("toolCode", toolCode)
                    .uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (null != session.getTransaction()) {
                session.getTransaction().rollback();
            }
            LOGGER.fatal("", e);
        } finally {
            session.close();
        }
        return LOGGER.traceExit(tool);
    }
}
