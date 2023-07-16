package caloryquiz.back.cal.web.Contoller;

import caloryquiz.back.cal.Domain.Error.ErrorResult;
import caloryquiz.back.cal.Domain.food.Food;
import caloryquiz.back.cal.Domain.food.FoodQuiz;
import caloryquiz.back.cal.Domain.food.FoodService;
import caloryquiz.back.cal.Domain.player.*;
import caloryquiz.back.cal.web.ArgumentResolver.PlayerArgumentResolver.PlayerCheck;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")

//@CrossOrigin(origins = "https://protein-quiz.vercel.app") //React와 연동을 위해 잠시 추가
public class WebController {
    private final PlayerService playerService;
    private final FoodService foodService;

    //start page
    @GetMapping
    public String connectionCheck(){
        return "Hello World";
    }


    //첫 페이지에서 닉네임을 넘기면 세션을 넘기고 게임 시작
    @PostMapping("/players")
    public ErrorResult getSession(HttpServletRequest request, @Validated @RequestBody PlayerNickName nickName, BindingResult bindingResult) {
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
           log.info("닉네임 에러 = {}",check);
            return errorResult;
        }
        String getNickName = nickName.getNickName();

        if(playerService.CheckUniqueNickName(getNickName)) { //중복 방지
            log.info("닉네임 에러 = 중복");
            errorResult.setMessage("이미 있는 닉네임입니다.");
            return errorResult;
        }

        //세션 생성
        HttpSession session = request.getSession();
        session.setAttribute("nickName", nickName);
        Player player = new Player(getNickName, 0, 1, new ArrayList<Long>());
        session.setAttribute("player",player);
        log.info("start player = {}",session.getAttribute("nickName").getClass());
        return errorResult;
    }

    //순위 정보 요청
    @GetMapping("/dashboard")
    public List<PlayerOutcome> dashboard() {
        return playerService.findAll(10);
    }

    //문제 페이지

    //퀴즈 관련 음식들 보내기
    @GetMapping("/quizs")
    public HashMap<String,Object> sendQuiz(@PlayerCheck Player player) {
        HashMap<String, Object> data = new HashMap<>();


        Food food1 = foodService.randomFood(player.getFoodList());
        FoodQuiz quiz = foodService.MakeQuiz(food1);

        data.put("quiz", quiz);

        data.put("player", player);
//        log.info("Get Quiz");
        return data;
    }


    @PostMapping("/outcome")
    public HashMap<String,Integer> QuizEnd(@PlayerCheck Player player, @Validated @RequestBody sendAnswer outcome,
                                           BindingResult bindingResult, HttpSession session) {

        if(bindingResult.hasErrors()){
            log.info("Binding Error = {} ",bindingResult.getAllErrors().get(0).getDefaultMessage());
//            log.info("outcome = {} {}",outcome.getAnswer(),outcome.getQuizId());
            outcome.setAnswer(0);
        }

        HashMap<String, Integer> data = playerService.update(player, outcome);

        log.info("player answer ={} , Id = {}, player score = {}, turn = {}" ,
                outcome.getAnswer(),outcome.getQuizId(),player.getScore(),player.getTurn());



        if(player.getTurn()==10) {
            log.info("save player = {}", player.getNickName());
            playerService.save(player);
        }else
            session.setAttribute("player",player);


        return data;
    }

    @GetMapping("/dashboard/player")
    public HashMap<String, Object> getData(@PlayerCheck Player player,HttpServletRequest request){
        HashMap<String, Object> data = new HashMap<>();
        log.info("Get player&DashBoard = {}", player.getNickName());


        //순위정보
        PlayerRank playerRank = playerService.getRank(player);
        log.info("player id = {}, nickName = {}, rank = {} ",player.getKey(),player.getNickName(),playerRank.getRank());
        //세션 해제
//        request.getSession(false).invalidate();
        data.put("player", playerRank);

        //dashboard 정보
        List<PlayerOutcome> players = playerService.findAll(10);
        data.put("dashBoard", players);

        return data;
    }

}
