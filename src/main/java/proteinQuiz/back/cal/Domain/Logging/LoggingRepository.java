package proteinQuiz.back.cal.Domain.Logging;

public interface LoggingRepository {

    //    void save(LoggingEntity loggingEntity);
    void save(String fileName);
    void drop();
}
