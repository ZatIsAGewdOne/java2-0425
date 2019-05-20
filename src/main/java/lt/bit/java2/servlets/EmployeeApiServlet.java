package lt.bit.java2.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lt.bit.java2.db.DBUtils;
import lt.bit.java2.db.entities.Employee;
import lt.bit.java2.db.EmployeeRepository;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
}
