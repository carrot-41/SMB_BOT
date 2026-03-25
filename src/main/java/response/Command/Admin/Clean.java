package response.Command.Admin;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import response.Util.EmbedUtil;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Clean {
    //클리어 (>클린 [삭제할 메세지 숫자])
    public static void CleanCommand(MessageReceivedEvent messageReceivedEvent, @NotNull String[] args){
        EmbedUtil embedUtil = new EmbedUtil(messageReceivedEvent);
        String String_Cnt = (args.length > 1 ? args[1] : "").toLowerCase();

        //만약 숫자 입력을 안하면 기본값 2로
        if (String_Cnt.isEmpty()){
            String_Cnt = "2";
        }

        try {
            int Cnt = Integer.parseInt(String_Cnt) + 1; //+1은 사용자가 작성한 메세지를 제외하고 Cnt만큼지우기 위함이다

            if (Cnt <= 0 || Cnt >100) {
                messageReceivedEvent.getMessage().reply("숫자는 1 ~ 100 사이로 입력해주세요.").queue();
            }
            else{
                messageReceivedEvent.getChannel().getIterableHistory()
                        .takeAsync(Cnt) //메시지 가져오기
                        .thenAccept(messageReceivedEvent.getChannel()::purgeMessages);

                embedUtil.Embed("메시지 삭제", Color.RED,"메세지 "+Cnt+"개 만큼 삭제 했습니다.",true,2);
            }
        }catch (NumberFormatException e){
            System.out.println(e.getMessage()+"\n 문자를 숫자로 변환하려 함");
            messageReceivedEvent.getMessage().reply("숫자를 입력하세요").
                    queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
        }
    }
}
