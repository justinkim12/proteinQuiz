package caloryquiz.back.cal.food;

import java.util.ArrayList;
import java.util.Optional;

public interface foodRepository {

    Food save(Food food);

    Optional<Food> findFoodByKey(Long key);

    Optional<Food> randomFood();
    ArrayList<Food> findAll();
}
