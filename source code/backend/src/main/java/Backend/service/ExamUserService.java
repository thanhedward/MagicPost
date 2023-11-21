package Backend.service;

import Backend.entity.Exam;
import Backend.entity.ExamUser;
import Backend.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExamUserService {
    void create(Exam exam, List<User> userSet);
    List<ExamUser> getExamListByUsername(String username);
    ExamUser findByExamAndUser(Long examId, String username);
    void update(ExamUser examUser);
    Optional<ExamUser> findExamUserById(Long id);

    List<ExamUser> getCompleteExams(Long courseId, String username);
    List<ExamUser> findAllByExam_Id(Long examId);
    List<ExamUser> findExamUsersByIsFinishedIsTrueAndExam_Id(Long examId);

    void updateTimeStart(Date timeStart, Long idExam);


}
