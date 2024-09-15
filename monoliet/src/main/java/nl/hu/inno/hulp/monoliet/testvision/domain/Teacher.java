package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Teacher {
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
    private boolean canIApproveThisTest(Teacher testValidator,Course course,Test test) throws Exception {
        if (course.getValidatingTests().contains(test)&&this==testValidator){
            return true;
        }
        else if(this!=testValidator&&course.getValidatingTests().contains(test)){
            throw new Exception("The Teacher is not assigned as validator, but the test needs to be Validated");
        }
        else throw new Exception("The test cannot be validated");
    }

    public void validateOtherTests(Course course, Test test) throws Exception {
        if (doesTeacherTeachCourse(course)&&course.getValidatingTests().contains(test)) {
            System.out.println(test.getQuestions());
        }
    }
    public void approveTest(Course course,Test test) throws Exception {
        if (doesTeacherTeachCourse(course)&&canIApproveThisTest(this,course,test)){
            test.validationStatus =Validation.APPROVED;
            course.getValidatingTests().remove(test);
            course.getApprovedTests().add(test);
        }
    }
    public void rejectTest(Course course,Test test, String reason) throws Exception {
        if (doesTeacherTeachCourse(course)&&canIApproveThisTest(this,course,test)) {
            test.validationStatus =Validation.DENIED;
            course.getValidatingTests().remove(test);
            course.getRejectedTests().add(test);
            test.reason=reason;
        }
    }
    public void viewWrongTest(Course course,Test test) throws Exception {
        if (Objects.equals(test.makerMail, this.email.getEmail()) &&course.getRejectedTests().contains(test)){
            System.out.println(test.reason);
        }
        else throw new Exception("This test was not rejected");
    }
    public void modifyQuestions(Course course, Test test, List<Question> oldQuestions, List<Question> newQuestion) throws Exception {
        if (Objects.equals(test.makerMail, this.email.getEmail()) &&course.getRejectedTests().contains(test)){
            System.out.println(test.reason);
            test.removeAllQuestions(oldQuestions);
            test.addAllQuestion(newQuestion);
            System.out.println(test.getQuestions());
            course.getValidatingTests().add(test);
            course.getRejectedTests().remove(test);
            test.validationStatus=Validation.WAITING;
            test.reason="";
        }
    }

}
