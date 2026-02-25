package BanWord.database;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurseWordRepo {
    private final CurseWordEntity repo;

    public boolean exists(String guildId, String word) {
        return repo.existsByGuildIdAndWordIgnoreCase(guildId, word);
    }

    // 금지어 리스트를 문자열로 반환
    public String listWords(String guildId) {
        List<CurseWord> words = getAllBanned(guildId);
        if (words.isEmpty()) {
            return "등록된 금지어가 없습니다.";
        }
        return words.stream()
                .map(CurseWord::getWord)
                .sorted(String::compareToIgnoreCase)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
    }

    // 금지어 등록
    public void ban(String guildId, String word) {
        CurseWord bw = repo.findByGuildIdAndWordIgnoreCase(guildId, word)
                .orElse(new CurseWord(guildId, word, true));
        bw.setBanned(true);
        repo.save(bw);
        System.out.println("금지어 추가 완료");
    }

    // 금지어 해제
    public void unban(String guildId, String word) {
        boolean found = repo.findByGuildIdAndWordIgnoreCaseAndBannedTrue(guildId, word)
                .map(bw -> {
                    bw.setBanned(false);
                    repo.save(bw);
                    return true;
                })
                .orElse(false);
        if (found) {
            System.out.println("금지어 해제 완료");
        } else {
            System.out.println("해제할 금지어를 찾을 수 없습니다.");
        }
    }
    // 금지어 활성화
    public void reban(String guildId, String word) {
        boolean found = repo.findByGuildIdAndWordIgnoreCaseAndBannedIsFalse(guildId, word)
                .map(bw -> {
                    bw.setBanned(true);
                    repo.save(bw);
                    return true;
                })
                .orElse(false);
        
        if (found) {
            System.out.println("금지어 활성화 완료");
        } else {
            System.out.println("활성화할 금지어를 찾을 수 없습니다.");
        }
    }

    // 서버별 모든 금지어 가져오기
    public List<CurseWord> getAllBanned(String guildId) {
        return repo.findAllByGuildIdAndBannedTrue(guildId);
    }
}
