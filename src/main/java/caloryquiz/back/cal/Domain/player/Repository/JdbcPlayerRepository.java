package caloryquiz.back.cal.Domain.player.Repository;

import caloryquiz.back.cal.Domain.food.Food;
import caloryquiz.back.cal.Domain.player.Player;
import caloryquiz.back.cal.Domain.player.PlayerOutcome;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Repository
public class JdbcPlayerRepository implements PlayerRepository{

    private final JdbcTemplate template;

    public JdbcPlayerRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Player save(Player player) {
        String sql ="insert into player(nickname,score) values(?,?)";
        template.update(sql, player.getNickName(), player.getScore());
        return player;
    }

    @Override
    public List<PlayerOutcome> findAll() {
        String sql = "select * from player order by score";
        return template.query(sql,playerOutcomeRowMapperMapper);
    }

    @Override
    public Optional<Player> findPlayerByKey(Long key) {
        String sql = "select * from player where playerKey = ?";
        return Optional.ofNullable(template.queryForObject(sql,playerMapper,key));
    }

    @Override
    public Player findByNickName(String nickName) {
        String sql = "select * from player where nickname = ?";
        return (template.queryForObject(sql, Player.class, nickName));
    }

    @Override
    public void delete(String nickName) {
        String sql = "delete from player where nickName = ?";
        template.update(sql, nickName);
    }

    // 맵퍼 람다 처리
    static RowMapper<PlayerOutcome> playerOutcomeRowMapperMapper = (rs, rowNum) ->
            new PlayerOutcome(rs.getString("nickname"),rs.getInt("score"));

    static RowMapper<Player> playerMapper = (rs, rowNum) ->
            new Player(rs.getString("nickname"), rs.getInt("score"), 0, new ArrayList<>());

    //AutoIncrement가 초기화 되지 않는 이슈를 잡는 용도
    @Override
    public void initKey(){
       String sql = "ALTER TABLE PLAYER  ALTER COLUMN playerKey RESTART WITH 1;";
       template.execute(sql);
    }

}
