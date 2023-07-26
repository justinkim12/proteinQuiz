package proteinQuiz.back.cal.Domain.player.Repository;

import org.springframework.transaction.annotation.Transactional;
import proteinQuiz.back.cal.Domain.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Transactional
@SpringBootTest
class JdbcPlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;


    @Test
    void saveAndFind() {
        Player player1 = new Player("a@a.com", 0,1,new ArrayList<>());
        Player player2 = new Player("b@a.com", 0,1,new ArrayList<>());
        Player player3 = new Player("c@a.com", 0, 1, new ArrayList<>());

        playerRepository.save(player1);
        playerRepository.save(player2);
        playerRepository.save(player3);

        assertThat(playerRepository.findByNickName("a@a.com")).isInstanceOf(Player.class);

    }
}