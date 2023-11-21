package Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ExamCalendar {
    private String courseName;
    private String partName;
    private String courseCode;
    private String examTitle;
    private Long examId;
    private int durationExam;
    private Date beginDate;
    private Date finishDate;
    private String completeString;
    private int isCompleted;
}
