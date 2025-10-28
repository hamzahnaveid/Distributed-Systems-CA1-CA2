package services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import resources.GymAppResource;

@ApplicationPath("/resteasy-api")
public class GymAppService extends Application {
	
	private Set<Object> singletons = new HashSet<Object>();
	
	public GymAppService() {
		singletons.add(new GymAppResource());
	}
	
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
