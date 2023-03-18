package caloryquiz.back.cal.web;

import caloryquiz.back.cal.food.Food;
import caloryquiz.back.cal.food.MemoryFoodRepository;
import caloryquiz.back.cal.player.MemoryPlayerRepository;
import caloryquiz.back.cal.player.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class webController {

    private final MemoryPlayerRepository memoryPlayerRepository = new MemoryPlayerRepository();
    private final MemoryFoodRepository memoryFoodRepository = new MemoryFoodRepository();
    private static Long turn =1L;
    ArrayList<String> foodList = new ArrayList<>();;


    @GetMapping
    public String login(Model model) {
        log.info("login");
        model.addAttribute("email", "");
        return "webpage/login";
    }

    @PostMapping
    public String startQuiz(String email) {
        if (email != null) {
            Player player = new Player();
            player.setEmail(email);
            memoryPlayerRepository.save(player);
            log.info("Save = {}", email);
            /**
             * TODO
             * 세션 관리
             */
        }
        turn=1L;
        log.info("Start Quiz");
        foodList = new ArrayList<>();
        return "redirect:/quiz";
    }

    @GetMapping("/quiz")
    public String quiz(@RequestParam(required = false) Integer submit, Model model, RedirectAttributes redirectAttributes){

        if (turn >= 3L) { //3번턴까지만
            log.info("finish");
//                redirectAttributes.addAttribute("score",score) //점수 넘겨주기
            /**
             * TODO
             * 로그인 처리로 사용자 정보도 넘겨주기
             * 아마 걍 player 객체로 넘겨주는게 좋을듯
             * 세션을 이 시점에 풀어도 되고
             */
            return "redirect:/finish";
        }

        Food food = memoryFoodRepository.randomFood().get();
        /**
         * TODO
         * 중복되는 것 안 나오게 하기
         * 새로 고침하고 풀지 않은건 나올 수 있음
         */
        model.addAttribute("food",food);
        log.info("NextFood = {}", food.getName());

        if (submit == null) {
            log.info("Quiz page");
        }
        else{
            log.info("Answer is {}", submit);
            turn+=1L;
        }

        log.info("Next turn = {}", turn);
        model.addAttribute("turn", turn);
        return "/webpage/quiz";
    }

    @GetMapping("/finish")
    public String finish(){
        foodList.clear();
        turn = 1L;
        return "webpage/finish";
    }

    @PostConstruct
    public void dataSetting() {

        Food food1 = new Food("새우깡 400g", "../photo/새우깡.jpg", 1950);
        Food food2 = new Food("감동란 2알", "../photo/감동란.jpg", 131);
        Food food3 = new Food("코카콜라 355ml(뚱캔)", "../photo/코카콜라.jpg", 160);

        memoryFoodRepository.save(food1);
        memoryFoodRepository.save(food2);
        memoryFoodRepository.save(food3);
    }

}
