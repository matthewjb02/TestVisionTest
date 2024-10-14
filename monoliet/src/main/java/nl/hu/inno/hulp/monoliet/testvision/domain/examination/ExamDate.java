package nl.hu.inno.hulp.monoliet.testvision.domain.examination;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;

import java.util.Date;
import java.util.Objects;

@Embeddable
public class ExamDate {
    private Date beginDate;
    private Date endDate;

    protected ExamDate() {
    }

    public ExamDate(Date beginDate, Date endDate) {
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExamDate examDate)) return false;

        if (!Objects.equals(beginDate, examDate.beginDate)) return false;
        return Objects.equals(endDate, examDate.endDate);
    }

    @Access(AccessType.FIELD)
    public Date getBeginDate() {
        return beginDate;
    }

    @Access(AccessType.FIELD)
    public Date getEndDate() {
        return endDate;
    }
}
