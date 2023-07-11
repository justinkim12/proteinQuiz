package caloryquiz.back.cal.Domain.player.Repository;

import caloryquiz.back.cal.Domain.player.Player;
import caloryquiz.back.cal.Domain.player.PlayerOutcome;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PlayerRepository {

    Player save(Player player);

    List<PlayerOutcome> findAll();

    Optional<Player> findPlayerByKey(Long key);

    Player findByNickName(String nickName);

    void delete(String nickName);

    void initKey();
}
