package caloryquiz.back.cal.Domain.player;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@RequiredArgsConstructor
public class sendAnswer {

    private Integer answer;
    private Long quizId;

}
