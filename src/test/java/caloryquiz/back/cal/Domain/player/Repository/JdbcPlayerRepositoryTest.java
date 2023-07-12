package caloryquiz.back.cal.Domain.player.Repository;

import caloryquiz.back.cal.Domain.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class JdbcPlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @TestConfiguration
    static class TestConfig{
        private final DataSource dataSource;

        public TestConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }
        @Bean
        PlayerRepository playerRepository(){
            return new JdbcPlayerRepository(dataSource);
        }
    }

    @AfterEach
    void after() {
        playerRepository.delete("a@a.com");
        playerRepository.delete("b@a.com");
        playerRepository.delete("c@a.com");
        playerRepository.initKey();
    }

    @Test
    void saveAndFind() {
        Player player1 = new Player("a@a.com", 0,1,new ArrayList<>());
        Player player2 = new Player("b@a.com", 0,1,new ArrayList<>());
        Player player3 = new Player("c@a.com", 0, 1, new ArrayList<>());

        playerRepository.save(player1);
        playerRepository.save(player2);
        playerRepository.save(player3);

        assertThat(playerRepository.findPlayerByKey(1L).getNickName()).isEqualTo("a@a.com");

    }
}