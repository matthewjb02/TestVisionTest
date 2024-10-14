package nl.hu.inno.hulp.monoliet.testvision.domain.question;


public interface Question {
    Long getId();
    int getPoints();
    String getQuestion();
    int getGivenPoints();
    void addGivenPoints(int points);
    void setPoints(int points);
    void setQuestion(String question);
}
