package lt.bit.java2.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "salaries", schema = "employees")
@IdClass(SalaryPK.class)
public class Salary {
    @Id
    @Column(name = "emp_no", nullable = false, insertable = false, updatable = false)
    private Integer empNo;

    @Column(name = "salary", nullable = false)
    private Integer salary;

    @Column(name = "from_date", nullable = false, insertable = false, updatable = false)
    private LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no", nullable = false)
    private Employee employee;
}
