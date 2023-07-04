package caloryquiz.back.cal.player;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;


    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public Player save(Player player,PlayerOutcome outcome){
        player.setScore(outcome.getScore());
        return playerRepository.save(player);
    }

    public Player done(Player player, Integer score) {
        return playerRepository.done(player, score);
    }

    public ArrayList<Player> findAll() {
        return playerRepository.findAll();
    }

    public Optional<Player> findPlayerByKey(Long key) {
        return playerRepository.findPlayerByKey(key);
    }

    public Player findByNickName(String nickName) {
        return playerRepository.findByNickName(nickName);
    }
}
