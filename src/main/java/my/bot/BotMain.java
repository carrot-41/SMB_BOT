package my.bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import response.ListenCommend;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import response.*;
import BanWord.MessageFilter;
import java.util.EnumSet;

@SpringBootApplication(scanBasePackages = {"my.bot", "response", "BanWord", "Warn"})
@EnableMongoRepositories(basePackages = {"BanWord.database", "Warn"})
public class BotMain {
    public static void main(String[] args) {
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_RESET = "\u001B[0m";

        ApplicationContext context = SpringApplication.run(BotMain.class, args);

        BotToken botToken = new BotToken();
        GatwayIntents intents = new GatwayIntents();
        EnumSet<GatewayIntent> intent = intents.getIntents();

        String token = botToken.getBotToken();

        ListenCommend commandListener = context.getBean(ListenCommend.class);
        MessageFilter messageFilter = context.getBean(MessageFilter.class);

        JDABuilder.createDefault(token).
                enableIntents(intent).
                setActivity(Activity.competing("도움말은 >help")).
                setStatus(OnlineStatus.DO_NOT_DISTURB).
                addEventListeners(commandListener, messageFilter,new Test()).
                build();

        System.out.println(ANSI_CYAN+"bot booting successful"+ANSI_RESET);
    }

}