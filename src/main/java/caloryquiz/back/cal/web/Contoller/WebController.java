package caloryquiz.back.cal.web.Contoller;

import caloryquiz.back.cal.food.Food;
import caloryquiz.back.cal.food.FoodService;
import caloryquiz.back.cal.player.Player;
import caloryquiz.back.cal.player.PlayerOutcome;
import caloryquiz.back.cal.player.PlayerService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public String connectionChech(){
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
        log.info("nickName = {}",session.getAttribute("nickName"));

    }

    //순위 정보 요청
        @GetMapping("/dashboard")
    public List<Player> dashboard() {

        /**
         * TODO playerRepository에서 상위 몇명 순위로 가져오기
         */

        return playerService.findAll();
    }

    //문제 페이지

    //퀴즈 관련 음식들 보내기
    @GetMapping("/quizs")
    public List<Food> sendQuiz() {
        /**
         * TODO 100개 정도로 보내면 될려나..
         */
        return foodService.findAll();
    }


    @PostMapping("/players/outcome")
    public void QuizEnd(@RequestBody PlayerOutcome outcome, HttpServletRequest request) {

        log.info("score = {}", outcome.getScore());
        HttpSession session = request.getSession(false);
        String nickName = (String) session.getAttribute("nickName");
        Player player = new Player(nickName, 0);
        log.info("save player = {}",player.getNickName());
        playerService.save(player,outcome);
    }

    @GetMapping("/dashboard/player")
    public HashMap<String, Object> getData(HttpServletRequest request){
        HashMap<String, Object> data = new HashMap<>();
        //player 정보
        HttpSession session = request.getSession(false);
        String nickName = (String) session.getAttribute("nickName");
        Player player = playerService.findByNickName(nickName);
        log.info("Get player&DashBoard = {}", player.getNickName());

        /**
         *TODO 순위를 추가하기
         * 세션 해제?
         */
        data.put("player", player);

        //dashboard 정보
        /**
         * DashBoard와 같게 짜기
         * 메서드 따로 파기
         */
        ArrayList<Player> players = playerService.findAll();
        data.put("dashBoard", players);

        return data;
    }

    @PostConstruct
    public void dataSetting() {

        Food food1 = new Food("새우깡 90g", "/photo/새우깡.jpg", 6);
        Food food2 = new Food("감동란 2알", "/photo/감동란.jpg", 12);
        Food food3 = new Food("코카콜라 355ml(뚱캔)", "/photo/코카콜라.jpg", 2);

        foodService.save(food1);
        foodService.save(food2);
        foodService.save(food3);
    }
}
