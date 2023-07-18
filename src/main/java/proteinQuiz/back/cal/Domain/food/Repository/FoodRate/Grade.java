package proteinQuiz.back.cal.Domain.food.Repository.FoodRate;

import lombok.Getter;

@Getter
public enum Grade {
    A(10),
    B(8),
    C(5),
    D(1),
    F(0);
    private Integer score;

    Grade(Integer score){
        this.score=score;
    }
    public static Grade fromValue(int value) {
        for (Grade grade : Grade.values()) {
            if (grade.getScore() == value) {
                return grade;
            }
        }
        throw new IllegalArgumentException("Invalid day value: " + value);
    }
}
