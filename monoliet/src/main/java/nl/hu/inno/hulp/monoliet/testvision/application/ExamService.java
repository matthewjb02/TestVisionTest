package nl.hu.inno.hulp.monoliet.testvision.application;

import nl.hu.inno.hulp.monoliet.testvision.data.ExamRepository;
import nl.hu.inno.hulp.monoliet.testvision.data.StudentRepository;
import nl.hu.inno.hulp.monoliet.testvision.domain.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.State;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.ExamInactiveException;
import nl.hu.inno.hulp.monoliet.testvision.domain.exception.NoExamFoundException;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.AnswerRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.ExamRequest;
import nl.hu.inno.hulp.monoliet.testvision.presentation.dto.StartExamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ExamService {
    private final ExamRepository examRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public ExamService(ExamRepository examRepository, StudentRepository studentRepository) {
        this.examRepository = examRepository;
        this.studentRepository = studentRepository;
    }

    public Exam startExam(StartExamRequest examRequest) {
        //Exam exam = new Exam();
        //examRepository.save(exam);
        return null;
    }

    public void seeQuestion(ExamRequest examRequest)  {
        Exam exam = getExamById(examRequest.id);

        if (exam.getState() == State.Active) {
            exam.seeQuestion();
        } else {
            throw new ExamInactiveException("This exam is inactive");
        }
    }

    public void enterAnswer(AnswerRequest answerRequest) {
        Exam exam = getExamById(answerRequest.id);

        if (exam.getState() == State.Active) {
            exam.answerQuestion(answerRequest.answer);
            examRepository.save(exam);
        } else {
            throw new ExamInactiveException("This exam is inactive");
        }
    }

    public Exam endExam(ExamRequest examRequest) {
        Exam exam = getExamById(examRequest.id);

        if (exam.getState() == State.Active) {
            return exam.endExam();
        } else {
            throw new ExamInactiveException("This exam is already inactive");
        }
    }

    public Exam getExamById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new NoExamFoundException("No exam with id: " + id + " found!"));
    }
}
