package caloryquiz.back.cal.Domain.food;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter@Setter
@RequiredArgsConstructor
public class Food {
    Long key;
    private String name; //제품 명
    private String file_path; //파일 경로
    private Integer protein; // 칼로리 (답)

    public Food( String name,String file_path) {

        this.name = name;
        this.file_path = file_path;
    }

    public Food( String name,String file_path, Integer protein) {

        this.name = name;
        this.file_path = file_path;
        this.protein = protein;
    }

}
