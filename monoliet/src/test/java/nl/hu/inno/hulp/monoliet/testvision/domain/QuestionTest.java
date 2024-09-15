package nl.hu.inno.hulp.monoliet.testvision.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    @Test
    @DisplayName("Question is generated correctly")
    void testCreateQuestion(){
        Question question = new Question(10, "Question1");

        assertEquals(10, question.getPoints());
        assertEquals("Question1", question.getQuestion());
        assertEquals("", question.getAnswer());
    }

    @Test
    @DisplayName("Answer is set correctly")
    void testSetAnswer(){
        Question question = new Question(10, "Question1");
        question.setAnswer("Answer");

        assertEquals("Answer", question.getAnswer());
    }

}