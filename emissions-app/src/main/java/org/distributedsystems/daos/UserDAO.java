package org.distributedsystems.daos;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.distributedsystems.entities.User;

public class UserDAO {
	
	protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaPU");
	
	public void persist(User user) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
	}
	
	public void remove(User user) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(user));
		em.getTransaction().commit();
		em.close();
	}
	
	public User merge(User user) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		User updatedUser = em.merge(user);
		em.getTransaction().commit();
		em.close();
		return updatedUser;
	}
	
	public List<User> getAllUsers() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<User> usersFromDb = new ArrayList<User>();
		usersFromDb = em.createNamedQuery("User.findAll").getResultList();
		em.getTransaction().commit();
		em.close();
		return usersFromDb;
	}
	
	public User findUserById(int id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<User> usersWithId = new ArrayList<User>();
		usersWithId = em.createNamedQuery("User.findById").setParameter("id", id).getResultList();
		em.getTransaction().commit();
		em.close();
		User user = null;
		for (User m : usersWithId) {
			user = m;
		}
		return user;	
	}

}
