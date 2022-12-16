import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBot extends TelegramLongPollingBot {
    // Your own telegram token (see @BotFather)
    private static final String BOT_TOKEN = Token.BOT_TOKEN;
    private static final String BOT_USERNAME = "NasaPictureTestBot";

    public TelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            long chatId = update.getMessage().getChatId();
            String command = update.getMessage().getText();
            if (command != null) {
                switch (command.trim().toLowerCase()) {
                    case "/help", "help" -> sendMessage(
                            chatId,
                            "Hello, I'm a NASA bot, sending a link to a new image upon request /give. " +
                                    "The link is updated once a day!"
                    );
                    case "/give" -> sendMessage(chatId, NasaApi.getPictureUrl());
                    default -> sendMessage(chatId, "Unknown command!");
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    private void sendMessage(long chatId, String text) {
        if (text != null && !text.isEmpty()) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(text);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

}
