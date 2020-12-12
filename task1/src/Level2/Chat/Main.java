package Level2.Chat;

public class Main {
    private static ChatLog chatLog;

    public static void main(String[] args) {
        chatLog = System.out::println;
        //тут мы просто уповаем на то, что в ходе конструирования колбэк не будет дёргаться, а если будет - заткнём затычкой.
        //  главно не передать как static reference, а то не `this.gui.appendChatLog` будет замкнут, а его старое значение(println)
        chatLog = new ChatGui(message -> chatLog.appendChatLog(message));
    }
}
