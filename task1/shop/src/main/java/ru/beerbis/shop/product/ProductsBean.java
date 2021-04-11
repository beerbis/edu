package ru.beerbis.shop.product;

import ru.beerbis.shop.ShopUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ProductsBean implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ShopUtils.putBean(sce.getServletContext(), new ProductManagerImpl(), ProductManager.class);
    }
}
