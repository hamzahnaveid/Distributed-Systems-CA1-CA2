package daos;

import java.util.ArrayList;
import java.util.List;

import entities.Gym;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

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

}
