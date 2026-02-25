package my.bot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BotToken  {
    private String DiscordBotToken;

    public BotToken() {
        settingToken();
    }

    public String getBotToken() {
        return DiscordBotToken;
    }

    private void settingToken() {
        try(InputStream input = getClass().getClassLoader().getResourceAsStream("Token.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            DiscordBotToken = properties.getProperty("BotToken");
            System.out.println("토큰 로딩 성공");
        } catch (IOException e) {
            System.out.println("토큰 로딩중 오류 방생");
            throw new RuntimeException(e);
        }
    }
}
