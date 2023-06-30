package caloryquiz.back.cal.player;

import java.util.ArrayList;
import java.util.Optional;

public interface PlayerRepository {

    Player save(Player player);
    Player done(Player player,Integer score); //퀴즈 끝났을때

    ArrayList<Player> findAll();

    Optional<Player> findPlayerByKey(Long key);

    Player findByNickName(String nickName);

}
