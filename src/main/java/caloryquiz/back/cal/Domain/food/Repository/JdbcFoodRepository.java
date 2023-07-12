package caloryquiz.back.cal.Domain.food.Repository;

import caloryquiz.back.cal.Domain.food.Food;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
//@Slf4j
public class JdbcFoodRepository implements FoodRepository {

    private final JdbcTemplate template;
    private static Integer numFood; //food 개수

    public JdbcFoodRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        String sql = "SELECT MAX(foodKey) FROM food";
        numFood = template.queryForObject(sql, Integer.class);
//        log.info("numFood = {}", numFood);
    }


    @Override
    public Food save(Food food) {
        String sql ="insert into food(name,file_path,protein) values(?,?,?)";
        template.update(sql,food.getName(),food.getFile_path(),food.getProtein());
        return food;
    }

    @Override
    public Food findFoodByKey(Long key) {
        String sql = "select * from food where foodKey = ?";
        return template.queryForObject(sql,foodMapper,key);
    }

    @Override
    public Food randomFood(ArrayList<Long> foodList) {
        Random rand = new Random();
        Long key;

        //중복 문제 방지 160문제 정도 중 10문제 뽑는 거라 오래 걸리지 않을 전망
        do {
            key = (long) (rand.nextInt(Math.toIntExact(numFood))) + 1;//1~numFood 사이
        } while (foodList.contains(key));


        return findFoodByKey(key);
    }

    @Override
    public List<Food> findAll() {

        String sql = "select * from food";
        return template.query(sql,foodMapper);
    }

    @Override
    public void delete(String name) {
        String sql = "delete from food where name = ?";
        template.update(sql, name);
    }

    @Override
    public void initKey(){
        String sql = "ALTER TABLE food  ALTER COLUMN foodKey RESTART WITH 1;";
        template.execute(sql);
    }

    static RowMapper<Food> foodMapper = (rs, rowNum) ->
            new Food(rs.getLong("foodKey"),rs.getString("name"),
                    rs.getString("file_path"),rs.getInt("protein"));

}
