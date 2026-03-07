package response.Command;

import CurseWordDB.database.CurseWordRepo;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import response.EmbedUtil;
import response.ListenCommend;

import java.awt.*;

public class CursWord {
    private static CurseWordRepo curseWordRepo;
    // 금지어 관련 커맨드 처리
    public static void CurseWordCommand(MessageReceivedEvent messageReceivedEvent, String[] args) {
        EmbedUtil embedUtil = new EmbedUtil(messageReceivedEvent);
        String word = (args.length > 1 ? args[1] : "").toLowerCase();
        String OnOff = (args.length > 2 ? args[2] : "").toLowerCase();
        String guildId = messageReceivedEvent.getGuild().getId();

        //금지어 등록 이외의 명령어 처리
        switch (word) {
            case "목록":
            case "리스트":
            case "ls":
            case "list":
                String list = curseWordRepo.listWords(guildId);
                embedUtil.Embed("금지어 목록", Color.green,list);
                break;

            default:
                if(word.isEmpty()&&OnOff.isEmpty()){
                    embedUtil.Embed("금지어",Color.RED , "잘못된 명령어 입력입니다.\n"+
                            " 도움말은 >help를 사용하여 확인하실 수 있습니다.",true);
                }

                return;
        }

        // 금지어 등록/해제/재활성화 처리
        String title;
        String comment = "";
        Color color = new Color(255,255,255);
        switch (OnOff) {
            case "추가":
            case "n":
            case "new":
                if (curseWordRepo.exists(guildId, word)) {
                    title = "금지어 등록 오류";
                    comment = word+"은(는) 이미 등록된 금지어입니다.";
                    color = Color.RED;

                } else {
                    curseWordRepo.ban(guildId, word);
                    title ="금지어 등록 성공";
                    comment = "금지어 " + word + "(이)가 등록되었습니다.";
                    color = Color.green;
                }
                break;

            case "해제":
            case "off":
                if (!curseWordRepo.exists(guildId, word)) {
                    title = "금지어 해제 오류";
                    comment = "현재 "+word+"은(는) 금지어로 설정되지 않았습니다.";
                    color = Color.RED;
                }
                else{
                    curseWordRepo.unban(guildId, word);
                    title = "금지어 해제 완료";
                    comment = "금지어 " + word + "(이)가 해제되었습니다.";
                    color = Color.GREEN;
                }
                break;

            case "활성화":
            case "on":
                if (!curseWordRepo.exists(guildId, word)) {
                    title = "금지어 해제 오류";
                    comment = "현재 "+word+"은(는) 금지어로 설정되지 않았습니다.";
                    color = Color.RED;
                }
                else{
                    curseWordRepo.reban(guildId, word);
                    title = "금지어 활성화 완료";
                    comment = "금지어 " + word + "(이)가 활성화되었습니다.";
                    color = Color.GREEN;
                }
                break;

            default:
                title = "알 수 없는 명령";
                comment = OnOff+"(은)는 알 수 없습니다.\n"+
                        "도움말은 >help를 사용하여 확인하실 수 있습니다.";
                color = Color.red;
        }
        embedUtil.Embed(title, color,comment,true,3);
    }
}
