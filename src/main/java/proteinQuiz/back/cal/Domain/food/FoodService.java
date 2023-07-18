package proteinQuiz.back.cal.Domain.food;

import proteinQuiz.back.cal.Domain.food.Repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    public FoodQuiz MakeQuiz(Food food){

        return new FoodQuiz(food.getName(), food.getFile_path(), food.getFoodKey());
    }


    public Food save(Food food) {
        return foodRepository.save(food);
    }

    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    public Food randomFood(ArrayList<Long> foodList) {
        return foodRepository.randomFood(foodList);
    }

    public Food findFoodByKey(Long key) {
        return foodRepository.findFoodByKey(key);
    }


    public void delete(String name) {
        foodRepository.delete(name);
    }
}
