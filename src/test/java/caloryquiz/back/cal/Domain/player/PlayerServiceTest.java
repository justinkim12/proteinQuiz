package caloryquiz.back.cal.Domain.player;

import caloryquiz.back.cal.Domain.food.Repository.FoodRate.FoodRateRepository;
import caloryquiz.back.cal.Domain.food.Repository.FoodRate.JdbcFoodRateRepository;
import caloryquiz.back.cal.Domain.food.Repository.FoodRepository;
import caloryquiz.back.cal.Domain.food.Repository.JdbcFoodRepository;
import caloryquiz.back.cal.Domain.player.Repository.JdbcPlayerRepository;
import caloryquiz.back.cal.Domain.player.Repository.PlayerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlayerServiceTest {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private FoodRateRepository foodRateRepository;

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

        @Bean
        FoodRepository foodRepository(){
            return new JdbcFoodRepository(dataSource);
        }

        @Bean
        FoodRateRepository foodRateRepository(){return new JdbcFoodRateRepository(dataSource);}
        @Bean
        PlayerService playerService(){
            return new PlayerService(playerRepository(), foodRepository(),foodRateRepository());}
    }

    @AfterEach
    void after() {
        playerRepository.delete("a@a.com");
        playerRepository.delete("b@a.com");
        playerRepository.delete("c@a.com");
        playerRepository.initKey();
    }

    @Test
    void saveAndFindAll(){
        Player player1 = new Player("a@a.com", 0,1,new ArrayList<>());
        Player player2 = new Player("b@a.com", 0,1,new ArrayList<>());
        Player player3 = new Player("c@a.com", 0, 1, new ArrayList<>());

        playerService.save(player1);
        playerService.save(player2);
        playerService.save(player3);

        Assertions.assertThat(playerService.findAll().size()).isEqualTo(3);
    }

    @Test()
    void Quiz(){
        Player player1 = new Player("a@a.com", 0,1,new ArrayList<>());
        sendAnswer sendAnswer = new sendAnswer();
        Long quizId = 1L;
        sendAnswer.setAnswer(0);
        sendAnswer.setQuizId(quizId);
        HashMap<String, Integer> map = playerService.update(player1, sendAnswer);
        Integer score = map.get("score");
        Assertions.assertThat(score).isEqualTo(0);

    }
}