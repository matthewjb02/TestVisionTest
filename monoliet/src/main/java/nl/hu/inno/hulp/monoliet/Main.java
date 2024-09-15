package nl.hu.inno.hulp.monoliet;

import nl.hu.inno.hulp.monoliet.testvision.domain.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.OpenQuestion;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.user.Teacher;

public class Main {
    public static void main(String[] args) throws Exception {
        Course course1 = new Course("Course1");

        Question question1 = new MultipleChoiceQuestion(1, "What is D?", 3, "A", "B", "C", "D");
        Question question2 = new MultipleChoiceQuestion(1, "What is B?", 1, "A", "B", "C", "D");
        Question question3 = new MultipleChoiceQuestion(1, "What is C?", 2, "A", "B", "C", "D");
        Question question4 = new MultipleChoiceQuestion(1, "What is A?", 0, "A", "B", "C", "D");
        Question question5 = new OpenQuestion(1, "Is true true?", "Yes");
        Question question6 = new OpenQuestion(1, "Is true false?", "No");
        Teacher teacher1= new Teacher("Jim","van Bim","jim.vanbim@hu.nl");
        Teacher teacher2=new Teacher("Elco", "Noom", "elco.noom@hu.nl");
        teacher1.addCourse(course1);
        teacher2.addCourse(course1);

        System.out.println(course1.getApprovedTests().get(0).getTotalPoints());
        System.out.println(course1.getApprovedTests().get(1).getTotalPoints());
    }
}
