package Backend.service;

import Backend.entity.Parcel;
import Backend.entity.User;
import Backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService{
    private Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);
    private final UserRepository userRepository;
    private final ParcelService parcelService;

    @Autowired
//    public StatisticsServiceImpl(ExamRepository examRepository, ExamUserRepository examUserRepository, QuestionRepository questionRepository, UserRepository userRepository) {
//        this.examRepository = examRepository;
//        this.examUserRepository = examUserRepository;
//        this.questionRepository = questionRepository;
//        this.userRepository = userRepository;
//    }

    public StatisticsServiceImpl(UserRepository userRepository, ParcelService parcelService) {
        this.userRepository = userRepository;
        this.parcelService = parcelService;
    }
    @Override
    public long countExamTotal() {
//        return examRepository.count();
        return 1;
    }
//
    @Override
    public long countQuestionTotal() {
//        return questionRepository.count();
        return 1;
    }

//
    @Override
    public long countAccountTotal() {
        return 1;
//        return userRepository.count();
    }
//
    @Override
    public long countExamUserTotal() {
        return 1;
//        return examUserRepository.count();
    }
//
    @Override
    public Double getChangeExamUser() {
//        int countExamNow = 0;
//        int countExamLastWeek = 0;
//        List<ExamUser> examUsers = examUserRepository.findExamUsersByOrderByTimeFinish();
//        for (ExamUser examUser :
//                examUsers) {
//            if (isSameWeek(new LocalDateTime(), new LocalDateTime(examUser.getTimeFinish()))) {
//                countExamNow++;
//            } else if (isLastWeek(new LocalDateTime(), new LocalDateTime(examUser.getTimeFinish()))) {
//                countExamLastWeek++;
//            } else break;
//        }
//        if (countExamNow == 0 && countExamLastWeek == 0) {
//            return 0.00;
//        }
//        if (countExamNow == 0 && countExamLastWeek != 0) {
//            return countExamLastWeek * -100.00;
//        }
//        if (countExamNow != 0 && countExamLastWeek == 0) {
//            return countExamNow * 100.00;
//        }
//        Double result = (double) countExamNow - countExamLastWeek;
//
//        double roundedRes = (double) result / countExamLastWeek;
//        roundedRes *= 100;
//        roundedRes = Math.round(roundedRes);
//        roundedRes /= 100;
//
//        return roundedRes * 100;
        return 1.0;
    }

    @Override
    public Double getChangeReceivedParcel() {
        List<Parcel> parcels = parcelService.getParcelList();
        int countParcelNow = 0;
        int countParcelLastWeek = 0;
        return null;
    }

    @Override
    public Double getChangeSucceedParcel() {
        return null;
    }

    @Override
    public Double getChangeFailedParcel() {
        return null;
    }

    //
    @Override
    public List<Long> countExamUserLastedSevenDaysTotal() {
//        List<Long> days = new ArrayList<>();
//        List<ExamUser> examUserList = examUserRepository.findExamUsersByOrderByTimeFinish();
//        long countDate = 0;
//        for (ExamUser examUser :
//                examUserList) {
//            int day = 0;
//            if (isSameDay(new LocalDateTime(new Date()).minusDays(day), new LocalDateTime(examUser.getTimeFinish()))) {
//                countDate++;
//            } else {
//                day++;
//                days.add(countDate);
//                countDate = 0;
//                if (day == 7) {
//                    break;
//                }
//            }
//            logger.error(days.toString());
//        }
//        Collections.reverse(days);
        List<Long> longList = new ArrayList<>();
        longList.add(1L);
        longList.add(2L);
        longList.add(5L);
        longList.add(5L);
        longList.add(8L);
        longList.add(1L);
        longList.add(0L);
        return longList;
    }
//
    @Override
    public Double getChangeQuestion() {
//        int countQuestionNow = 0;
//        int countQuestionLastWeek = 0;
//        List<Question> questions = questionRepository.findByOrderByCreatedDateDesc();
//        for (Question question :
//                questions) {
//            if (isSameWeek(new LocalDateTime(), new LocalDateTime(question.getCreatedDate()))) {
//                countQuestionNow++;
//            } else if (isLastWeek(new LocalDateTime(), new LocalDateTime(question.getCreatedDate()))) {
//                countQuestionLastWeek++;
//            } else break;
//        }
//        if (countQuestionNow == 0 && countQuestionLastWeek == 0) {
//            return 0.00;
//        }
//        if (countQuestionNow == 0 && countQuestionLastWeek != 0) {
//            return countQuestionLastWeek * -100.00;
//        }
//        if (countQuestionNow != 0 && countQuestionLastWeek == 0) {
//            return countQuestionNow * 100.00;
//        }
//        Double result = (double) countQuestionNow - countQuestionLastWeek;
//        double roundedRes = (double) result / countQuestionLastWeek;
//        roundedRes *= 100;
//        roundedRes = Math.round(roundedRes);
//        roundedRes /= 100;

//        return roundedRes * 100;
        return 12.0;
    }
//
    @Override
    public Double getChangeAccount() {
//        int countAccountNow = 0;
//        int countAccountLastWeek = 0;
//        List<User> users = userRepository.findByDeletedIsFalseOrderByCreatedDateDesc();
//        for (User user :
//                users) {
//            if (isSameWeek(new LocalDateTime(), new LocalDateTime(user.getCreatedDate()))) {
//                countAccountNow++;
//            } else if (isLastWeek(new LocalDateTime(), new LocalDateTime(user.getCreatedDate()))) {
//                countAccountLastWeek++;
//            } else break;
//        }
//        logger.error("account now: "+ countAccountNow);
//        logger.error("account last: "+ countAccountLastWeek);
//        if (countAccountNow == 0 && countAccountLastWeek == 0) {
//            return 0.00;
//        }
//        if (countAccountNow == 0 && countAccountLastWeek != 0) {
//            return countAccountLastWeek * -100.00;
//        }
//        if (countAccountNow != 0 && countAccountLastWeek == 0) {
//            return countAccountNow * 100.00;
//        }
//        Double result = (double) countAccountNow - countAccountLastWeek;
//        double roundedRes = (double) result / countAccountLastWeek;
//        roundedRes *= 100;
//        roundedRes = Math.round(roundedRes);
//        roundedRes /= 100;
//
//        return roundedRes * 100;
        return 12.0;
    }
//
    @Override
    public Double getChangeExam() {
//        int countExamNow = 0;
//        int countExamLastWeek = 0;
//        List<Exam> exams = examRepository.findByCanceledIsTrueOrderByCreatedDateDesc();
//        for (Exam exam :
//                exams) {
//            if (isSameWeek(new LocalDateTime(), new LocalDateTime(exam.getCreatedDate()))) {
//                countExamNow++;
//            } else if (isLastWeek(new LocalDateTime(), new LocalDateTime(exam.getCreatedDate()))) {
//                countExamLastWeek++;
//            } else break;
//        }
//        if (countExamNow == 0 && countExamLastWeek == 0) {
//            return 0.00;
//        }
//        if (countExamNow == 0 && countExamLastWeek != 0) {
//            return countExamLastWeek * -100.00;
//        }
//        if (countExamNow != 0 && countExamLastWeek == 0) {
//            return countExamNow * 100.00;
//        }
//        Double result = (double) countExamNow - countExamLastWeek;
//        double roundedRes = (double) result / countExamLastWeek;
//        roundedRes *= 100;
//        roundedRes = Math.round(roundedRes);
//        roundedRes /= 100;
//
//        return result * 100;
        return 12.4;
    }



    public static boolean isSameDay(final LocalDateTime d1, final LocalDateTime d2) {
        if ((d1 == null) || (d2 == null))
            throw new IllegalArgumentException("The date must not be null");
        return d1.toLocalDate().isEqual(d2.toLocalDate()) ;
    }
    public static boolean isSameWeek(final LocalDateTime d1, final LocalDateTime d2) {
        if ((d1 == null) || (d2 == null))
            throw new IllegalArgumentException("The date must not be null");

        return (d1.plusDays(DayOfWeek.MONDAY.getValue() - d1.getDayOfWeek().getValue()).toLocalDate()
                .isEqual(d2.plusDays(DayOfWeek.MONDAY.getValue() - d2.getDayOfWeek().getValue()).toLocalDate()));

    }

    public static boolean isLastWeek(final LocalDateTime d1, final LocalDateTime d2) {
        if ((d1 == null) || (d2 == null))
            throw new IllegalArgumentException("The date must not be null");

        return (d1.plusDays(DayOfWeek.MONDAY.getValue() - d1.getDayOfWeek().getValue() - 7).toLocalDate()
                .isEqual(d2.plusDays(DayOfWeek.MONDAY.getValue() - d2.getDayOfWeek().getValue()).toLocalDate()));
    }
}
