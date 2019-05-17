package lt.bit.java2.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lt.bit.java2.db.Employee;
import lt.bit.java2.db.EmployeeRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/employee")
public class EmployeeApiServlet extends HttpServlet {

    EmployeeRepository employeeRepository;
    ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        employeeRepository = new EmployeeRepository();
        objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new Jdk8Module())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageSizeParam = req.getParameter("pageSize");
        String offsetParam = req.getParameter("offset");

        int pageSize = 10;
        try {
            pageSize = Integer.parseInt(pageSizeParam);
        } catch (NumberFormatException e) {}

        int offset = 0;
        try {
            offset = Integer.parseInt(offsetParam);
        } catch (NumberFormatException e) {}

        List<Employee> employees = employeeRepository.list("", pageSize, offset);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        objectMapper.writeValue(resp.getWriter(), employees);
    }
}
