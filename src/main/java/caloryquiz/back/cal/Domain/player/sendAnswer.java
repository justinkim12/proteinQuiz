package caloryquiz.back.cal.Domain.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Getter
@Setter
@RequiredArgsConstructor
public class sendAnswer {

    @NotNull
    private Integer answer;
    @NotNull
    private Long quizId;

}
