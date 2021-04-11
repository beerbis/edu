import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet(name = "ExampleHttpServlet", urlPatterns = "/http_servlet/*")
public class ExampleHttpServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(ExampleHttpServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("New GET request");

        resp.getWriter().printf("<h1>New GET request</h1>");
        getServletContext().getRequestDispatcher( "/header.html").include(req, resp);
        resp.getWriter().printf("<h1>getContextPath = " + req.getContextPath() + "</h1>");
        resp.getWriter().printf("<h1>getPathInfo = " + req.getPathInfo() + "</h1>");

        resp.getWriter().printf("<h1>getQueryString = " + req.getQueryString() + "</h1>");
        Cookie[] cookies = req.getCookies();
        Stream<Cookie> cookieStream = cookies == null ? Stream.empty() : Arrays.stream(cookies);
        resp.getWriter().printf("<h1>getCookies = " + cookieStream
                .map(c -> c.getName() + " = " + c.getValue() + "<br/>")
                .collect(Collectors.joining(", ")) + "</h1>");
        Cookie cookie = new Cookie("q" + new Random().nextInt(), "noValue");
        cookie.setMaxAge(20);
        resp.addCookie(cookie);

//        resp.sendRedirect("http://ya.ru");
        Integer foo1 = (Integer) req.getAttribute("foo");
        foo1 = foo1 == null ? 0 : foo1;
        req.setAttribute("foo", foo1 + 1);
        resp.getWriter().printf("<h1>request.attribute[foo]= " + foo1);

        Integer foo2 = (Integer) getServletContext().getAttribute("foo");
        foo2 = foo2 == null ? 0 : foo2;
        getServletContext().setAttribute("foo", foo2 + 1);
        resp.getWriter().printf("<h1>context.attribute[foo]= " + foo2);


        getServletContext().getRequestDispatcher("/basic_servlet").include(req, resp);
        resp.getWriter().printf("<h1>After: New GET request</h1>");
        getServletContext().getRequestDispatcher("/fee/index.html").include(req, resp);

        //редиректим на статический тупо-файл

        resp.setStatus(500);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("New POST request");

        resp.getWriter().printf("<h1>New POST request</h1>");
    }
}