package lt.bit.java2;

import java.time.LocalDate;

public class Employee {

    Integer empNo;

    String firstName;

    String lastName;

    LocalDate birthDate;

    LocalDate hireDate;

    public Employee(Integer empNo, String firstName, String lastName, LocalDate birthDate, LocalDate hireDate) {
        this.empNo = empNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empNo=" + empNo +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", hireDate=" + hireDate +
                '}';
    }
}
