package org.distributedsystems.resources;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.distributedsystems.daos.EmissionDAO;
import org.distributedsystems.daos.UserDAO;
import org.distributedsystems.entities.Emission;
import org.distributedsystems.entities.User;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/app")
@ApplicationScoped
public class EmissionsAppResource {
	
	@Inject
	EmissionDAO emissionDao;
	@Inject
	UserDAO userDao;
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@FormParam("email") String email, @FormParam("password") String password) {
		User user = userDao.findUserByEmail(email);
		
		if (user == null) {
			return Response.status(401).entity("User does not exist in DB under that email address. Please try again").build();
		}
		
		if (!user.getPassword().equals(password)) {
			return Response.status(401).entity("Invalid password. Please try again.").build();
		}
		
		return Response.status(Response.Status.FOUND).header("Location", "/home.html").build();
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response register(@FormParam("email") String email, @FormParam("password") String password) {	
		User user = new User(email, password);
		userDao.persist(user);
		return Response.status(Response.Status.FOUND).header("Location", "/home.html").build();
	}
    
    @GET
    @Path("/parseXml")
    public void parseXml() throws ParserConfigurationException, SAXException, IOException {
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder builder = factory.newDocumentBuilder();
    	Document document = builder.parse("/Users/hamzahnaveid/Documents/MMR_IRArticle23T1_IE_2016v2.xml");

    	
    	NodeList rows = document.getElementsByTagName("Row");
    	
    	for (int i = 0; i <rows.getLength(); i++) {
    		NodeList children = rows.item(i).getChildNodes();
    		
    		for (int j = 0; j < children.getLength(); j++) {
    			Node node = children.item(j);
    			
    			if (node.getNodeName().contains("Year") && node.getTextContent().equalsIgnoreCase("2023") && children.item(j+2).getTextContent().equalsIgnoreCase("WEM") && !children.item(j+8).getTextContent().isEmpty() && Double.parseDouble(children.item(j+8).getTextContent()) > 0) {
    				String category = children.item(j-2).getTextContent();
    				int year = Integer.parseInt(node.getTextContent());
    				String scenario = children.item(j+2).getTextContent();
    				String gasUnits = children.item(j+4).getTextContent();
    				double value = Double.parseDouble(children.item(j+8).getTextContent());
    				String description = getDescription(category);
    				
    				Emission emission = new Emission(category, null, year, scenario, gasUnits, value);
    				emissionDao.persist(emission);
    			}
    			
    		}
    	}
    }
    
    @GET
    @Path("/viewAllEmissions")
	@Produces(MediaType.TEXT_PLAIN)
    public String viewAllEmissions() {
    	String result = "";
    	List<Emission> emissions = emissionDao.getAllEmissions();
    	for (Emission e : emissions) {
    		result += e.toString();
    	}
    	return result;
    }
    
    @GET
	@Path("/viewByCategory")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
	public String register(@QueryParam("category") String category) {
    	String result = "";
    	List<Emission> emissionsByCategory = emissionDao.findEmissionsByCategory(category);
		for (Emission e : emissionsByCategory) {
			result += e.toString();
		}
		return result;
	}
    
    public String getDescription(String category) {
    	String result = "";
    	return result;
    }
    
}
