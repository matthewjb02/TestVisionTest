//package nl.hu.inno.hulp.monoliet.testvision.domain.usecase;
//
//import nl.hu.inno.hulp.exam.domain.Course;
//import nl.hu.inno.hulp.exam.Exam;
//import nl.hu.inno.hulp.examination.ExamSession;
//import nl.hu.inno.hulp.examination.Examination;
//import nl.hu.inno.hulp.exam.domain.question.OpenQuestion;
//import nl.hu.inno.hulp.grading.Grading;
//import nl.hu.inno.hulp.grading.Submission;
//import nl.hu.inno.hulp.exam.GradingCriteria;
//import nl.hu.inno.hulp.exam.Statistics;
//import nl.hu.inno.hulp.users.Student;
//import nl.hu.inno.hulp.users.Teacher;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class TeacherGradesStudentExamination {
//
//    @Test
//    public void testSubmissionLifecycle() {
//
//        // Een student en docent registreren zich voor het systeem
//        Teacher maker = new Teacher("Neal", "Tyson", "neal.tyson@hu.nl");
//        Teacher examValidator =new Teacher("Bill","Nye","bill.nye@hu.nl");
//        Student student = new Student("Elon", "Musk", false);
//        Course course= new Course("1");
//
//        // Een docent maakt een vraag aan die hij kan toevoegen aan de aangemaakte toets. Ook voegt de docent toetsstatistieken toe
//        OpenQuestion question = new OpenQuestion(10, "What does the atomic symbol K name", "Potassium");
//        Exam exam = new Exam(course,maker,examValidator, question);
//        GradingCriteria gradingCriteria = new GradingCriteria(0.5, 0.5);
//        exam.addGradingCriteria(gradingCriteria);
//        exam.addStatistics(new Statistics());
//
//        // een student besluit de toets te maken. Hij beantwoordt de vragen en levert de toets in.
//        Examination examination = new Examination("toets1", exam, "hallo", null, 120, 30);
//
//        ExamSession examSession = new ExamSession(examination, student);
//        examSession.answerQuestion(1, "Potassium");
//        examSession.endSession();
//        examSession.getExam().addSubmission(new Submission(examSession)); // dit gebeurt in the real world in endExam() van de ExamService
//
//        // Een docent vraagt om de inleveringen van de toets. Hij beoordeelt de inlevering per vraag met evt. feedback toe.
//        Submission submission = examSession.getExam().getSubmissions().get(0);
//        submission.updateGradingForQuestion(1, 10, "Well Done!");
//
//        // De docent voegt de eindbeoordeling toe met evt. feedback en de toetsstatistieken worden automatisch bijgewerkt.
//        submission.addGrading(new Grading(submission.calculateGrade(), "Well Done!"));
//        submission.getExamSession().getExam().updateStatistics(); // dit gebeurt in de real world in addGrading() van de SubmissionService
//
//
//        Statistics statistics = exam.getStatistics();
//
//        OpenQuestion examQuestion = (OpenQuestion)submission.getExamSession().seeQuestion(1);
//
//        assertEquals(1, exam.getSubmissions().size());
//        assertEquals("Potassium", examQuestion.getAnswer());
//        assertEquals(10, submission.getExamSession().seeQuestion(1).getGivenPoints());
//        assertEquals("Well Done!", examQuestion.getTeacherFeedback());
//        assertEquals(10, submission.getGrading().getGrade());
//        assertEquals("Well Done!", submission.getGrading().getComments());
//        assertEquals(1, statistics.getPassCount());
//        assertEquals(0, statistics.getFailCount());
//        assertEquals(10.0, statistics.getAverageScore(), 0.01);
//    }
//}