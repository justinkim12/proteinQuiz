package caloryquiz.back.cal.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@RequiredArgsConstructor
public class Player {
    private Long key;
    private String email;
    private Integer score;
    private Long turn;
    private ArrayList<Long> foodList; //푼 문제들 넣어두기
    /**
     * TODO
     * 로그인 처리
     * turn 변수 여기에 넣기
     */
    public Player(String email, Integer score, Long turn, ArrayList<Long> foodList) {
        this.email = email;
        this.score = score;
        this.turn = turn;
        this.foodList = foodList;
    }


}
