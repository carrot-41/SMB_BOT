package BanWord.database;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CurseWordEntity extends MongoRepository<CurseWord, String> {
    // guildId와 word로 금지어 찾기 (banned 상태와 무관)
    Optional<CurseWord> findByGuildIdAndWordIgnoreCase(String guildId, String word);
    boolean existsByGuildIdAndWordIgnoreCase(String guildId, String word);
    
    // 활성화된 금지어 해제
    Optional<CurseWord> findByGuildIdAndWordIgnoreCaseAndBannedTrue(String guildId, String word);
    Optional<CurseWord> findByGuildIdAndWordIgnoreCaseAndBannedIsFalse(String guildId, String word);

    List<CurseWord> findAllByGuildIdAndBannedTrue(String guildId);
}
