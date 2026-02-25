package Warn;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface WarnEntity extends MongoRepository<WarnCount,String> {
    //guildId와 userId로 경고 횟수 검색
    Optional<WarnCount> findByGuildIdAndUserId(String guildId,String userId);
    Optional<WarnCount> findByGuildIdAndUserIdAndMuteTrue(String guildId, String userId, boolean mute);
    //Optional<WarnCount> findbyGuildIdAndUserIdAndmuteFalse(String guildId, String userId, boolean mute);
}
