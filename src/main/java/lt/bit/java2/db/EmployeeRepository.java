package lt.bit.java2.db;

import lt.bit.java2.db.entities.Employee;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

public class EmployeeRepository {

    public Employee get(Integer empNo) {
        EntityManager em = DBUtils.getEntityManager();
        return em.find(Employee.class, empNo);
    }

    public void create(Employee employee) {
        EntityManager em = DBUtils.getEntityManager();
        em.persist(employee);
    }

    public List<Employee> list(String name, int pageSize, int offset) {
        //TODO
        return null;
    }

}
