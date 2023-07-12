package caloryquiz.back.cal.Domain;

import caloryquiz.back.cal.Domain.food.Repository.FoodRate.FoodRateRepository;
import caloryquiz.back.cal.Domain.food.Repository.FoodRate.JdbcFoodRateRepository;
import caloryquiz.back.cal.Domain.food.Repository.FoodRepository;
import caloryquiz.back.cal.Domain.food.Repository.JdbcFoodRepository;
import caloryquiz.back.cal.Domain.player.Repository.JdbcPlayerRepository;
import caloryquiz.back.cal.Domain.player.Repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DbConfig {
    private final DataSource dataSource;

    @Bean
    public FoodRepository foodRepository(){return new JdbcFoodRepository(dataSource);}

    @Bean
    public PlayerRepository playerRepository(){return new JdbcPlayerRepository(dataSource);}

    @Bean
    public FoodRateRepository foodRateRepository(){
        return new JdbcFoodRateRepository(dataSource);
    }
}
