package lt.bit.java2.db;

import lt.bit.java2.db.entities.Salary;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class SalaryRepository {

    public List<Salary> listByEmpNo(int empNo) {
        EntityManager em = DBUtils.getEntityManager();

        TypedQuery<Salary> query = em.createNamedQuery(Salary.QUERY_LIST, Salary.class);
        query.setParameter("empNo", empNo);
        List<Salary> list = query.getResultList();

        em.close();

        return list;
    }

    public Salary create(Salary salary) {
        return DBUtils.executeInTransaction(em -> {
            em.persist(salary);
            return salary;
        });
    }
}
