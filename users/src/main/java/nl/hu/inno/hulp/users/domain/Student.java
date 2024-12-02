package nl.hu.inno.hulp.users.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.aerospike.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document
public class Student extends User {
    @Id
    private Long id;
    private boolean extraTimeRight = false;
    @Embedded
    private StudentEmail email;

    protected Student() {
        super();
    }

    public Student(String firstName, String lastName, boolean extraTimeRight,String email) {
        super(firstName, lastName);
        this.extraTimeRight = extraTimeRight;
        this.email = new StudentEmail(email);
    }

    public void changeExtraTimeRight(boolean right) {
        this.extraTimeRight = right;
    }

    public boolean isExtraTimeRight() {
        return extraTimeRight;
    }
}
