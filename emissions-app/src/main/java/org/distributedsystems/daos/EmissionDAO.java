package org.distributedsystems.daos;

import java.util.ArrayList; 
import java.util.List;

import org.distributedsystems.entities.Emission;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EmissionDAO {
	
	@Inject
	EntityManager em;
	
	
	@Transactional
	public void persist(Emission emission) {
		em.persist(emission);
	}
	
	@Transactional
	public void remove(Emission emission) {
		em.remove(em.merge(emission));
	}
	
	@Transactional
	public Emission merge(Emission emission) {
		Emission updatedEmission = em.merge(emission);
		return updatedEmission;
	}
	
	@Transactional
	public List<Emission> getAllEmissions() {
		List<Emission> emissionsFromDb = new ArrayList<Emission>();
		emissionsFromDb = em.createNamedQuery("Emission.findAll").getResultList();
		return emissionsFromDb;
	}
	
	@Transactional
	public List<Emission> findEmissionsByCategory(String category) {
		List<Emission> emissionsWithCategory = new ArrayList<Emission>();
		emissionsWithCategory = em.createNamedQuery("Emission.findByCategory").setParameter("category", category).getResultList();
		return emissionsWithCategory;
	}

}
