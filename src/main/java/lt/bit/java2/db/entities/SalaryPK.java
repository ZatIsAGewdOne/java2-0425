package lt.bit.java2.db.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class SalaryPK implements Serializable {

    @Id
    @Column(name = "emp_no", nullable = false, insertable = false, updatable = false)
    private Integer empNo;

    @Id
    @Column(name = "from_date", nullable = false, insertable = false, updatable = false)
    private LocalDate fromDate;
}
