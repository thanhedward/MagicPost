package Backend.service;

import Backend.dto.AnswerSheet;
import Backend.dto.ChoiceCorrect;
import Backend.dto.ChoiceList;
import Backend.dto.ExamQuestionPoint;
import Backend.entity.Exam;
import Backend.entity.Question;
import Backend.repository.ExamRepository;
import Backend.repository.IntakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class ExamServiceImpl implements ExamService {

    private ExamRepository examRepository;
    private IntakeRepository intakeRepository;
    private PartService partService;
    private UserService userService;
    private QuestionService questionService;
    private ChoiceService choiceService;

    @Autowired
    public ExamServiceImpl(ExamRepository examRepository, IntakeRepository intakeRepository, PartService partService, UserService userService, QuestionService questionService, ChoiceService choiceService) {
        this.examRepository = examRepository;
        this.intakeRepository = intakeRepository;
        this.partService = partService;
        this.userService = userService;
        this.questionService = questionService;
        this.choiceService = choiceService;
    }

    @Override
    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    @Override
    public Page<Exam> findAll(Pageable pageable) {
        return examRepository.findAll(pageable);
    }

    @Override
    public void cancelExam(Long id) {
        examRepository.cancelExam(id);
    }

    @Override
    public List<Exam> getAll() {
        return examRepository.findAll();
    }

    @Override
    public Optional<Exam> getExamById(Long id) {
        return examRepository.findById(id);
    }


    @Override
    public Page<Exam> findAllByCreatedBy_Username(Pageable pageable, String username) {
        return examRepository.findAllByCreatedBy_Username(pageable, username);
    }


    @Override
    public List<ChoiceList> getChoiceList(List<AnswerSheet> userChoices, List<ExamQuestionPoint> examQuestionPoints) {
        List<ChoiceList> choiceLists = new ArrayList<>();
        userChoices.forEach(userChoice -> {
            ChoiceList choiceList = new ChoiceList();
            Question question = questionService.getQuestionById(userChoice.getQuestionId()).get();
            choiceList.setQuestion(question);
            choiceList.setPoint(userChoice.getPoint());

            List<ChoiceCorrect> choiceCorrects = new ArrayList<>();
            switch (question.getQuestionType().getTypeCode()) {
                case TF: {
                    userChoice.getChoices().forEach(choice -> {
                        ChoiceCorrect choiceCorrect = new ChoiceCorrect();

                        choiceCorrect.setChoice(choice);
                        String choiceText = choiceService.findChoiceTextById(choice.getId());
                        Integer isRealCorrect;
                        if (choice.getChoiceText().equals(choiceText) && choice.getIsCorrected() == 1) {
                            isRealCorrect = 1;
                            choiceList.setIsSelectedCorrected(true);
                        } else {
                            isRealCorrect = 0;
                            choiceList.setIsSelectedCorrected(false);
                        }
                        choiceCorrect.setIsRealCorrect(isRealCorrect);
                        choiceCorrects.add(choiceCorrect);
                    });
                    break;
                }
                case MC: {

                    choiceList.setIsSelectedCorrected(false);
                    userChoice.getChoices().forEach(choice -> {
                        ChoiceCorrect choiceCorrect = new ChoiceCorrect();
                        choiceCorrect.setChoice(choice);
                        Integer isRealCorrect = choiceService.findIsCorrectedById(choice.getId());
                        choiceCorrect.setIsRealCorrect(isRealCorrect);
                        if (choice.getIsCorrected() == isRealCorrect && isRealCorrect == 1) {
                            choiceList.setIsSelectedCorrected(true);
                        }
                        choiceCorrects.add(choiceCorrect);
                    });
                    break;
                }
                case MS: {
                    choiceList.setIsSelectedCorrected(true);
                    userChoice.getChoices().forEach(choice -> {
                        ChoiceCorrect choiceCorrect = new ChoiceCorrect();
                        choiceCorrect.setChoice(choice);
                        Integer isRealCorrect = choiceService.findIsCorrectedById(choice.getId());
                        choiceCorrect.setIsRealCorrect(isRealCorrect);
                        if (choice.getIsCorrected() == 0 && isRealCorrect == 1) {
                            choiceList.setIsSelectedCorrected(false);
                        }
                        choiceCorrects.add(choiceCorrect);
                    });
                    break;
                }
            }

            //set choices
            choiceList.setChoices(choiceCorrects);

            choiceLists.add(choiceList);
        });
        return choiceLists;
    }
}
