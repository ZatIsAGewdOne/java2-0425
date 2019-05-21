package lt.bit.java2.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lt.bit.java2.db.DBUtils;
import lt.bit.java2.db.Gender;
import lt.bit.java2.db.entities.Employee;
import lt.bit.java2.db.EmployeeRepository;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@WebServlet("/api/employee")
public class EmployeeApiServlet extends HttpServlet {

    ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new Jdk8Module())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer empNo = Integer.parseInt(req.getParameter("emp_no"));

        EntityManager em = DBUtils.getEntityManager();

        Employee employee = em.find(Employee.class, empNo);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        objectMapper.writeValue(resp.getWriter(), employee);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        EntityManager em = DBUtils.getEntityManager();

        try {
            Employee e = new Employee();
            e.setEmpNo(Integer.parseInt(req.getParameter("emp_no")));
            e.setFirstName(req.getParameter("first_name"));
            e.setLastName(req.getParameter("last_name"));
            e.setBirthDate(LocalDate.parse(req.getParameter("birth_date")));
            e.setHireDate(LocalDate.parse(req.getParameter("hire_date")));
            e.setGender(Gender.fromValue(req.getParameter("gender")));

            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();

            objectMapper.writeValue(resp.getWriter(), e);

        } catch (Exception e) {
            try {
                em.getTransaction().rollback();
                objectMapper.writeValue(resp.getWriter(), Collections.singletonMap("error", e.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } finally {
            if (em.isOpen()) em.close();
        }
    }
}
