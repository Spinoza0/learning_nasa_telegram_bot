public class Main {
    public static void main(String[] args) {
        System.out.println("Telegram bot - start");
        try {
            new TelegramBot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
