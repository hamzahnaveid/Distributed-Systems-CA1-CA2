package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
	@NamedQuery(name="Plan.findAll", query="select p from Plan p"),
	@NamedQuery(name = "Plan.findById", query = "select p from Plan p where p.planId=:id")
})

@Entity
public class Plan {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int planId;
	
	private String description;
	private double totalCost;
	
	public Plan() {
		
	}
	
	public Plan(String description, double totalCost) {
		this.description = description;
		this.totalCost = totalCost;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	

}
