package caloryquiz.back.cal.food;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final foodRepository foodRepository;


    public Food save(Food food) {
        return foodRepository.save(food);
    }

    public ArrayList<Food> findAll() {
        return foodRepository.findAll();
    }

    public Optional<Food> randomFood() {
        return randomFood();
    }

    public Optional<Food> findFoodByKey(Long key) {
        return foodRepository.findFoodByKey(key);
    }


}
