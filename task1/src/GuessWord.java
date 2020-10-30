import java.util.Scanner;

public class GuessWord {
    final static private String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado", "broccoli", "carrot", "cherry", "garlic", "grape", "melon", "leak", "kiwi", "mango", "mushroom", "nut", "olive", "pea", "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};

    public static void main(String[] args) {
        String secret = ChooseSecret();
        System.out.println("Спойлер: " + secret + "\n");

        System.out.println("Ваша задача угадать загаданное компуктером буржуйское слово.\n" +
                "Гадать будем до победного конца без права на капитуляцию.\n" +
                "Компуктер будет выводить правильно угаданное начало слова.");
        System.out.print("\nПопытка первая: ");

        Scanner scanner = new Scanner(System.in);

        do {
            String userGuess = scanner.next().toLowerCase();
            if (secret.equals(userGuess)) {
                System.out.println("Конгратюлирую! Слово и правда было \"" + secret + "\"");
                break;
            }
            
            String goodPart = TakeMaskedPartialGoodness(userGuess, secret);
            if (!goodPart.isEmpty())
                System.out.println("Это было близко. Слово и правда: " + goodPart);

            System.out.print("Пробуй ещё: ");
        } while(true);

    }

    private static String TakeMaskedPartialGoodness(String guess, String secret) {
        String result = "";
        int maxComparableId = Math.min(guess.length(), secret.length());
        for (int i = 0; i < maxComparableId; i++){
            if (guess.charAt(i) != secret.charAt(i)) break;
            result += guess.charAt(i);
        }

        if (result.isEmpty()) return "";
        return AppendUpTo15(result);
    }

    private static String AppendUpTo15(String string) {
        final String maskChars = "###############";
        return string + maskChars.substring(string.length());
    }

    private static String ChooseSecret() {
        int idx = (int) (Math.random() * words.length);
        return words[idx];
    }
}
