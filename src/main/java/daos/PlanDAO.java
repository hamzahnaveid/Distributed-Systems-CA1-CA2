package daos;

import java.util.ArrayList;  
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Plan;


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
	
	public Plan findPlanById(int id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Plan> plansWithId = new ArrayList<Plan>();
		plansWithId = em.createNamedQuery("Plan.findById").setParameter("id", id).getResultList();
		em.getTransaction().commit();
		em.close();
		Plan plan = null;
		for (Plan p : plansWithId) {
			plan = p;
		}
		return plan;	
	}

}
