package caloryquiz.back.cal.Domain.player;

import caloryquiz.back.cal.Domain.food.Repository.FoodRate.FoodRateRepository;
import caloryquiz.back.cal.Domain.food.Repository.FoodRate.Grade;
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
    private final FoodRateRepository foodRateRepository;

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public Player save(Player player,sendAnswer outcome){
        return playerRepository.save(player);
    }

    public List<PlayerOutcome> findAll() {
        return playerRepository.findAll();
    }

    public List<PlayerOutcome> findAll(Integer max_index) {
        return playerRepository.findAll(max_index);
    }


    public Player findPlayerByKey(Long key) {
        return playerRepository.findPlayerByKey(key);
    }

    public Player findByNickName(String nickName) {
        return playerRepository.findByNickName(nickName);
    }

    public boolean CheckUniqueNickName(String nickName){
        if (playerRepository.findByNickName(nickName)!=null)
            return true;
        return false;
    }

    public PlayerRank getRank(Player player) {
        Integer rank = playerRepository.getRank(player.getKey());
        return new PlayerRank(player.getNickName(), player.getScore(), rank);
    }


    public HashMap<String, Integer> update(Player player, sendAnswer outcome) {

        Long foodID = outcome.getQuizId();
        Integer answer = foodRepository.findFoodByKey(foodID).getProtein();
        Integer user_answer = outcome.getAnswer();
        Grade grade = scoreLogic(answer, user_answer); //등급 생성

        //정답율 저장
        foodRateRepository.save(foodID, grade);

        //플레이어 점수, 턴, foodList 업데이트
        Integer score = grade.getScore();
        player.setScore(player.getScore()+score); //점수 추가
        player.setTurn(player.getTurn()+1); //다음 턴

        ArrayList<Long> foodList = player.getFoodList();
        foodList.add(foodID);
        player.setFoodList(foodList);

        //반환할 json map 생성
        HashMap<String, Integer> map = new HashMap<>();
        map.put("answer",answer);//음식의 정답
        map.put("score", score);//이번 라운드 얻은 점수
        return map;
    }

    private static Grade scoreLogic(Integer answer, Integer user_answer) {
        //점수
        Grade grade = Grade.F;
        // 절대값 오차 계산
        int absoluteError = Math.abs(answer - user_answer);

        // 오차 비율 계산
        double errorPercentage = (double) absoluteError / answer * 100;

        if (user_answer == answer) {
            grade = Grade.A; // 정답일 경우 10점
        } else if (errorPercentage <= 10) {
            grade = Grade.B; // 오차범위 10% 이내일 경우 8점
        } else if (errorPercentage <= 20) {
            grade = Grade.C; // 오차범위 20% 이내일 경우 5점
        } else if (errorPercentage<=50){
            grade = Grade.D; // 오차범위 50% 이내일 경우 1점
        }
        return grade;
    }

}
