package lt.bit.java2.db

import java.time.LocalDate

data class Employee(
        var empNo: Int?,
        var firstName: String,
        var lastName: String,
        var birthDate: LocalDate,
        var hireDate: LocalDate,
        var gender: Gender)
