package daos;

import java.util.ArrayList; 
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Payment;


public class PaymentDAO {
	
protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaPU");
	
	public void persist(Payment payment) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(payment);
		em.getTransaction().commit();
		em.close();
	}
	
	public void remove(Payment payment) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(payment));
		em.getTransaction().commit();
		em.close();
	}
	
	public Payment merge(Payment payment) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Payment updatedPayment = em.merge(payment);
		em.getTransaction().commit();
		em.close();
		return updatedPayment;
	}
	
	public List<Payment> getAllPayments() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Payment> paymentsFromDb = new ArrayList<Payment>();
		paymentsFromDb = em.createNamedQuery("Payment.findAll").getResultList();
		em.getTransaction().commit();
		em.close();
		return paymentsFromDb;
	}
	
	public List<Payment> findPaymentsByMemberId(int id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Payment> paymentsWithId = new ArrayList<Payment>();
		paymentsWithId = em.createNamedQuery("Payment.findByMemberId").setParameter("id", id).getResultList();
		em.getTransaction().commit();
		em.close();
		return paymentsWithId;
	}

}
