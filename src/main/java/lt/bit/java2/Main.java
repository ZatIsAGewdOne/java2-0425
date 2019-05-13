package lt.bit.java2;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lt.bit.java2.db.Employee;
import lt.bit.java2.db.EmployeeRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {
        new Main().start(args);
    }

    void start(String[] args) throws IOException {

        LOGGER.finest("Finest");
        LOGGER.finer("Finer");
        LOGGER.fine("Fine");
        LOGGER.info("Info");
        LOGGER.warning("Warning");
        LOGGER.severe("Severe");

        LOGGER.log(Level.CONFIG, "Config");
        LOGGER.log(Level.OFF, "Off");
        LOGGER.log(Level.ALL, "All");

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

                cmdCreate(new Employee(empNo, firstName, lastName, hireDate, birthDate, sex));
            }

        } else {
            System.err.println("Neteisingi parametrai");
        }
    }


    EmployeeRepository employeeRepository = new EmployeeRepository();

    void cmdQuery(String name, int pageSize, int pageNumber) throws IOException {

        List<Employee> employees = employeeRepository.list(name, pageSize, pageNumber);
        employees.forEach(System.out::println);
    }


    void cmdCreate(Employee employee) throws IOException {
        employeeRepository.create(employee);
    }

}
