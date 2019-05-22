package lt.bit.java2.db;

import lombok.val;
import lt.bit.java2.listeners.AppListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.function.Function;

public class DBUtils {

    static Logger logger = LoggerFactory.getLogger(DBUtils.class);


//    static private Properties properties() throws IOException {
//
//        Properties properties = new Properties();
//
//        properties.load(DBUtils.class.getClassLoader().getResourceAsStream("application.properties"));
//
//        return properties;
//    }

    static private EntityManagerFactory entityManagerFactory;

    static private ThreadLocal<EntityManager> threadLocal;

    static {
        logger.info("Static Initialization start");
        try {
            threadLocal = new ThreadLocal<>();
            entityManagerFactory = Persistence.createEntityManagerFactory("employee-persistence-unit");

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            logger.info("Static Initialization done.");
        }
    }

    static private EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    static public EntityManager getEntityManager() {
        EntityManager em = threadLocal.get();
        if (em == null || !em.isOpen()) {
            em = getEntityManagerFactory().createEntityManager();
            threadLocal.set(em);
        }
        return em;
    }

    static public <E> E executeInTransaction(Function<EntityManager, E> action) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            E e = action.apply(em);
            em.getTransaction().commit();
            return e;

        } catch (Exception e) {
            e.printStackTrace();

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DBException(e);

        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    static public class DBException extends RuntimeException {
        public DBException(String message) {
            super(message);
        }

        public DBException(Throwable cause) {
            super(cause);
        }
    }
}
