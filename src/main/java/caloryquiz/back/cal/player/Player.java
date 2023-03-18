package caloryquiz.back.cal.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class Player {
    private String email;
    private Integer score;
    private Integer turn;
    /**
     * TODO
     * 로그인 처리
     * turn 변수 여기에 넣기
     */
}
