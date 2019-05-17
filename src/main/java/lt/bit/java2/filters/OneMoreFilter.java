package lt.bit.java2.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "First", urlPatterns = "/demo/*")
public class OneMoreFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        System.err.println("OneMoreFilter");

        res.setCharacterEncoding("UTF-8");

        res.getWriter().print("<h2>Filtras #2 - start</h2>");

        chain.doFilter(req, res);

        res.getWriter().print("<h2>Filtras #2 - end</h2>");
    }
}
