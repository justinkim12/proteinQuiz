package proteinQuiz.back.cal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import proteinQuiz.back.cal.Domain.DbConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@Import(DbConfig.class)
@SpringBootApplication(scanBasePackages = "proteinQuiz.back.cal")
@EnableScheduling
public class CalApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalApplication.class, args);
	}

	@PostConstruct

	public void TimeSet(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		log.info("Server start at = {}",new Date().getTime());
	}
}
