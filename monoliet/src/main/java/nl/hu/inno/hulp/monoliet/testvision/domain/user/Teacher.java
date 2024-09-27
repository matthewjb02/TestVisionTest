package nl.hu.inno.hulp.monoliet.testvision.domain.user;

import jakarta.persistence.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.*;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Exam;
import nl.hu.inno.hulp.monoliet.testvision.domain.question.Question;
import nl.hu.inno.hulp.monoliet.testvision.domain.exam.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Teacher extends User {
    @GeneratedValue
    @Id
    private long id;
    private String firstName;
    private String lastName;
    @Embedded
    private TeacherEmail email;
    @ManyToMany
    private List<Course> courses;
    public Teacher() {}
    public Teacher(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = new TeacherEmail(email);
        this.courses = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getId() {
        return id;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public TeacherEmail getEmail() {
        return email;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }
    private boolean doesTeacherTeachCourse(Course course) throws Exception {
        if (courses.contains(course)) {
            return true;
        }
        else throw new Exception("The Teacher does not teach this course");
    }
    private boolean canIApproveThisExam(Teacher examValidator, Course course, Exam exam) throws Exception {
        if (course.getValidatingExams().contains(exam)&&this==examValidator){
            return true;
        }
        else if(this!=examValidator&&course.getValidatingExams().contains(exam)){
            throw new Exception("The Teacher is not assigned as validator, but the exam needs to be Validated");
        }
        else throw new Exception("The exam cannot be validated");
    }

    public void validateOtherExams(Course course, Exam exam) throws Exception {
        if (doesTeacherTeachCourse(course)&&course.getValidatingExams().contains(exam)) {
            System.out.println(exam.getQuestions());
        }
    }
    public void approveExam(Course course, Exam exam) throws Exception {
        if (doesTeacherTeachCourse(course)&& canIApproveThisExam(this,course, exam)){
            exam.setValidationStatus(Validation.APPROVED);
            course.getValidatingExams().remove(exam);
            course.getApprovedExams().add(exam);
        }
    }
    public void rejectExam(Course course, Exam exam, String reason) throws Exception {
        if (doesTeacherTeachCourse(course)&& canIApproveThisExam(this,course, exam)) {
            exam.setValidationStatus(Validation.DENIED);
            course.getValidatingExams().remove(exam);
            course.getRejectedExams().add(exam);
            exam.setReason(reason);
        }
    }
    public void viewWrongExam(Course course, Exam exam) throws Exception {
        if (Objects.equals(exam.getMakerMail(), this.email.getEmail()) &&course.getRejectedExams().contains(exam)){
            System.out.println(exam.getReason());
        }
        else throw new Exception("This exam was not rejected");
    }
    public void modifyQuestions(Course course, Exam exam, List<Question> oldQuestions, List<Question> newQuestion) throws Exception {
        if (Objects.equals(exam.getMakerMail(), this.email.getEmail()) &&course.getRejectedExams().contains(exam)){
            System.out.println(exam.getReason());
            exam.removeQuestions(oldQuestions);
            exam.addQuestions(newQuestion);
            System.out.println(exam.getQuestions());
            course.getValidatingExams().add(exam);
            course.getRejectedExams().remove(exam);
            exam.setValidationStatus(Validation.WAITING);
            exam.setReason("");
        }
    }
}
