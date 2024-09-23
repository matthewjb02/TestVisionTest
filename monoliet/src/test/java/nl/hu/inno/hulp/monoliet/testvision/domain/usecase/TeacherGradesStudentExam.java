package nl.hu.inno.hulp.monoliet.testvision.domain.usecase;

import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Grading;
import nl.hu.inno.hulp.monoliet.testvision.domain.submission.Submission;
import nl.hu.inno.hulp.monoliet.testvision.domain.test.Statistics;
import nl.hu.inno.hulp.monoliet.testvision.domain.test.Test;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Student;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeacherGradesStudentExam {

    @org.junit.jupiter.api.Test
    public void testSubmissionLifecycle() {

        // Een student en docent registreren zich voor het systeem
        Teacher maker = new Teacher("Neal", "Tyson", "neal.tyson@hu.nl");
        Student student = new Student("Elon", "Musk");

        // Een docent maakt een vraag aan die hij kan toevoegen aan de aangemaakte toets. Ook voegt de docent toetsstatistieken toe
        OpenQuestion question = new OpenQuestion(10, "What does the atomic symbol K name", "Potassium");
        Test test = new Test(maker.getFirstName(), maker.getLastName(), question);
        test.addStatistics(new Statistics());

        // een student besluit de toets te maken. Hij beantwoordt de vragen en levert de toets in.
        Exam exam = new Exam(student, test);
        exam.answerQuestion(1, "Potassium");
        exam.endExam();
        exam.getTest().addSubmission(new Submission(exam)); // dit gebeurt in the real world in endExam() van de ExamService

        // Een docent vraagt om de inleveringen van de toets. Hij beoordeelt de inlevering per vraag met evt. feedback toe.
        Submission submission = exam.getTest().getSubmissions().get(0);
        submission.updateGradingForQuestion(1, 10, "Well Done!");

        // De docent voegt de eindbeoordeling toe met evt. feedback en de toetsstatistieken worden automatisch bijgewerkt.
        submission.addGrading(new Grading(submission.calculateGrade(), "Well Done!"));
        submission.getExam().getTest().updateStatistics(); // dit gebeurt in de real world in addGrading() van de SubmissionService


        Statistics statistics = test.getStatistics();

        OpenQuestion examQuestion = (OpenQuestion)submission.getExam().seeQuestion(1);

        assertEquals(1, test.getSubmissions().size());
        assertEquals("Potassium", examQuestion.getAnswer());
        assertEquals(10, submission.getExam().seeQuestion(1).getGivenPoints());
        assertEquals("Well Done!", submission.getExam().seeQuestion(1).getTeacherFeedback());
        assertEquals(10, submission.getGrading().getGrade());
        assertEquals("Well Done!", submission.getGrading().getComments());
        assertEquals(1, statistics.getPassCount());
        assertEquals(0, statistics.getFailCount());
        assertEquals(10.0, statistics.getAverageScore(), 0.01);
    }
}