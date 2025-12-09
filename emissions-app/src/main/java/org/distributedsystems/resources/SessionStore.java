package org.distributedsystems.resources;

import java.util.HashMap;

import org.distributedsystems.entities.User;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SessionStore {
	
	private HashMap<String, User> sessions = new HashMap<>();
	
	public void addSession(String token, User user) {
		sessions.put(token, user);
	}
	
	public User getUser(String token) {
		return sessions.get(token);
	}
	
	public void remove(String token) {
		sessions.remove(token);
	}

}
