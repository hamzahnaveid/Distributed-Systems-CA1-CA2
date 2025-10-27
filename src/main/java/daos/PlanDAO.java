package daos;

import java.util.ArrayList;
import java.util.List;

import entities.Plan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PlanDAO {
	
protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaPU");
	
	public void persist(Plan plan) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(plan);
		em.getTransaction().commit();
		em.close();
	}
	
	public void remove(Plan plan) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(plan));
		em.getTransaction().commit();
		em.close();
	}
	
	public Plan merge(Plan plan) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Plan updatedPlan = em.merge(plan);
		em.getTransaction().commit();
		em.close();
		return updatedPlan;
	}
	
	public List<Plan> getAllPlans() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Plan> plansFromDb = new ArrayList<Plan>();
		plansFromDb = em.createNamedQuery("Plan.findAll").getResultList();
		em.getTransaction().commit();
		em.close();
		return plansFromDb;
	}
	
	public List<Plan> findPlansByMemberId(int id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Plan> plansWithId = new ArrayList<Plan>();
		plansWithId = em.createNamedQuery("Plan.findByMemberId").setParameter("id", id).getResultList();
		em.getTransaction().commit();
		em.close();
		return plansWithId;
	}

}
