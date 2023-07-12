package caloryquiz.back.cal.Domain.food;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FoodQuiz{

    private String name;
    private String file_path;
    private Long key;

    FoodQuiz(String name, String file_path, Long key) {
        this.name=name;
        this.file_path = file_path;
        this.key=key;
    }
}
