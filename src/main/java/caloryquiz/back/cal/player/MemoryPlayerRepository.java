package caloryquiz.back.cal.player;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryPlayerRepository implements PlayerRepository {
    private static final Map<Long, Player> store = new ConcurrentHashMap<>(); //static
    private static Long key =0L;

    @Override
    public Player save(Player player) {
        player.setKey(key++);
        store.put(player.getKey(), player);
        return player;
    }

    @Override
    public Player done(Player player, Integer score) {
        player.setScore(score);
        return player;
    }

    @Override
    public ArrayList<Player> findAll() {
        return new ArrayList<>(store.values());
    }


    @Override
    public Optional<Player> findPlayerByKey(Long key) {
        return findAll().stream().filter(m -> m.getKey().equals(key)).findFirst();
    }

    @Override
    public Player findByNickName(String nickName) {
        return findAll().stream().filter(m -> m.getNickName().equals(nickName)).findFirst().get();
    }
}
