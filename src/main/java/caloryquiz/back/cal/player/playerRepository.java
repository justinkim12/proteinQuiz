package caloryquiz.back.cal.player;

public interface playerRepository {

    Player save(Player player);
    Player done(Player player,Integer score); //퀴즈 끝났을때

}
