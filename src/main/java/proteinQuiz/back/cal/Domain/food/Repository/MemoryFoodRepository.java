package proteinQuiz.back.cal.Domain.food.Repository;

import proteinQuiz.back.cal.Domain.food.Food;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryFoodRepository implements FoodRepository {
    private static final Map<Long, Food> store = new HashMap<>(); //static
    private static Long id = 0L;

    @Override
    public Food save(Food food) {
        food.setFoodKey(id++);
        store.put(food.getFoodKey(), food);
        return food;
    }

    @Override
    public List<Food> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(String name) {

    }

    @Override
    public void initKey() {

    }

    @Override
    public Food randomFood(ArrayList<Long> foodList) {
        Random rand = new Random();
        Long key =(long) (rand.nextInt(Math.toIntExact(id)));
        return findFoodByKey(key);
    }

    @Override
    public Food findFoodByKey(Long key) {
        return findAll().stream().filter(m -> m.getFoodKey().equals(key)).findFirst().get();
    }



}
