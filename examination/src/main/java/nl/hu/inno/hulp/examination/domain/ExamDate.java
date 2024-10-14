package nl.hu.inno.hulp.examination.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.time.ZoneId;
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

    public boolean checkDate() {
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return currentDate.before(endDate) && currentDate.after(beginDate);
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
