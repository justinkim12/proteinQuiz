package proteinQuiz.back.cal;

import org.springframework.scheduling.annotation.EnableScheduling;
import proteinQuiz.back.cal.Domain.DbConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(DbConfig.class)
@SpringBootApplication(scanBasePackages = "proteinQuiz.back.cal")
@EnableScheduling
public class CalApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalApplication.class, args);
	}

}
