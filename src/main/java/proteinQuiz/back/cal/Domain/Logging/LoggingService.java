package proteinQuiz.back.cal.Domain.Logging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class LoggingService {

    private final LoggingRepository loggingRepository;

    @Autowired
    public LoggingService(LoggingRepository loggingRepository) {
        this.loggingRepository = loggingRepository;
    }

    @Scheduled(cron = "0 0 13 * * ?")
    public void saveLog() {
        Date d = new Date(); //오늘날짜
        d = new Date(d.getTime()+(1000*60*60*24*-1)); //어제날짜
        SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = "log-" + today.format(d)+".0";//일단은 .0 한개만 만들어질 예정
        log.warn("Saving All logs {}", fileName);
        loggingRepository.save(fileName); // 로그를 DB에 저장
    }
}