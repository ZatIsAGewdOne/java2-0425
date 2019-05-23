package lt.bit.java2.db.entities;

import lombok.Data;
import lt.bit.java2.db.Gender;
import lt.bit.java2.db.GenderConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Data
@Entity
@Table(name = "employees", schema = "employees")
@NamedEntityGraph(name = Employee.GRAPH_SALARY, attributeNodes = @NamedAttributeNode("salaries"))
public class Employee {

    public static final String GRAPH_SALARY = "graph.salary";

    @Id
    @Column(name = "emp_no", nullable = false)
    private Integer empNo;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "first_name", nullable = false, length = 14)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 16)
    private String lastName;

    @Column(name = "gender", nullable = false)
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Collection<Salary> salaries;
 }
