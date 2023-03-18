package caloryquiz.back.cal.food;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter@Setter
@RequiredArgsConstructor
public class Food {
    private Long key;
    private String name; //제품 명
    private String file_path; //파일 경로
    private Integer calorie; // 칼리로 (답)

    public Food( String name,String file_path, Integer calorie) {

        this.name = name;
        this.file_path = file_path;
        this.calorie = calorie;
    }
}
