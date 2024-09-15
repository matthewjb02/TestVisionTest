package nl.hu.inno.hulp.monoliet.testvision.presentation;

import nl.hu.inno.hulp.monoliet.testvision.application.TestDTO;
import nl.hu.inno.hulp.monoliet.testvision.application.TestService;
import nl.hu.inno.hulp.monoliet.testvision.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public List<TestDTO> getAllTests() {
        return testService.getAllTests();
    }

    @GetMapping("/{id}")
    public TestDTO getTestById(@PathVariable Long id) {
        return testService.getTestById(id);
    }

    @PostMapping("testMaker/{makerId}/testValidator/{testValidatorId}")
    public TestDTO addTest(@RequestBody Test test, @PathVariable Long makerId, @PathVariable Long testValidatorId) {
        return testService.addTest(test, makerId, testValidatorId);
    }

    @DeleteMapping("/{id}")
    public TestDTO deleteTest(@PathVariable Long id) {
        return testService.deleteTest(id);
    }

    @PostMapping("/{testId}/questions")
    public TestDTO addQuestionsByIdsToTest(@PathVariable Long testId, @RequestBody List<Long> questionIds) {
        return testService.addQuestionsByIdsToTest(testId, questionIds);
    }
}
