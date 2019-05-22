package lt.bit.java2.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lt.bit.java2.db.EmployeeRepository;
import lt.bit.java2.db.Gender;
import lt.bit.java2.db.entities.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;

@WebServlet("/api/employee")
public class EmployeeApiServlet extends HttpServlet {

    private ObjectMapper objectMapper;

    private EmployeeRepository employeeRepository;

    @Override
    public void init() throws ServletException {
        objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new Jdk8Module())
                .registerModule(new Hibernate5Module())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        employeeRepository = new EmployeeRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        Integer empNo = Integer.parseInt(req.getParameter("emp_no"));
        String salary = req.getParameter("salary");

        Employee employee = salary == null ? employeeRepository.get(empNo) : employeeRepository.getWithSalary(empNo);

        objectMapper.writeValue(resp.getWriter(), employee);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        try {
            Employee employee = new Employee();
            employee.setEmpNo(Integer.parseInt(req.getParameter("emp_no")));
            employee.setFirstName(req.getParameter("first_name"));
            employee.setLastName(req.getParameter("last_name"));
            employee.setBirthDate(LocalDate.parse(req.getParameter("birth_date")));
            employee.setHireDate(LocalDate.parse(req.getParameter("hire_date")));
            employee.setGender(Gender.fromValue(req.getParameter("gender")));

            employeeRepository.create(employee);

            objectMapper.writeValue(resp.getWriter(), employee);

        } catch (Exception e) {
            try {
                objectMapper.writeValue(resp.getWriter(), Collections.singletonMap("error", e.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
