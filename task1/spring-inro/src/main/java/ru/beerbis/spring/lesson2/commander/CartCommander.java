package ru.beerbis.spring.lesson2.commander;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.beerbis.spring.lesson2.Cart;
import ru.beerbis.spring.lesson2.ProductRepository;

import java.time.Period;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class CartCommander {
    private static final Pattern PATTERN = Pattern.compile("\\s+");
    private final ProductRepository goods;

    public CartCommander(ProductRepository goods) {
        this.goods = goods;
    }

    public Cart Command(String command, Cart cart) {
        var words = PATTERN.split(command);
        if (words.length == 0) return cart;

        switch (words[0].toLowerCase()) {
            case "goods":
                if (words.length > 1) {
                    goods.getList().stream().filter(it -> it.getName().contains(words[1])).forEach(it -> System.out.println(it));
                } else {
                    goods.getList().forEach(it -> System.out.println(it));
                }
                break;
            case "loose":
                return null;
            case "list":
                if (!checkCart(cart)) break;
                for (var item : cart) System.out.println(item);
                break;
            case "add":
                if (!checkCart(cart)) break;
                if (words.length < 2) {
                    System.out.println("ERROR: not enough parameters");
                    break;
                }
                Integer id = null;
                try {
                    id = Integer.parseInt(words[1]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    break;
                }
                goods.getProduct(id).ifPresentOrElse(
                        cart::put,
                        () -> System.out.println("no such goods id"));
                break;
            default:
                System.out.println("I do not know... may be.");
        }

        return cart;
    }

    public String getHelpDescription() {
        return
                "loose              - to loose a cart \n" +
                "list               - to see a goods in \n" +
                "add id             - to put a good into a cart \n" +
                "goods [name part]  - list all possible goods, with id, name and price";
    }

    private boolean checkCart(Cart cart) {
        if (cart != null) return true;
        System.out.println("where did you loose your cart, man?!.");
        return false;
    }
}
