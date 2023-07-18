package proteinQuiz.back.cal.web.Contoller;

import proteinQuiz.back.cal.Domain.Error.ErrorResult;
import proteinQuiz.back.cal.Domain.player.*;
import proteinQuiz.back.cal.web.ArgumentResolver.PlayerArgumentResolver.PlayerCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class PlayerController {
    private final PlayerService playerService;

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
        HttpSession session = request.getSession();
        session.setAttribute("nickName", nickName);
        Player player = new Player(getNickName, 0, 1, new ArrayList<Long>());
        session.setAttribute("player",player);
        log.info("[{}] start player", player.getNickName());
        return errorResult;
    }

    //순위 정보 요청
    @GetMapping("/dashboard")
    public List<PlayerOutcome> dashboard() {
        return playerService.findAll(10);
    }

    //문제 정답
    @PostMapping("/outcome")
    public HashMap<String,Integer> QuizEnd(@PlayerCheck Player player, @Validated @RequestBody sendAnswer outcome,
                                           BindingResult bindingResult, HttpSession session) {

        if(bindingResult.hasErrors()){
            log.info("Binding Error = {} ",bindingResult.getAllErrors().get(0).getDefaultMessage());
            outcome.setAnswer(0);
        }

        HashMap<String, Integer> data = playerService.update(player, outcome);

        log.info("[{}] answer ={} , QuizId = {}, player score = {}, turn = {}" , player.getNickName(),
                outcome.getAnswer(),outcome.getQuizId(),player.getScore(),player.getTurn());


        //턴에 따라 저장 또는 세션 수정
        if(player.getTurn()==10) {
            log.info("[{}] save player", player.getNickName());
            playerService.save(player);
        }else
            session.setAttribute("player",player);


        return data;
    }

    //퀴즈 종료
    @GetMapping("/dashboard/player")
    public HashMap<String, Object> getData(@PlayerCheck Player player,HttpServletRequest request){
        HashMap<String, Object> data = new HashMap<>();

        //순위정보
        PlayerRank playerRank = playerService.getRank(player);
        log.info("[{}] Get player&DashBoard, rank = {}", player.getNickName(),playerRank.getRank());

        //세션 해제
        request.getSession(false).invalidate();

        //dashboard 정보
        List<PlayerOutcome> players = playerService.findAll(10);

        data.put("player", playerRank);
        data.put("dashBoard", players);

        return data;
    }
}
