package Backend.repository;

import Backend.entity.ExamUser;
import Backend.exception.ErrorMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

@Repository
public interface ExamUserRepository extends JpaRepository<ExamUser, Long> {
    List<ExamUser> findAllByUser_Username(String username);
    List<ExamUser> findAllByUser_UsernameAndExam_Canceled(String username, boolean canceled);
    ExamUser findByExam_IdAndUser_Username(Long examId, String username);
    List<ExamUser> findAllByExam_Part_Course_IdAndUser_UsernameAndTotalPointIsGreaterThan(Long courseId, String username, Double point);
    List<ExamUser> findAllByExam_Id(Long examId);
    List<ExamUser> findExamUsersByOrderByTimeFinish();
    List<ExamUser> findExamUsersByIsFinishedIsTrueAndExam_Id(Long examId);

    @Modifying
    @Query(value = "UPDATE exam_user SET exam_user.is_started=true, time_start=:timeStart WHERE exam_user.id=:idExam" , nativeQuery = true)
    void updateTimeStart (Date timeStart, Long idExam);
}
