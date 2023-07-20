package proteinQuiz.back.cal.Domain.Logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
@Slf4j
public class JdbcLoggingRepository implements LoggingRepository {

    private final JdbcTemplate template;
    private String logPattern = "\\[(.*?)\\] (.*?) (.*?) - (.*)";
    private Pattern pattern = Pattern.compile(logPattern);
    public JdbcLoggingRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(String fileName) {//LoggingEntity loggingEntity 인자로 넣기
        //sql
        String sql = "insert into log(log_level,time,logger_name,message) values(?,?,?,?)";
        // 파일경로
        String filePath = "./logs/"+fileName+".log";
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath),
                    StandardCharsets.UTF_8);

            lines.stream().forEach((line) -> {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String logLevel = matcher.group(1);
                    Time timestamp = Time.valueOf((matcher.group(2)));
                    String loggerName = matcher.group(3);
                    String message = matcher.group(4);
                    // 로그 정보 저장
                    template.update(sql, logLevel, timestamp, loggerName, message);
                }
            });
        } catch (IOException e) {
            log.error("error={}", e.getMessage(), e);
        }
    }

    @Override
    public void drop() {
        String sql = "delete from log";
        template.execute(sql);
    }
}
