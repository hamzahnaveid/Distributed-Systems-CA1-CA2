package daos;

import java.util.ArrayList; 
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Gym;
import entities.Member;


public class GymDAO {
	
protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaPU");
	
	public void persist(Gym gym) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(gym);
		em.getTransaction().commit();
		em.close();
	}
	
	public void remove(Gym gym) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(gym));
		em.getTransaction().commit();
		em.close();
	}
	
	public Gym merge(Gym gym) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Gym updatedGym = em.merge(gym);
		em.getTransaction().commit();
		em.close();
		return updatedGym;
	}
	
	public List<Gym> getAllGyms() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Gym> gymsFromDb = new ArrayList<Gym>();
		gymsFromDb = em.createNamedQuery("Gym.findAll").getResultList();
		em.getTransaction().commit();
		em.close();
		return gymsFromDb;
	}
	
	public Gym findGymById(int id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Gym> gymsWithId = new ArrayList<Gym>();
		gymsWithId = em.createNamedQuery("Gym.findById").setParameter("id", id).getResultList();
		em.getTransaction().commit();
		em.close();
		Gym gym = null;
		for (Gym g : gymsWithId) {
			gym = g;
		}
		return gym;	
	}

}
