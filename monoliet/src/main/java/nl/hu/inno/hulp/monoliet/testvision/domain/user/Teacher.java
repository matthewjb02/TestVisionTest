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
    private Course findRightCourse(Exam exam) {
        Course courseFound = null;
            for (Course rightCourse : courses) {
                 int courseIndex=0;
                if (courses.get(courseIndex).getValidatingExams().contains(exam)) {
                    courseFound = rightCourse;
                }
                else if (courses.get(courseIndex).getRejectedExams().contains(exam)) {
                courseFound = rightCourse;
           }
                courseIndex+=1;
            }
        if (courseFound==null) {
            throw new IllegalArgumentException("the teacher does not teach the course  where the exam belongs to");
        }
            return courseFound;
    }

    private int indexOfCourseInList(Exam exam){

        return courses.indexOf(findRightCourse(exam));
    }

    public void validateOtherExams( Exam exam) throws Exception {
        if (doesTeacherTeachCourse(findRightCourse(exam))) {
            System.out.println(exam.getQuestions());
        }
    }
    public void approveExam(Exam exam) throws Exception {
        if (doesTeacherTeachCourse(findRightCourse(exam))&& canIApproveThisExam(this,findRightCourse(exam), exam)){
            exam.setValidationStatus(Validation.APPROVED);
            Course course=courses.get(indexOfCourseInList(exam));
            course.getValidatingExams().remove(exam);
            course.getApprovedExams().add(exam);
        }
    }
    public void rejectExam( Exam exam, String reason) throws Exception {
        if (doesTeacherTeachCourse(findRightCourse(exam))&& canIApproveThisExam(this,findRightCourse(exam), exam)) {
            exam.setValidationStatus(Validation.DENIED);
            Course course=courses.get(indexOfCourseInList(exam));
            course.getValidatingExams().remove(exam);
            course.getRejectedExams().add(exam);
            exam.setReason(reason);
        }
    }
    public void viewWrongExam( Exam exam) throws Exception {
        if (Objects.equals(exam.getMakerMail(), this.email.getEmail()) &&findRightCourse(exam).getRejectedExams().contains(exam)){
            System.out.println(exam.getReason());
        }
        else throw new Exception("This exam was not rejected");
    }
    public void modifyQuestions( Exam exam, List<Question> oldQuestions, List<Question> newQuestion) {
        if (Objects.equals(exam.getMakerMail(), this.email.getEmail()) &&findRightCourse(exam).getRejectedExams().contains(exam)){
            exam.removeQuestions(oldQuestions);
            exam.addQuestions(newQuestion);
            Course course=courses.get(indexOfCourseInList(exam));
            course.getValidatingExams().add(exam);
            course.getRejectedExams().remove(exam);
            exam.setValidationStatus(Validation.WAITING);
            exam.setReason("");
        }
    }
}
