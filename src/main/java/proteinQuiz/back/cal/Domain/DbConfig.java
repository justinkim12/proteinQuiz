package proteinQuiz.back.cal.Domain;

import proteinQuiz.back.cal.Domain.Logging.JdbcLoggingRepository;
import proteinQuiz.back.cal.Domain.Logging.LoggingRepository;
import proteinQuiz.back.cal.Domain.Logging.LoggingService;
import proteinQuiz.back.cal.Domain.food.Repository.FoodRate.FoodRateRepository;
import proteinQuiz.back.cal.Domain.food.Repository.FoodRate.JdbcFoodRateRepository;
import proteinQuiz.back.cal.Domain.food.Repository.FoodRepository;
import proteinQuiz.back.cal.Domain.food.Repository.JdbcFoodRepository;
import proteinQuiz.back.cal.Domain.player.Repository.JdbcPlayerRepository;
import proteinQuiz.back.cal.Domain.player.Repository.PlayerRepository;
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

    @Bean
    public LoggingService loggingService(){
        return new LoggingService(loggingRepository());}

    @Bean
    public LoggingRepository loggingRepository(){return new JdbcLoggingRepository(dataSource);}
}
