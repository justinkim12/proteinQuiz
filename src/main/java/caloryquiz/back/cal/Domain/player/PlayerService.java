package caloryquiz.back.cal.Domain.player;

import caloryquiz.back.cal.Domain.food.Repository.FoodRepository;
import caloryquiz.back.cal.Domain.player.Repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final FoodRepository foodRepository;

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public Player save(Player player,sendAnswer outcome){

        return playerRepository.save(player);
    }

    public List<PlayerOutcome> findAll() {
        return playerRepository.findAll();
    }

    public Optional<Player> findPlayerByKey(Long key) {
        return playerRepository.findPlayerByKey(key);
    }

    public Player findByNickName(String nickName) {
        return playerRepository.findByNickName(nickName);
    }

    public HashMap<String, Integer> update(Player player, sendAnswer outcome) {

        Integer answer = foodRepository.findFoodByKey(outcome.getQuizId()).get().getProtein();
        Integer user_answer = outcome.getAnswer();
        Integer score = scoreLogic(answer, user_answer);
        player.setScore(player.getScore()+score); //점수 추가
        player.setTurn(player.getTurn()+1); //다음 턴

        ArrayList<Long> foodList = player.getFoodList();
        foodList.add(outcome.getQuizId());
        player.setFoodList(foodList);

        HashMap<String, Integer> map = new HashMap<>();

        map.put("answer",answer);//음식의 정답
        map.put("score", score);//이번 라운드 얻은 점수
        return map;
    }

    private static Integer scoreLogic(Integer answer, Integer user_answer) {
        //점수
        Integer score =0;
        // 절대값 오차 계산
        int absoluteError = Math.abs(answer - user_answer);

        // 오차 비율 계산
        double errorPercentage = (double) absoluteError / answer * 100;

        if (user_answer == answer) {
            score = 10; // 정답일 경우 10점
        } else if (errorPercentage <= 10) {
            score = 8; // 오차범위 10% 이내일 경우 6점
        } else if (errorPercentage <= 20) {
            score = 5; // 오차범위 20% 이내일 경우 3점
        } else if (errorPercentage<=50){
            score = 1; // 오차범위 50% 이내일 경우 1점
        }
        return score;
    }

}
