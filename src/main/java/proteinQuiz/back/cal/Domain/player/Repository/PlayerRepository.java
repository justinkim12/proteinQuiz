package proteinQuiz.back.cal.Domain.player.Repository;

import proteinQuiz.back.cal.Domain.player.Player;
import proteinQuiz.back.cal.Domain.player.PlayerOutcome;

import java.util.List;

public interface PlayerRepository {

    Player save(Player player);

    List<PlayerOutcome> findAll();

    List<PlayerOutcome> findAll(Integer max_integer);

    Player findPlayerByKey(Long key);

    Player findByNickName(String nickName);

    void delete(String nickName);



    void initKey();

    Integer getRank(Long key);
}
