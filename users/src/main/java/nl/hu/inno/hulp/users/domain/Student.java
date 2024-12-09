package nl.hu.inno.hulp.users.domain;

import com.aerospike.mapper.annotations.AerospikeKey;
import com.aerospike.mapper.annotations.AerospikeRecord;
import jakarta.persistence.Embedded;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AerospikeRecord(namespace="users", set="students")
public class Student extends User {
    @AerospikeKey
    private Long id;
    private boolean extraTimeRight = false;
    @Embedded
    private StudentEmail email;

    protected Student() {
        super();
    }

    public Student(Long id, String firstName, String lastName, boolean extraTimeRight,String email) {
        super(firstName, lastName);
        this.id = id;
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
