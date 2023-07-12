package caloryquiz.back.cal.Domain.player;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class PlayerOutcome {
    private String nickName;
    private Integer score;

    public PlayerOutcome(String nickName, Integer score) {
        this.nickName = nickName;
        this.score = score;
    }
}
