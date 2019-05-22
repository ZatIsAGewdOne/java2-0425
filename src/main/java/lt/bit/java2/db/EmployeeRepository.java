package lt.bit.java2.db;

import lt.bit.java2.db.entities.Employee;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeRepository {

    public Employee get(Integer empNo) {
        EntityManager em = DBUtils.getEntityManager();
        Employee employee = em.find(Employee.class, empNo);
        em.close();

        return employee;
    }

    public Employee getWithSalary(Integer empNo) {
        EntityManager em = DBUtils.getEntityManager();

        EntityGraph entityGraph = em.getEntityGraph(Employee.GRAPH_SALARY);
        Map<String, Object> properties = Collections.singletonMap("javax.persistence.fetchgraph", entityGraph);

        Employee employee = em.find(Employee.class, empNo, properties);
        em.close();

        return employee;
    }

    public Employee create(Employee employee) {
        return DBUtils.executeInTransaction(em -> {
            em.persist(employee);
            return employee;
        });
    }

    public void delete(Integer empNo) {
        DBUtils.executeInTransaction(em -> {
            Employee employee = em.find(Employee.class, empNo);
            if (employee == null) throw new DBUtils.DBException("Entity not found");
            em.remove(employee);
            return true;
        });
    }

    public Employee update(Employee employee) {
        return DBUtils.executeInTransaction(em -> {
            Employee e = em.find(Employee.class, employee.getEmpNo());
            if (e == null) throw new DBUtils.DBException("Entity not found");
            return em.merge(employee);
        });
    }

    public List<Employee> list(String name, int pageSize, int offset) {
        //TODO
        return null;
    }

}
