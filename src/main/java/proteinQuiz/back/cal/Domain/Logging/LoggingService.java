package proteinQuiz.back.cal.Domain.Logging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

//@Service
@Slf4j
public class LoggingService {

    private final LoggingRepository loggingRepository;

    @Autowired
    public LoggingService(LoggingRepository loggingRepository) {
        this.loggingRepository = loggingRepository;
    }

    @Scheduled(cron = "0 10 * * * ?") // 매시 10분마다
    public void saveLog() {
        Date d = new Date(); //오늘날짜
        d = new Date(d.getTime()+(1000*60*10*-1)); //10분전꺼
        SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd-HH");
        String fileName = today.format(d);
        log.warn("Saving All logs {}", fileName);
        loggingRepository.save(fileName); // 로그를 DB에 저장
    }
}