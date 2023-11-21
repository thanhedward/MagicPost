package Backend.dto;

import Backend.entity.Exam;
import lombok.Data;

import java.util.List;

@Data
public class ExamDto {
    private Exam exam;
    private List<ExamQuestionPoint> examQuestionPoints;
}
