package nl.hu.inno.hulp.examination.application.service;

import nl.hu.inno.hulp.commons.enums.ExamState;
import nl.hu.inno.hulp.commons.exception.ExamSessionNotStored;
import nl.hu.inno.hulp.commons.exception.ExaminationInactiveException;
import nl.hu.inno.hulp.commons.exception.NoExamSessionFoundException;
import nl.hu.inno.hulp.commons.exception.NotAllowedException;
import nl.hu.inno.hulp.commons.request.AnswerRequest;
import nl.hu.inno.hulp.commons.request.ExamSessionRequest;
import nl.hu.inno.hulp.commons.request.SeeQuestion;
import nl.hu.inno.hulp.commons.request.StartExamSession;
import nl.hu.inno.hulp.examination.data.ExamSessionRepository;
import nl.hu.inno.hulp.examination.domain.ExamSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ExamSessionService {
    private final ExamSessionRepository examSessionRepository;
    private final StudentService studentService;
    private final ExaminationService examinationService;

    @Autowired
    public ExamSessionService(ExamSessionRepository examSessionRepository, StudentService studentService,
                              ExaminationService examinationService,) {
        this.examSessionRepository = examSessionRepository;
        this.studentService = studentService;
        this.examinationService = examinationService;
    }

    public ExamSession startExamSession(StartExamSession request) {
        if (examinationService.validatingStudent(request)) {
            ExamSession examSession = new ExamSession(examinationService.getExaminationById(request.examinationId()),
                    studentService.getStudent(request.studentId()));

            examSession.startSession(request.password());
            examSessionRepository.save(examSession);

            return examSession;
        }

        throw new NotAllowedException("Student is not allowed to start session because student is not a candidate.");
    }

    public QuestionEntity seeQuestion(SeeQuestion examRequest)  {
        ExamSession examSession = getExamSessionById(examRequest.examSessionId());

        if (examSession.getState() == ExamState.Active) {
            return examSession.seeQuestion(examRequest.questionNr());
        } else {
            throw new ExaminationInactiveException("This exam is inactive");
        }
    }

    public ExamSession enterAnswer(AnswerRequest request) {
        ExamSession examSession = getExamSessionById(request.examSessionId());

        if (examSession.getState() == ExamState.Active) {
            examSession.answerQuestion(request.questionNr(), request.answer());
            examSessionRepository.save(examSession);
            return examSession;
        } else {
            throw new ExaminationInactiveException("This exam is inactive");
        }
    }

    public ExamSession endExamSession(ExamSessionRequest request) {
        ExamSession examSession = getExamSessionById(request.examSessionId());

        if (examSession.getState() == ExamState.Active) {
            examSession.endSession();

            if (!examinationService.storeExamSession(examSession)) {
                throw new ExamSessionNotStored("Exam session can't be stored in examination.");
            }

            return examSession;

        } else {
            throw new ExaminationInactiveException("This exam is already completed");
        }
    }

    public ExamSession getExamSessionById(Long id) {
        return examSessionRepository.findById(id)
                .orElseThrow(() -> new NoExamSessionFoundException("No exam session with id: " + id + " found!"));
    }
}
