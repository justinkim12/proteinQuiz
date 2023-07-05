package caloryquiz.back.cal.food;

import java.util.ArrayList;
import java.util.Optional;

public interface FoodRepository {

    Food save(Food food);

    Optional<Food> findFoodByKey(Long key);

    Optional<Food> randomFood();
    ArrayList<Food> findAll();

}
