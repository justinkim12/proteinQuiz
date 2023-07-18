//package caloryquiz.back.cal.Domain.player.Repository;
//
//import caloryquiz.back.cal.Domain.player.Player;
//import caloryquiz.back.cal.Domain.player.PlayerOutcome;
//import org.springframework.stereotype.Repository;
//
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//
////@Repository
//public class MemoryPlayerRepository implements PlayerRepository {
//    private static final Map<Long, Player> store = new ConcurrentHashMap<>(); //static
//    private static Long key =0L;
//
//    @Override
//    public Player save(Player player) {
//        player.setKey(key++);
//        store.put(player.getKey(), player);
//        return player;
//    }
//
//
//
//    @Override
//    public ArrayList<PlayerOutcome> findAll() {
////        return new ArrayList<>(store.values());
//        return null;
//    }
//
//
//    @Override
//    public Optional<Player> findPlayerByKey(Long key) {
////        return findAll().stream().filter(m -> m.getKey().equals(key)).findFirst();
//        return null;
//    }
//
//    @Override
//    public Player findByNickName(String nickName) {
////        return findAll().stream().filter(m -> m.getNickName().equals(nickName)).findFirst().get();
//        return null;
//    }
//
//    @Override
//    public void delete(String nickName) {
//
//    }
//}
