package nl.hu.inno.hulp.monoliet.testvision.domain;

import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeacherTest {
    private static final Teacher maker=new Teacher("Neal","Tyson","neal.tyson@hu.nl");
    private static final Teacher testValidator=new Teacher("Bill","Nye","bill.nye@hu.nl");
    private static final Course course=new Course("scheikunde-1");
    private static final Question question=
            new OpenQuestion(1,"What does the atomic symbol K name","Potassium");

    @Test
    public void acceptTest() throws Exception {
        //give the teachers a course
        maker.addCourse(course);
        testValidator.addCourse(course);
        //initiate the test
        nl.hu.inno.hulp.monoliet.testvision.domain.test.Test test=
                new nl.hu.inno.hulp.monoliet.testvision.domain.test.Test(maker.getFirstName(), maker.getLastName(),question);
        //add the test to the course
        course.addTest(test);
        //approve the test
        testValidator.approveTest(course,test);
        //check whether the test is approved
        assertEquals(course.getApprovedTests().size(),1);
    }
}
