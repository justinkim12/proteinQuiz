package caloryquiz.back.cal.Domain.food.Repository.FoodRate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcFoodRateRepository implements FoodRateRepository{

    private JdbcTemplate template;


    public JdbcFoodRateRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Long foodId, Grade grade) {
        String sql = "UPDATE food_rate SET " + grade.name() +
                " =  " + grade.name() + "+ 1" +
                " WHERE food_key = ? ";

        template.update(sql, foodId);
    }
}
