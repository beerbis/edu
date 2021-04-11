package ru.beerbis.shop.servlet;

import ru.beerbis.shop.ShopUtils;
import ru.beerbis.shop.product.Product;
import ru.beerbis.shop.product.ProductManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet(name = "cart", urlPatterns = "/cart")
public class CartServlet extends HttpServlet {
    private ProductManager productManager;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productManager = ShopUtils.getBean(getServletContext(), ProductManager.class);
        Objects.requireNonNull(productManager, "productManager");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "text/html; charset=utf-8");
        resp.getWriter().println("<title>Корзинка</title>");
        resp.getWriter().println("<body>");
        resp.getWriter().println(
                productManager.get().stream().map(Product::toString).collect(Collectors.joining("<br>"))
        );
        resp.getWriter().println("</body>");
    }
}
