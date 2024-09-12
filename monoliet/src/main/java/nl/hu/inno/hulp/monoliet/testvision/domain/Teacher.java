package nl.hu.inno.hulp.monoliet.testvision.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Teacher {
    @GeneratedValue
    @Id
    private long id;
   @Embedded
   private Person person;
    @Embedded
    private TeacherEmail email;
    @ManyToMany
    @JoinTable(
            name = "teacher_course",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;
    public Teacher() {}
    public Teacher(String firstName, String lastName, String email) {
        this.person = new Person(firstName, lastName);
        this.email = new TeacherEmail(email);
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
            test.validation =Validation.APPROVED;
            course.getValidatingTests().remove(test);
            course.getApprovedTests().add(test);
        }
    }
    public void rejectTest(Course course,Test test, String reason) throws Exception {
        if (doesTeacherTeachCourse(course)&&canIApproveThisTest(this,course,test)) {
            test.validation =Validation.DENIED;
            course.getValidatingTests().remove(test);
            course.getRejectedTests().add(test);
            test.reason=reason;
        }
    }
    public void viewWrongTest(Course course,Test test) throws Exception {
        if (test.Maker==this&&course.getRejectedTests().contains(test)){
            System.out.println(test.reason);
        }
    }
    public void modifyQuestions(Course course, Test test, List<Question> oldQuestions, List<Question> newQuestion) throws Exception {
        if (test.Maker==this&&course.getRejectedTests().contains(test)){
            System.out.println(test.reason);
            test.getQuestions().removeAll(oldQuestions);
            test.getQuestions().addAll(newQuestion);
        }
    }
    public void ensembleTest(Teacher testValidator, Course course, Question... questions) throws Exception {

        if (doesTeacherTeachCourse(course)&&testValidator.courses.contains(course)&&this!=testValidator) {
            Test test = new Test(questions);
            test.Maker=this;
            course.getValidatingTests().add(test);
            test.testValidator=testValidator;
        }if(!testValidator.courses.contains(course)){
            throw new Exception("The Validator does not teach this course");
        }
        if (this==testValidator){
            throw new Exception("You cannot Validate your own test");
        }
    }
}
