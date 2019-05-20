package lt.bit.java2.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lt.bit.java2.Main;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class DBUtils {

    static private DataSource dataSource = null;

    static private Properties properties() throws IOException {

        Properties properties = new Properties();

        properties.load(Main.class.getClassLoader().getResourceAsStream("application.properties"));

        return properties;
    }


    static public DataSource getDataSource() throws IOException {

        if (dataSource == null) {

            Properties properties = properties();

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");
            String driver = properties.getProperty("db.driver");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(user);
            config.setPassword(password);
            config.setDriverClassName(driver);

            dataSource = new HikariDataSource(config);
        }

        return dataSource;
    }
}
