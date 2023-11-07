package proteinQuiz.back.cal.web.Contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import proteinQuiz.back.cal.Domain.Error.ErrorResult;
import proteinQuiz.back.cal.Domain.food.Food;
import proteinQuiz.back.cal.Domain.food.FoodQuiz;
import proteinQuiz.back.cal.Domain.food.FoodService;
import proteinQuiz.back.cal.Domain.player.*;
import proteinQuiz.back.cal.web.ArgumentResolver.PlayerArgumentResolver.PlayerCheck;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {
    private final PlayerService playerService;
    private final FoodService foodService;

    //첫 페이지에서 닉네임을 넘기면 세션을 넘기고 게임 시작
    @PostMapping("/players")
    public ErrorResult getSession(@Validated @RequestBody PlayerNickName nickName, BindingResult bindingResult) {


        ErrorResult errorResult = new ErrorResult("ok");

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            log.info("Binding Error message = {}", errorMessage);
            errorResult.setMessage(errorMessage);
            return errorResult;
        }

        String check = nickName.basicCheck();
        if(check.length()>0){
            errorResult.setMessage(check);
            log.info("NickName error message = {}",check);
            return errorResult;
        }
        String getNickName = nickName.getNickName();

        if(playerService.CheckUniqueNickName(getNickName)) { //중복 방지
            log.info("NickName error message = 중복");
            errorResult.setMessage("이미 있는 닉네임입니다.");
            return errorResult;
        }

        //세션 생성
        Player player = new Player(getNickName, 0, 1, new ArrayList<Long>());
        log.info("TEST--[{}] start player", player.getNickName());
        return errorResult;
    }

    //순위 정보 요청
    @GetMapping("/dashboard")
    public List<PlayerOutcome> dashboard() {
        return playerService.findAll(10);
    }

    //문제 정답
    @PostMapping("/outcome")
    public HashMap<String,Integer> QuizEnd(@Validated @RequestBody sendAnswer outcome,
                                           BindingResult bindingResult) {
        Player player = new Player("TESTER", 0, 10, new ArrayList<Long>());

        if(bindingResult.hasErrors()){
            log.info("Binding Error = {} ",bindingResult.getAllErrors().get(0).getDefaultMessage());
            outcome.setAnswer(0);
        }

        HashMap<String, Integer> data = playerService.update(player, outcome);

        log.info("TEST--[{}] answer ={} , QuizId = {}, player score = {}, turn = {}" , player.getNickName(),
                outcome.getAnswer(),outcome.getQuizId(),player.getScore(),player.getTurn()-1);//업데이트가 되었기 땜에 turn-1

        return data;
    }

    //퀴즈 종료
    @GetMapping("/dashboard/player")
    public HashMap<String, Object> getData(){
        HashMap<String, Object> data = new HashMap<>();
        Player player = new Player("TESTER", 0, 10, new ArrayList<Long>());
        //순위정보
        PlayerRank playerRank = new PlayerRank(new PlayerOutcome(player.getNickName(), player.getScore()),2);
        log.info("TEST--[{}] Get player&DashBoard, rank = {}", player.getNickName(),playerRank.getRank());

        //dashboard 정보
        List<PlayerOutcome> players = playerService.findAll(10);

        data.put("player", playerRank);
        data.put("dashBoard", players);

        return data;
    }

    //문제 페이지

    //퀴즈 관련 음식들 보내기
    @GetMapping("/quizs")
    public HashMap<String,Object> sendQuiz() {
        Player player = new Player("TESTER", 0, 10, new ArrayList<Long>());
        HashMap<String, Object> data = new HashMap<>();


        Food food1 = foodService.randomFood(player.getFoodList());
        FoodQuiz quiz = foodService.MakeQuiz(food1);

        data.put("quiz", quiz);
        data.put("player", player);
        return data;
    }





}
