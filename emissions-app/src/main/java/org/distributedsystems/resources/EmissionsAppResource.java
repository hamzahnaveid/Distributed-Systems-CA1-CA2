package org.distributedsystems.resources;

import java.io.IOException; 

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.distributedsystems.daos.EmissionDAO;
import org.distributedsystems.entities.Emission;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/app")
@ApplicationScoped
public class EmissionsAppResource {
	
	@Inject
	EmissionDAO emissionDao;
    
    @GET
    @Path("/parseXml")
    @Produces(MediaType.TEXT_PLAIN)
    public String parseXml() throws ParserConfigurationException, SAXException, IOException {
    	String result = "";
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
    				
    				Emission emission = new Emission(category, null, year, scenario, gasUnits, value);
    				emissionDao.persist(emission);
    			}
    			
    		}
    	}
    	return result;
    }
    
}
