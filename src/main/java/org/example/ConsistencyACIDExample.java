package org.example;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class ConsistencyACIDExample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private int quantity;

    public void save(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Query query = em.createQuery("SELECT p FROM ProductEntity p where p.id = :id");
            query.setParameter("id",product.getId());
            List<ProductEntity> products = query.getResultList();

            if (products.isEmpty())
                throw new RuntimeException("Invalid product id: "+ product.getId());

            em.persist(this);
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
    }finally {
            em.close();
            emf.close();
        }
        }

}
