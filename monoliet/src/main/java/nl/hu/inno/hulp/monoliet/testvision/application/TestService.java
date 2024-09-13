package nl.hu.inno.hulp.monoliet.testvision.application;

import nl.hu.inno.hulp.monoliet.testvision.data.QuestionRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.TestRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public TestService(TestRepository testRepository, QuestionRepository questionRepository) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
    }

    public List<TestDTO> getAllTests() {
        List<Test> allTests = testRepository.findAll();
        List<TestDTO> testDTOs = new ArrayList<>();
        for (Test test : allTests) {
            testDTOs.add(toDTO(test));
        }

        return testDTOs;
    }

    public TestDTO getTestById(Long id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No test with id: " + id + " found!"));

        return toDTO(test);
    }

    public TestDTO addTest(Test test) {
        Test savedTest = testRepository.save(test);
        return toDTO(savedTest);
    }

    public TestDTO deleteTest(Long id) {
        TestDTO oldTestDTO = getTestById(id);
        testRepository.deleteById(id);
        return oldTestDTO;
    }

    public TestDTO addQuestionsByIdsToTest(Long testId, List<Long> questionIds) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Test not found"));

        List<Question> newQuestions = questionRepository.findAllById(questionIds);

        test.getQuestions().addAll(newQuestions);
        test.calculateTotalPoints();
        testRepository.save(test);
        return toDTO(test);
    }

    private TestDTO toDTO(Test test) {
        return new TestDTO(
                test.getId(),
                test.getQuestionsAsString(),
                test.getTotalPoints()
        );
    }
}
