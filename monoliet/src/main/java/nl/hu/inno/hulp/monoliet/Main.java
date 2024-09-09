package nl.hu.inno.hulp.monoliet;

import nl.hu.inno.hulp.monoliet.testvision.domain.*;

public class Main {
    public static void main(String[] args) {
        Course course1 = new Course("Course1");

        Question question1 = new MultipleChoiceQuestion(1, "What is D?", 3, "A", "B", "C", "D");
        Question question2 = new MultipleChoiceQuestion(1, "What is B?", 1, "A", "B", "C", "D");
        Question question3 = new MultipleChoiceQuestion(1, "What is C?", 2, "A", "B", "C", "D");
        Question question4 = new MultipleChoiceQuestion(1, "What is A?", 0, "A", "B", "C", "D");
        Question question5 = new OpenQuestion(1, "Is true true?", "Yes");
        Question question6 = new OpenQuestion(1, "Is true false?", "No");

        Test test1 = new Test(question1, question2, question3, question4);
        Test test2 = new Test(question5, question6);

        course1.addTest(test1);
        course1.addTest(test2);

        System.out.println(course1.getTests().get(0).getTotalPoints());
        System.out.println(course1.getTests().get(1).getTotalPoints());
    }
}
