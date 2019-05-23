package lt.bit.java2.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "salaries", schema = "employees")
@IdClass(SalaryPK.class)
@NamedQuery(name = Salary.QUERY_LIST, query = "from Salary where empNo = :empNo")
public class Salary {

    public static final String QUERY_LIST = "query.salary.list";

    @Id
    @Column(name = "emp_no", nullable = false, insertable = false, updatable = false)
    private Integer empNo;

    @Id
    @Column(name = "from_date", nullable = false, insertable = false, updatable = false)
    private LocalDate fromDate;

    @Column(name = "salary", nullable = false)
    private Integer salary;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no", nullable = false, insertable = false, updatable = false)
    private Employee employee;
}
