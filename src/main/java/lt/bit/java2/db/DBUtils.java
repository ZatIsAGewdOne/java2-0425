package lt.bit.java2.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DBUtils {


    static private Properties properties() throws IOException {

        Properties properties = new Properties();

        properties.load(DBUtils.class.getClassLoader().getResourceAsStream("application.properties"));

        return properties;
    }

    static private EntityManagerFactory entityManagerFactory;

    static private EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("employee-persistence-unit");
        }
        return entityManagerFactory;
    }

    static public EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }
}
