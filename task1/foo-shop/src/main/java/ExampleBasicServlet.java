import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.Enumeration;
import java.util.StringJoiner;

@WebServlet(name = "BasicServlet", urlPatterns = "basic_servlet")
public class ExampleBasicServlet implements Servlet {

    private static Logger logger = LoggerFactory.getLogger(ExampleBasicServlet.class);

    private transient ServletConfig config;

    // Метод вызывается контейнером после того как был создан класс сервлета
    @Override
    public void init(ServletConfig config) throws ServletException {
        // Сохраняем полученную от сервера конфигурацию
        this.config = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    // Метод вызывается для каждого нового HTTP запроса к данному сервлету
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        logger.info("New request");

        // получаем объект типа BufferedWriter и пишем в него ответ на пришедший запрос
        res.getWriter().println("<h1>Servlet content</h1>");

        res.getWriter().println("<h1>attributes: " + attrs(req) + "</h1>");
    }

    private String attrs(ServletRequest request) {
        Enumeration<String> names = request.getAttributeNames();
        StringJoiner sj = new StringJoiner("<br/>");
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            sj.add(name + " = " + request.getAttribute(name).toString());
        }

        return sj.toString();
    }

    @Override
    public String getServletInfo() {
        return "BasicServlet";
    }

    // При завершении работы веб приложения, контейнер вызывает этот метод для всех сервлетов из этого приложения
    @Override
    public void destroy() {
        logger.info("Servlet {} destroyed", getServletInfo());
    }
}
