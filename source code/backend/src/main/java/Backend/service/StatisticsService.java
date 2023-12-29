package Backend.service;

import java.util.List;

public interface StatisticsService {
    long countExamTotal();
    long countQuestionTotal();
    long countAccountTotal();
    long countExamUserTotal();
    Double getChangeExam();
    Double getChangeQuestion();
    Double getChangeAccount();
    Double getChangeExamUser();
    Double getChangeReceivedParcel();
    Double getChangeSucceedParcel();
    Double getChangeFailedParcel();
    List<Long> countExamUserLastedSevenDaysTotal();
}
