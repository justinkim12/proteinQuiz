package caloryquiz.back.cal.player;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryPlayerRepository implements playerRepository{
    private static final Map<String, Player> store = new ConcurrentHashMap<>(); //static

    @Override
    public Player save(Player player) {
        store.put(player.getEmail(), player);
        return player;
    }

    @Override
    public Player done(Player player, Integer score) {
        player.setScore(score);
        return player;
    }


}
