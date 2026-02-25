package BanWord.database;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor // MongoDB 매핑을 위한 기본 생성자
@Document(collection = "words")
public class CurseWord {
    @Id
    private String id;
    private String word;
    private String guildId;
    private boolean banned = false; // 사용 여부 판단

    public CurseWord(String guildId, String word, boolean banned) {
        this.guildId = guildId;
        this.word = word;
        this.banned = banned;
    }

    // 편의 생성자 (새 금지어 등록용)
    public CurseWord(String word) {
        this.word = word;
        this.banned = true;
    }

}
