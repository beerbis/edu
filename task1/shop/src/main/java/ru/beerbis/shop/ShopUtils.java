package ru.beerbis.shop;

import javax.servlet.ServletContext;
import java.util.Objects;

final public class ShopUtils {
    private ShopUtils() {
    }

    public static <T> T getBean(ServletContext context, Class<T> clazz) {
        return clazz.cast(context.getAttribute(clazz.getName()));
    }

    public static <T> void putBean(ServletContext context, T bean, Class as) {
        Objects.requireNonNull(bean);
        Objects.requireNonNull(as);
        context.setAttribute(as.getName(), as.cast(bean));
    }
}
