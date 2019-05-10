package lt.bit.java2;

import com.google.gson.Gson;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length > 1) {
            String cmd = args[0];

            if (cmd.equals("-q")) {
                if (args.length != 4) {
                    System.err.println("Neteisingi parametrai");
                    return;
                }

                String name = args[1];
                int pageSize = Integer.parseInt(args[2]);
                int offset = Integer.parseInt(args[3]);

                cmdQuery(name, pageSize, offset);

            } else if (cmd.equals("-c")) {

                if (args.length != 7) {
                    System.err.println("Neteisingi parametrai");
                    return;
                }

                int empNo = Integer.parseInt(args[1]);
                String firstName = args[2];
                String lastName = args[3];
                String sex = args[4];
                LocalDate hireDate = LocalDate.parse(args[5]);
                LocalDate birthDate = LocalDate.parse(args[6]);

                cmdCreate(empNo, firstName, lastName, sex, hireDate, birthDate);
            }

        } else {
            System.err.println("Neteisingi parametrai");
        }
    }

    static Properties properties() throws IOException {

        Properties properties = new Properties();

        properties.load(Main.class.getClassLoader().getResourceAsStream("application.properties"));

        return properties;
    }

    static void cmdCreate(int empNo, String firstName, String lastName, String sex, LocalDate hireDate, LocalDate birthDate) throws IOException {
        DataSource dataSource = getHikariDataSource();

        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            // Pradedame tranzakcija
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO employees(emp_no, birth_date, first_name, last_name, gender, hire_date) " +
                    " VALUES(?,?,?,?,?,?)");

            statement.setInt(1, empNo);
            statement.setDate(2, Date.valueOf(birthDate));
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, sex);
            statement.setDate(6, Date.valueOf(hireDate));

            statement.execute();

            statement.setInt(1, empNo);
            statement.setDate(2, Date.valueOf(birthDate));
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, sex);
            statement.setDate(6, Date.valueOf(hireDate));

            statement.execute();


            statement.close();

            // uzbaigiame tranzakcija:
            connection.commit();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    static void cmdQuery(String name, int pageSize, int offset) throws IOException {

        List<Employee> employees = null;

        DataSource dataSource = getHikariDataSource();

        try {
            Connection connection = dataSource.getConnection();

            employees = query(connection, name, pageSize, offset);

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (employees != null) {
            employees.forEach(e -> System.out.println(e));
        }
    }


    static DataSource getDataSource() throws IOException {

        Properties properties = properties();

        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(url);
        mysqlDataSource.setUser(user);
        mysqlDataSource.setPassword(password);

        return mysqlDataSource;
    }

    static DataSource getHikariDataSource() throws IOException {

        Properties properties = properties();

        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);

        return new HikariDataSource(config);
    }


    static List<Employee> query(Connection connection, String name, int pageSize, int pageNumber) throws SQLException {
        if (pageNumber < 1) pageNumber = 1;
        if (pageSize < 5 || pageSize > 100) pageSize = 5;

        int offset = pageSize * (pageNumber - 1);


//        Statement statement = connection.createStatement();
//
//        ResultSet resultSet = statement.executeQuery(
//                "SELECT emp_no, first_name, last_name, hire_date, birth_date, gender FROM employees WHERE first_name LIKE \"%"
//                        + name + "%\" LIMIT " + pageSize + " OFFSET " + offset);

        PreparedStatement statement = connection.prepareStatement(
                "SELECT emp_no, first_name, last_name, hire_date, birth_date " +
                        " FROM employees " +
                        " WHERE first_name LIKE ?" +
                        " ORDER BY emp_no " +
                        " LIMIT ? OFFSET ?");

        statement.setString(1, "%" + name + "%");
        statement.setInt(2, pageSize);
        statement.setInt(3, offset);

        ResultSet resultSet = statement.executeQuery();

        List<Employee> result = new ArrayList<>();

        while (resultSet.next()) {

//            System.out.println(resultSet.getInt("emp_no") +
//                    " " + resultSet.getString("first_name") +
//                    " " + resultSet.getString("last_name") +
//                    " " + resultSet.getDate("hire_date"));

//            Map<String, Object> map = new HashMap<>();
//            map.put("emp_no", resultSet.getInt("emp_no"));
//            map.put("first_name", resultSet.getString("first_name"));
//            map.put("last_name", resultSet.getString("last_name"));
//            map.put("hire_date", resultSet.getDate("hire_date"));

            Employee employee = new Employee(resultSet.getInt("emp_no"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getDate("hire_date").toLocalDate(),
                    resultSet.getDate("birth_date").toLocalDate());

            result.add(employee);
        }

        statement.close();

        return result;
    }

    public String method(int x, int y) {

        Result result = new Result(x, y, 0);

        try {
            result.setZ(x / y);

        } catch (ArithmeticException e) {

            result.setError(e.getMessage());
        }

        Gson gson = new Gson();
        return gson.toJson(result);
    }
}
