package nl.hu.inno.hulp.monoliet.testvision.application;

import nl.hu.inno.hulp.monoliet.testvision.domain.Question;
import nl.hu.inno.hulp.monoliet.testvision.data.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionDTO> getAllQuestions() {
        List<Question> allQuestions = questionRepository.findAll();
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Question question : allQuestions) {
            questionDTOs.add(toDTO(question));
        }

        return questionDTOs;
    }

    public QuestionDTO getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No question with id: " + id + " found!"));

        return toDTO(question);
    }

    public QuestionDTO addQuestion(Question question) {
        questionRepository.save(question);

        return toDTO(question);
    }

    public QuestionDTO deleteQuestion(Long id) {
        QuestionDTO oldDTO = getQuestionById(id);
        questionRepository.deleteById(id);
        return oldDTO;
    }

    private QuestionDTO toDTO(Question question) {
        return new QuestionDTO(
                question.getId(),
                question.getPoints(),
                question.getQuestion()
        );
    }
}
