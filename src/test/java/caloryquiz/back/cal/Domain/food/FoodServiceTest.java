package caloryquiz.back.cal.Domain.food;

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

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FoodServiceTest {

    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private FoodService foodService;

    @TestConfiguration
    static class TestConfig{
        private final DataSource dataSource;

        public TestConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }
        @Bean
        FoodRepository foodRepository(){
            return new JdbcFoodRepository(dataSource);
        }
        @Bean
        FoodService foodService(){
            return new FoodService(foodRepository());}
    }
    @AfterEach
    void after() {
        foodService.delete("food1");
        foodService.delete("food2");
        foodService.delete("food3");
        foodRepository.initKey();
    }
    @Test
    void saveAndFind(){
        Food food1 = new Food("food1", "url1", 10);
        Food food2 = new Food("food2", "url2", 10);
        Food food3 = new Food("food3", "url3", 10);

        foodService.save(food1);
        foodService.save(food2);
        foodService.save(food3);

        Assertions.assertThat(foodService.findAll().size()).isEqualTo(3);
    }

}