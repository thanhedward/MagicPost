package Backend.service;

import Backend.entity.QuestionType;
import Backend.utilities.EQTypeCode;

import java.util.List;
import java.util.Optional;

public interface QuestionTypeService {
    Optional<QuestionType> getQuestionTypeById(Long id);

    Optional<QuestionType> getQuestionTypeByCode(EQTypeCode code);

    List<QuestionType> getQuestionTypeList();

    void saveQuestionType(QuestionType questionType);

    void delete(Long id);

    boolean existsById(Long id);
}
