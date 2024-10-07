package nl.hu.inno.hulp.monoliet.testvision.application.service;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.MultipleChoiceQuestionDTO;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.OpenQuestionDTO;
import nl.hu.inno.hulp.monoliet.testvision.application.dto.QuestionDTO;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.data.QuestionRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.QuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionDTO> getAllQuestions() {
        List<QuestionEntity> allQuestions = questionRepository.findAll();
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (QuestionEntity question : allQuestions) {
            questionDTOs.add(toDTO(question));
        }

        return questionDTOs;
    }

    public QuestionDTO getQuestionById(Long id) {
        QuestionEntity question = questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No question with id: " + id + " found!"));

        return toDTO(question);
    }

    public QuestionDTO addQuestion(QuestionEntity question) {
        questionRepository.save(question);

        return toDTO(question);
    }

    public QuestionDTO deleteQuestion(Long id) {
        QuestionDTO oldDTO = getQuestionById(id);
        questionRepository.deleteById(id);
        return oldDTO;
    }

    private QuestionDTO toDTO(QuestionEntity question) {
        if (question.getClass().equals(MultipleChoiceQuestion.class)){
            MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion)question;

            return new MultipleChoiceQuestionDTO(
                    mcQuestion.getId(),
                    mcQuestion.getPoints(),
                    mcQuestion.getQuestion(),
                    mcQuestion.getGivenPoints(),
                    mcQuestion.getAnswers(),
                    mcQuestion.getCorrectAnswerIndexes(),
                    mcQuestion.getGivenAnswers());
        } else {
            OpenQuestion openQuestion = (OpenQuestion)question;

            return new OpenQuestionDTO(
                    openQuestion.getId(),
                    openQuestion.getPoints(),
                    openQuestion.getQuestion(),
                    openQuestion.getGivenPoints(),
                    openQuestion.getTeacherFeedback(),
                    openQuestion.getCorrectAnswer(),
                    openQuestion.getAnswer());
        }
    }
}
