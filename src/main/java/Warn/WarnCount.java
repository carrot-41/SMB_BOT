package Warn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection="WarnCnt")
public class WarnCount {
    @Id
    private String id;
    private String guildId, userId;
    private int warncnt=0;
    private boolean mute = false;

    //경고 생성자
    public WarnCount(String guildId,String userId, int warncnt,boolean mute){
        this.guildId = guildId;
        this.userId = userId;
        this.warncnt = warncnt;
        this.mute = mute;
    }

    //경고 새로 받을 시
    public WarnCount(String guildId,String userId){
        this(guildId, userId,0,false);
    }

    // 경고 1회 증가
    public void increaseWarn() {
        warncnt++;
    }

    // 경고 1회 감소
    public void decreaseWarn() {
        if (warncnt > 0) {
            warncnt--;
        }
    }
}

