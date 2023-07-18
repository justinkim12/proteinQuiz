package proteinQuiz.back.cal.Domain.food.Repository;

import proteinQuiz.back.cal.Domain.food.Food;

import java.util.ArrayList;
import java.util.List;

public interface FoodRepository {

    Food save(Food food);

    Food findFoodByKey(Long key);

    Food randomFood(ArrayList<Long> foodList);

    List<Food> findAll();

    void delete(String foodName);

    void initKey();
}
