package org.distributedsystems.daos;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.transaction.Transactional;

import org.distributedsystems.entities.User;

@ApplicationScoped
public class UserDAO {
	
	@Inject
	EntityManager em;
	
	
	@Transactional
	public void persist(User user) {
		em.persist(user);
	}
	
	@Transactional
	public void remove(User user) {
		em.remove(em.merge(user));
	}
	
	@Transactional
	public User merge(User user) {
		User updatedUser = em.merge(user);
		return updatedUser;
	}
	
	@Transactional
	public List<User> getAllUsers() {
		List<User> usersFromDb = new ArrayList<User>();
		usersFromDb = em.createNamedQuery("User.findAll").getResultList();
		return usersFromDb;
	}
	
	@Transactional
	public User findUserByEmail(String email) {
		List<User> userWithEmail = new ArrayList<User>();
		userWithEmail = em.createNamedQuery("User.findByEmail").setParameter("email", email).getResultList();
		User user = null;
		for (User u : userWithEmail) {
			user = u;
		}
		return user;	
	}

}
