package BanWord;

import BanWord.database.CurseWord;
import BanWord.database.CurseWordRepo;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MessageFilter extends ListenerAdapter {
    private final CurseWordRepo curseWordRepo;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        
        String message = event.getMessage().getContentRaw().trim();
        // 명령어(>로 시작)는 금지어 검열에서 제외
        if (message.startsWith(">ban") && Objects.requireNonNull(event.getMember()).hasPermission(Permission.ADMINISTRATOR)) return;
        if(Boolean.parseBoolean(String.valueOf(event.getAuthor().getId().equals("1011910096315498537"))))  return; ;

        String content = message.toLowerCase();
        // 특수기호/공백/숫자 제거 후 비교용 문자열
        String normalizedContent = normalize(content);
        String guildId = event.getGuild().getId();

        List<CurseWord> banned = curseWordRepo.getAllBanned(guildId);
        for (CurseWord bw : banned) {
            String target = bw.getWord();
            if (target == null || target.isEmpty()) continue;

            String normalizedTarget = normalize(target.toLowerCase());
            if (normalizedTarget.isEmpty()) continue;

            if (normalizedContent.contains(normalizedTarget)) {
                // 금지어 포함 메시지 삭제 + 안내 후 5초 뒤 안내 메시지 삭제
                event.getMessage().delete().queue();
                event.getChannel().sendMessage("금지어가 포함되어 메시지가 삭제되었습니다.")
                        .queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
                break;
            }
        }
    }

    // 문자만 남기고 소문자로 정규화
    // - 숫자/특수기호 제거
    // - 연속 중복 문자 축소
    private String normalize(String text) {
        if (text == null) return "";
        String t = text.toLowerCase();
        // 문자만 남기기
        t = t.replaceAll("[^\\p{L}]", "");
        // 한글 자모(낱자) 제거 (호환 자모 포함)
        t = t.replaceAll("[\\p{InHangul_Jamo}\\p{InHangul_Compatibility_Jamo}]", "");
        // 연속 중복 문자 하나로 축소
        t = t.replaceAll("(\\p{L})\\1+", "$1");
        return t;
    }
}

