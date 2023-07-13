package caloryquiz.back.cal.web.Contoller;

import caloryquiz.back.cal.Domain.food.Food;
import caloryquiz.back.cal.Domain.food.FoodQuiz;
import caloryquiz.back.cal.Domain.food.FoodService;
import caloryquiz.back.cal.Domain.player.*;
import caloryquiz.back.cal.web.ArgumentResolver.PlayerArgumentResolver.PlayerCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
@CrossOrigin(origins = "*") //React와 연동을 위해 잠시 추가
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
    public void getSession(HttpServletRequest request, @RequestBody String nickName) {
        //세션 생성
        HttpSession session = request.getSession();
        /**
         * TODO nickName primary 검사
         */
        session.setAttribute("nickName", nickName);
        Player player = new Player(nickName, 0, 1, new ArrayList<Long>());
        session.setAttribute("player",player);
        log.info("nickName = {}",session.getAttribute("nickName"));

    }

    //순위 정보 요청
    @GetMapping("/dashboard")
    public List<PlayerOutcome> dashboard() {

        /**
         * TODO playerRepository에서 상위 몇명 순위로 가져오기
         */

        return playerService.findAll();
    }

    //문제 페이지

    //퀴즈 관련 음식들 보내기
    @GetMapping("/quizs")
    public HashMap<String,Object> sendQuiz(@PlayerCheck Player player) {
        HashMap<String, Object> data = new HashMap<>();


        Food food1 = foodService.randomFood(player.getFoodList());
        FoodQuiz quiz = foodService.MakeQuiz(food1);

        data.put("quiz", quiz);

        //session 생성
        data.put("player", player);
        log.info("Get Quiz");
        return data;
    }


    @PostMapping("/players/outcome")
    public HashMap<String,Integer> QuizEnd(@PlayerCheck Player player, @RequestBody sendAnswer outcome, HttpSession session) {

        log.info("answer ={} , Id = {}",outcome.getAnswer(),outcome.getQuizId());

        HashMap<String, Integer> data = playerService.update(player, outcome);

        log.info("player score = {}, turn = {}", player.getScore(),player.getTurn());



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

        /**
         *TODO
         * 저장했으면
         */

        //순위정보
        PlayerRank playerRank = playerService.getRank(player);
        log.info("player id = {}, nickName = {}, rank = {} ",player.getKey(),player.getNickName(),playerRank.getRank());
        //세션 해제
        request.getSession(false).invalidate();
        data.put("player", playerRank);

        //dashboard 정보
        /**
         * DashBoard와 같게 짜기
         * 메서드 따로 파기
         */
        List<PlayerOutcome> players = playerService.findAll();
        data.put("dashBoard", players);

        return data;
    }

}
