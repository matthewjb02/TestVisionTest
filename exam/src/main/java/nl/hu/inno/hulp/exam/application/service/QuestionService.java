package nl.hu.inno.hulp.exam.application.service;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.data.QuestionRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.QuestionEntity;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.MultipleChoiceQuestionRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.OpenQuestionRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.request.QuestionRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.MultipleChoiceQuestionResponse;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.OpenQuestionResponse;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.response.QuestionResponse;
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

    public List<QuestionResponse> getAllQuestions() {
        List<QuestionEntity> allQuestions = questionRepository.findAll();
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (QuestionEntity question : allQuestions) {
            questionResponses.add(new QuestionResponse(question));
        }

        return questionResponses;
    }

    public QuestionResponse getQuestionById(Long id) {
        QuestionEntity question = questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No question with id: " + id + " found!"));

        if (question instanceof MultipleChoiceQuestion){
            return new MultipleChoiceQuestionResponse((MultipleChoiceQuestion) question);
        } else {
            return new OpenQuestionResponse((OpenQuestion) question);
        }
    }

    public QuestionResponse addQuestion(QuestionRequest question) {

        QuestionEntity entity;
        if (question instanceof MultipleChoiceQuestionRequest){
            entity = new MultipleChoiceQuestion(question.getPoints(), question.getQuestion(),
                    ((MultipleChoiceQuestionRequest) question).getCorrectAnswerIndexes(),
                    ((MultipleChoiceQuestionRequest) question).getAnswers());

        } else {
            entity = new OpenQuestion(question.getPoints(), question.getQuestion(), ((OpenQuestionRequest)question).getCorrectAnswer());
        }

        QuestionEntity savedEntity = questionRepository.save(entity);

//        if (entity instanceof MultipleChoiceQuestion){
//            return new MultipleChoiceQuestionResponse((MultipleChoiceQuestion) savedEntity);
//        } else {
//            return new OpenQuestionResponse((OpenQuestion) savedEntity);
//        }

        return getQuestionById(savedEntity.getId());
    }

    public QuestionResponse deleteQuestion(Long id) {
        QuestionResponse oldResponse = getQuestionById(id);
        questionRepository.deleteById(id);
        return oldResponse;
    }
}
