package org.distributedsystems.daos;

import java.util.ArrayList;
import java.util.List;

import org.distributedsystems.entities.Emission;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EmissionDAO {
	
	protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaPU");
	
	public void persist(Emission emission) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(emission);
		em.getTransaction().commit();
		em.close();
	}
	
	public void remove(Emission emission) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(emission));
		em.getTransaction().commit();
		em.close();
	}
	
	public Emission merge(Emission emission) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Emission updatedEmission = em.merge(emission);
		em.getTransaction().commit();
		em.close();
		return updatedEmission;
	}
	
	public List<Emission> getAllEmissions() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Emission> emissionsFromDb = new ArrayList<Emission>();
		emissionsFromDb = em.createNamedQuery("Emission.findAll").getResultList();
		em.getTransaction().commit();
		em.close();
		return emissionsFromDb;
	}
	
	public List<Emission> findEmissionsByCategory(String category) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Emission> emissionsWithCategory = new ArrayList<Emission>();
		emissionsWithCategory = em.createNamedQuery("Emission.findByCategory").setParameter("category", category).getResultList();
		em.getTransaction().commit();
		em.close();
		return emissionsWithCategory;	
	}

}
