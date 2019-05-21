package lt.bit.java2.db.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class SalaryPK implements Serializable {
    private int empNo;
    private LocalDate fromDate;

    @Column(name = "emp_no", nullable = false, insertable = false, updatable = false)
    @Id
    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    @Column(name = "from_date", nullable = false, insertable = false, updatable = false)
    @Id
    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalaryPK salaryPK = (SalaryPK) o;
        return empNo == salaryPK.empNo &&
                Objects.equals(fromDate, salaryPK.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, fromDate);
    }
}
