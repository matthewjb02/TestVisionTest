package nl.hu.inno.hulp.exam.application.service;

import com.couchbase.client.core.deps.com.fasterxml.jackson.core.JsonProcessingException;
import com.couchbase.client.core.deps.com.fasterxml.jackson.databind.JsonMappingException;
import com.couchbase.client.core.deps.com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.commons.request.MultipleChoiceQuestionRequest;
import nl.hu.inno.hulp.commons.request.OpenQuestionRequest;
import nl.hu.inno.hulp.commons.request.QuestionRequest;
import nl.hu.inno.hulp.commons.response.MultipleChoiceQuestionResponse;
import nl.hu.inno.hulp.commons.response.OpenQuestionResponse;
import nl.hu.inno.hulp.commons.response.QuestionResponse;
import nl.hu.inno.hulp.exam.data.QuestionRepository;
import nl.hu.inno.hulp.exam.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.exam.domain.question.OpenQuestion;
import nl.hu.inno.hulp.exam.domain.question.QuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ObjectMapper objectMapper;
    private final CouchbaseTemplate couchbaseTemplate;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, ObjectMapper objectMapper, CouchbaseTemplate couchbaseTemplate) {
        this.questionRepository = questionRepository;
        this.objectMapper = objectMapper;
        this.couchbaseTemplate = couchbaseTemplate;
    }

    public List<QuestionResponse> getAllQuestions() {
        List<QuestionEntity> allQuestions = questionRepository.findAll();
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (QuestionEntity question : allQuestions) {
            questionResponses.add(getQuestionResponse(question));
        }

        return questionResponses;
    }

    public QuestionResponse getQuestionById(String id) {
       QuestionResponse question= null;
        try { Optional<QuestionEntity> questionEntity = Optional.ofNullable(couchbaseTemplate.findById(OpenQuestion.class).one(id));
            if (questionEntity.isPresent())
            { String entityAsString = objectMapper.writeValueAsString(questionEntity.get());
                System.out.println("Retrieved Entity JSON: " + entityAsString);
                question= getQuestionResponse(  objectMapper.readValue(entityAsString, OpenQuestion.class)) ;}
            else
            {
                Optional<QuestionEntity> questionEntity2 = Optional.ofNullable(couchbaseTemplate.findById(MultipleChoiceQuestion.class).one(id));

                if (questionEntity2.isPresent())
                { String entityAsString = objectMapper.writeValueAsString(questionEntity2.get());
                    System.out.println("Retrieved Entity JSON: " + entityAsString);
                   question= getQuestionResponse(  objectMapper.readValue(entityAsString, MultipleChoiceQuestion.class)); }}
    } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return question;
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

        return getQuestionResponse(savedEntity);
    }

    public QuestionResponse deleteQuestion(String id) {
        QuestionResponse oldResponse = getQuestionById(id);
        questionRepository.deleteById(id);
        return oldResponse;
    }

    public QuestionResponse getQuestionResponse(QuestionEntity question){
        if (question instanceof MultipleChoiceQuestion){
            MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion)question;
            return new MultipleChoiceQuestionResponse(mcQuestion.getId(), mcQuestion.getPoints(), mcQuestion.getQuestion(),
                    mcQuestion.getAnswers(),
                    mcQuestion.getCorrectAnswerIndexes(), mcQuestion.getGivenAnswers());
        } else {
            OpenQuestion openQuestion = (OpenQuestion) question;
            return new OpenQuestionResponse(openQuestion.getId(),openQuestion.getPoints(), openQuestion.getQuestion(), openQuestion.getCorrectAnswer(),
                    openQuestion.getAnswer(), openQuestion.getTeacherFeedback());
        }
    }
}
