package proteinQuiz.back.cal.Domain.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
public class sendAnswer {

    @NotNull
    private Integer answer;
    @NotNull
    private Long quizId;

}
