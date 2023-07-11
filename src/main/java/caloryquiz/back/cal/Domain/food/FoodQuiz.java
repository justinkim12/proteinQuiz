package caloryquiz.back.cal.Domain.food;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FoodQuiz extends Food{
    FoodQuiz(String name, String file_path){
        super(name, file_path);
    }
    FoodQuiz(String name, String file_path, Long key) {
        super(name, file_path);
        this.setKey(key);
    }
}
