package proteinQuiz.back.cal.web.Contoller;

import proteinQuiz.back.cal.Domain.food.Food;
import proteinQuiz.back.cal.Domain.food.FoodQuiz;
import proteinQuiz.back.cal.Domain.food.FoodService;
import proteinQuiz.back.cal.Domain.player.*;
import proteinQuiz.back.cal.web.ArgumentResolver.PlayerArgumentResolver.PlayerCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QuizController {
    private final PlayerService playerService;
    private final FoodService foodService;


    //문제 페이지

    //퀴즈 관련 음식들 보내기
    @GetMapping("/quizs")
    public HashMap<String,Object> sendQuiz(@PlayerCheck Player player) {
        HashMap<String, Object> data = new HashMap<>();


        Food food1 = foodService.randomFood(player.getFoodList());
        FoodQuiz quiz = foodService.MakeQuiz(food1);

        data.put("quiz", quiz);
        data.put("player", player);
        return data;
    }



}
