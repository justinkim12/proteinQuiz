package caloryquiz.back.cal.Domain.food.Repository.FoodRate;

public interface FoodRateRepository {

    void save(Long foodId, Grade grade);
}
