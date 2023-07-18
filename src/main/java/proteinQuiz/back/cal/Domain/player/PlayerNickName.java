package proteinQuiz.back.cal.Domain.player;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class PlayerNickName {
    @NotNull
    private String nickName;

    public String basicCheck(){
        String name = this.nickName;
        String message = "";
        if (name.length()>50)
            message = "문자열 길이 초과입니다!";
        if (name.contains(" "))
            message = "띄어 쓰기 제거해주세요!";
        return message;
    }
}
