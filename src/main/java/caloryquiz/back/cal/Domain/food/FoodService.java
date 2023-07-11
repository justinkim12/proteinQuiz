package caloryquiz.back.cal.Domain.food;

import caloryquiz.back.cal.Domain.food.Repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    public FoodQuiz MakeQuiz(Food food){
        return new FoodQuiz(food.getName(), food.getFile_path(), food.getKey());
    }


    public Food save(Food food) {
        return foodRepository.save(food);
    }

    public ArrayList<Food> findAll() {
        return foodRepository.findAll();
    }

    public Optional<Food> randomFood() {
        return foodRepository.randomFood();
    }

    public Optional<Food> findFoodByKey(Long key) {
        return foodRepository.findFoodByKey(key);
    }


}
