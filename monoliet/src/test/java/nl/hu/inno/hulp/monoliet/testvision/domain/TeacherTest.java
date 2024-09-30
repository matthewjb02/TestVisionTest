package nl.hu.inno.hulp.monoliet.testvision.domain;

import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeacherTest {
    private static final Teacher maker=new Teacher("Neal","Tyson","neal.tyson@hu.nl");
    private static final Teacher examValidator =new Teacher("Bill","Nye","bill.nye@hu.nl");
    private static final Course course=new Course("scheikunde-1");
    private static final Question question=
            new OpenQuestion(1,"What does the atomic symbol K name","Potassium");

//    @Test
//    @DisplayName("test whether the approved exam has been approved")
//    public void acceptExam() throws Exception {
//        //give the teachers a course
//        maker.addCourse(course);
//        examValidator.addCourse(course);
//        //initiate the exam
//        Exam exam =
//                new Exam(maker.getFirstName(), maker.getLastName(),question);
//        //add the exam to the course
//        course.addExam(exam);
//        //approve the exam
//        examValidator.approveExam( exam);
//
//        //check whether the exam has been added to the list of approved exams
//        assertEquals(course.getApprovedExams().size(),1);
//    }
}
