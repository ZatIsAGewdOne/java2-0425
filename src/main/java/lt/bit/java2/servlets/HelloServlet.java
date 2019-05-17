package lt.bit.java2.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/demo/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");

        if ("Jonas".equals(name)) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/demo/labas");
            dispatcher.forward(req, resp);
            return;
        }

        if ("Ona".equals(name)) {
            resp.sendRedirect("http://google.com");
            return;
        }

        if (name == null) name = "Pasauli";

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.print("{\"name\":" + "\"" + name + "\"}");
    }
}
