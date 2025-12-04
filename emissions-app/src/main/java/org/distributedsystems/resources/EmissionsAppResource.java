package org.distributedsystems.resources;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.distributedsystems.daos.EmissionDAO;
import org.distributedsystems.daos.UserDAO;
import org.distributedsystems.entities.Emission;
import org.distributedsystems.entities.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    @Produces(MediaType.TEXT_PLAIN)
    public String parseXml() throws ParserConfigurationException, SAXException, IOException {
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
    				
    				Emission emission = new Emission(category, description, year, scenario, gasUnits, value);
    				emissionDao.persist(emission);
    			}
    			
    		}
    	}
    	
    	return "XML file parsed to DBa";
    }
    
    @GET
    @Path("/parseJson")
    @Produces(MediaType.TEXT_PLAIN)
    public String parseJson() throws IOException {
    	
    	File file = new File("/Users/hamzahnaveid/Downloads/GreenhouseGasEmissions2025.json");
    	ObjectMapper mapper = new ObjectMapper();
    	JsonNode node = mapper.readTree(file);
    	JsonNode list = node.get("Emissions");
    	
    	for (JsonNode obj : list) {
    		String category = obj.get("Category").asText();
    		String gasUnits = obj.get("Gas Units").asText();
    		double value = obj.get("Value").asDouble();
    		String description = getDescription(category);
    		
    		if (value < 0) {
    			continue;
    		}
    		
    		Emission emission = new Emission(category, description, 2023, "WEM", gasUnits, value);
    		emissionDao.persist(emission);
    	}
    	
    	return "JSON file parsed to DB";

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
    
    @POST
   	@Path("/addEmission")
   	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
   	public String addEmission(@FormParam("category") String category, @FormParam("year") String year, @FormParam("scenario") String scenario, @FormParam("units") String gasUnits, @FormParam("value") String value) throws IOException {
    	String description = getDescription(category);
       	Emission emission = new Emission(category, description, Integer.parseInt(year), scenario, gasUnits, Double.parseDouble(value));
       	emissionDao.persist(emission);
   		return "Emission added to DB";
   	}
    
    @POST
   	@Path("/deleteByCategory")
   	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
   	public String deleteByCategory(@FormParam("category") String category) {
       	List<Emission> emissionsByCategory = emissionDao.findEmissionsByCategory(category);
       	for (Emission e : emissionsByCategory) {
       		emissionDao.remove(e);
       	}
   		return "All " + category + " emissions removed from DB";
   	}
    
    @POST
   	@Path("/updateByCategory")
   	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
   	public String updateByCategory(@FormParam("category") String category, @FormParam("description") String description) {
    	List<Emission> emissionsByCategory = emissionDao.findEmissionsByCategory(category);
    	for (Emission e : emissionsByCategory) {
       		e.setDescription(description);
       		emissionDao.merge(e);
       	}
    	return "All " + category + " emissions updated with description " + "\'" + description + "\'";
   	}
    
    @GET
    @Path("/viewAllUsers")
	@Produces(MediaType.TEXT_PLAIN)
    public String viewAllUsers() {
    	String result = "";
    	List<User> users = userDao.getAllUsers();
    	for (User u : users) {
    		result += u.toString();
    	}
    	return result;
    }
    
    @POST
   	@Path("/deleteByEmail")
   	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
   	public String deleteByEmail(@FormParam("email") String email) {
       	User user = userDao.findUserByEmail(email);
       	userDao.remove(user);
   		return "User " + user.getId() + " removed from DB";
   	}
    
    @POST
   	@Path("/updateByEmail")
   	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
   	public String updateByEmail(@FormParam("email") String email, @FormParam("password") String password) {
       	User user = userDao.findUserByEmail(email);
       	user.setPassword(password);
       	userDao.merge(user);
   		return "User " + user.getId() + " password updated";
   	}
    
    
    @GET
    @Path("/getDescription")
    @Produces(MediaType.TEXT_PLAIN)
    public String getDescription(@QueryParam("category") String category) throws IOException {
    	String result = "";
    	String url = "https://www.ipcc-nggip.iges.or.jp/EFDB/find_ef.php?ipcc_code=" + category;
    	
    	org.jsoup.nodes.Document document = Jsoup.connect(url).get();
		Elements nodes = document.select(".listCell");
		if (!nodes.select("ul > li").isEmpty()) {
			result += nodes.select("ul > li").get(1).text();
		}

    			
    	return result;
    }
   
}
