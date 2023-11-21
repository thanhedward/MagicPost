package Backend.repository;

import Backend.entity.Part;
import Backend.entity.Question;
import Backend.entity.QuestionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByPart(Part part);

    List<Question> findByOrderByCreatedDateDesc();

    List<Question> findByQuestionType(QuestionType questionType);

    Page<Question> findQuestionsByPart(Pageable pageable, Part part);

    Page<Question> findQuestionsByPartAndDeletedFalse(Pageable pageable, Part part);

    Page<Question> findQuestionsByPart_IdAndCreatedBy_Username(Pageable pageable, Long partId, String username);

    Page<Question> findQuestionsByPart_IdAndCreatedBy_UsernameAndDeletedFalse(Pageable pageable, Long partId, String username);

    Page<Question> findAll(Pageable pageable);

    Page<Question> findQuestionsByCreatedBy_Username(Pageable pageable, String username);

    @Query(value = "select q.id from question q where q.id =:questionId", nativeQuery = true)
    String findQuestionTextById(Long questionId);
}
