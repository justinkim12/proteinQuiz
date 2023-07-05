package caloryquiz.back.cal.food;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryFoodRepository implements FoodRepository {
    private static final Map<Long, Food> store = new HashMap<>(); //static
    private static Long id = 0L;

    @Override
    public Food save(Food food) {
        food.setKey(id++);
        store.put(food.getKey(), food);
        return food;
    }

    @Override
    public ArrayList<Food> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Food> randomFood() {
        Random rand = new Random();
        Long key =(long) (rand.nextInt(Math.toIntExact(id)));
        return findFoodByKey(key);
    }

    @Override
    public Optional<Food> findFoodByKey(Long key) {
        return findAll().stream().filter(m -> m.getKey().equals(key)).findFirst();
    }


}
