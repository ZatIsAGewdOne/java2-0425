package lt.bit.java2.servlets

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/demo")
class DemoServlet : HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.contentType = "text/html"
        resp.characterEncoding = "UTF-8"

        val name = req.getParameter("name") ?: "Pasauli"

        val printer = resp.writer
        printer.println("<h1>Labukas ${name} :)</h1>")
    }
}