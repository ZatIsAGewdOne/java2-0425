package lt.bit.java2.db;

import lt.bit.java2.db.entities.Product;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProductRepository {

    public Product get(Integer empNo) {
        EntityManager em = DBUtils.getEntityManager();
        Product product = em.find(Product.class, empNo);
        em.close();

        return product;
    }

    public Product create(Product product) {
        return DBUtils.executeInTransaction(em -> {
            em.persist(product);
            return product;
        });
    }

    public void delete(Integer id) {
        DBUtils.executeInTransaction(em -> {
            Product product = em.find(Product.class, id);
            if (product == null) throw new DBUtils.DBException("Entity not found");
            em.remove(product);
            return true;
        });
    }

    public Product update(Product product) {
        return DBUtils.executeInTransaction(em -> {
            Product e = em.find(Product.class, product.getId());
            if (e == null) throw new DBUtils.DBException("Entity not found");
            return em.merge(product);
        });
    }

    public List<Product> list(int pageSize, int offset) {
        EntityManager em = DBUtils.getEntityManager();
        TypedQuery<Product> products = em.createNamedQuery("product.list", Product.class);
        products.setFirstResult(offset);
        products.setMaxResults(pageSize);
        List<Product> result = products.getResultList();
        em.close();
        return result;
    }
}
