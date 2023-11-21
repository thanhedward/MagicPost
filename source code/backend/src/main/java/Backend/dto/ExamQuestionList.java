package Backend.dto;

import Backend.entity.Exam;
import Backend.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamQuestionList {
    private Exam exam;
    private List<Question> questions;
    private int remainingTime;
}
