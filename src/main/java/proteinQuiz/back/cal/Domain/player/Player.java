package proteinQuiz.back.cal.Domain.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@RequiredArgsConstructor
public class Player {
    private Long key;
    private String nickName;
    private Integer score;
    private Integer turn;
    private ArrayList<Long> foodList; //푼 문제들 넣어두기
    /**
     * TODO
     * 로그인 처리
     * turn 변수 여기에 넣기
     */
    public Player(String nickName, Integer score,Integer turn, ArrayList<Long> foodList) {
        this.nickName = nickName;
        this.score = score;
        this.turn = turn;
        this.foodList = foodList;
    }


}
