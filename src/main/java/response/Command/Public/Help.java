package response.Command.Public;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import response.Util.EmbedUtil;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Help {
    //help.md를 읽어오기
    public static void readhelp(MessageReceivedEvent messageReceivedEvent){
        EmbedUtil embedUtil = new EmbedUtil(messageReceivedEvent);
        InputStream is = Help.class
                .getClassLoader()
                .getResourceAsStream("help.md");
        if (is == null) {
            throw new RuntimeException("파일을 찾을 수 없습니다.");
        }
        try {
            String help = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            embedUtil.Embed("도움말", Color.cyan,help);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
