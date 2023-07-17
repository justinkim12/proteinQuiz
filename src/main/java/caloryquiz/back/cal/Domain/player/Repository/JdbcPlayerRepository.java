package caloryquiz.back.cal.Domain.player.Repository;

import caloryquiz.back.cal.Domain.food.Food;
import caloryquiz.back.cal.Domain.player.Player;
import caloryquiz.back.cal.Domain.player.PlayerOutcome;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcPlayerRepository implements PlayerRepository{

    private final JdbcTemplate template;

    public JdbcPlayerRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Player save(Player player) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql ="insert into player(nickname,score) values(?,?)";
//        template.update(sql, player.getNickName(), player.getScore());
        template.update(conn -> {
            PreparedStatement pstmt = conn.prepareStatement(
                    sql, new String[]{"playerKey"});
            pstmt.setString(1, player.getNickName());
            pstmt.setInt(2, player.getScore());
            return pstmt;
        }, keyHolder);

        player.setKey(keyHolder.getKey().longValue());
        return player;
    }

    @Override
    public List<PlayerOutcome> findAll() {
        String sql = "select * from player order by score desc";
        return template.query(sql,playerOutcomeRowMapperMapper);
    }

    @Override
    public List<PlayerOutcome> findAll(Integer max_integer) {
        String sql = "select * from player order by score desc limit ?";
        return template.query(sql,playerOutcomeRowMapperMapper, max_integer);
    }

    @Override
    public Player findPlayerByKey(Long key) {
        String sql = "select * from player where playerKey = ?";
        return template.queryForObject(sql,playerMapper,key);
    }

    @Override
    public Player findByNickName(String nickName) {
        String sql = "select * from player where nickname = ?";
        try {
            return (template.queryForObject(sql, playerMapper, nickName));
        }catch (IncorrectResultSizeDataAccessException e){
            return null;
        }
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

    @Override
    public Integer getRank(Long key) {
        String sql = "SELECT rank\n" +
                "FROM (\n" +
                "  SELECT playerKey, RANK() OVER (ORDER BY score DESC) AS rank\n" +
                "  FROM player\n" +
                ") AS subquery\n" +
                "WHERE playerKey = ?";
        return (template.queryForObject(sql, Integer.class, key));

    }

}
