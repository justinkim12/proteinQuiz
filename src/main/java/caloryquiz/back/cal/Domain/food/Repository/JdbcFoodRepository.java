package caloryquiz.back.cal.Domain.food.Repository;

import caloryquiz.back.cal.Domain.food.Food;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Optional;

//@Repository
public class JdbcFoodRepository implements FoodRepository {

    private final JdbcTemplate template;

    public JdbcFoodRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }


    @Override
    public Food save(Food food) {
        String sql = "";
        return null;
    }

    @Override
    public Optional<Food> findFoodByKey(Long key) {
        return Optional.empty();
    }

    @Override
    public Optional<Food> randomFood() {
        return Optional.empty();
    }

    @Override
    public ArrayList<Food> findAll() {
        return null;
    }
}
