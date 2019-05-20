package lt.bit.java2.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class DBUtils {


    static private Properties properties() throws IOException {

        Properties properties = new Properties();

        properties.load(DBUtils.class.getClassLoader().getResourceAsStream("application.properties"));

        return properties;
    }


//    static public DataSource getDataSource() throws IOException {
//
//        if (dataSource == null) {
//
//            Properties properties = properties();
//
//            String url = properties.getProperty("db.url");
//            String user = properties.getProperty("db.user");
//            String password = properties.getProperty("db.password");
//            String driver = properties.getProperty("db.driver");
//
//            HikariConfig config = new HikariConfig();
//            config.setJdbcUrl(url);
//            config.setUsername(user);
//            config.setPassword(password);
//            config.setDriverClassName(driver);
//
//            dataSource = new HikariDataSource(config);
//        }
//
//        return dataSource;
//    }

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
