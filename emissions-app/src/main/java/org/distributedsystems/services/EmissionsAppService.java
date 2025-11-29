package org.distributedsystems.services;

import java.util.HashSet;
import java.util.Set;

import org.distributedsystems.resources.EmissionsAppResource;

import jakarta.ws.rs.core.Application;

public class EmissionsAppService extends Application {
	
private Set<Object> singletons = new HashSet<Object>();
	
	public EmissionsAppService() {
		singletons.add(new EmissionsAppResource());
	}
	
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
