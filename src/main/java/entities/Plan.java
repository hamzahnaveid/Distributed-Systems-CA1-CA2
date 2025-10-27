package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;

@NamedQueries({
	@NamedQuery(name="Plan.findAll", query="select p from Plan p"), 
	@NamedQuery(name = "Plan.findByMemberId", query = "select p from Plan p where p.memberId=:id")
})

@Entity
public class Plan {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int planId;
	
	@OneToOne
	private int memberId;
	
	private String description;
	private double totalCost;
	
	public Plan() {
		
	}
	
	public Plan(int memberId, String description, double totalCost) {
		this.memberId = memberId;
		this.description = description;
		this.totalCost = totalCost;
	}
	

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}
	

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
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
	
	public void updateTotalCost(double paymentAmount) {
		this.totalCost += paymentAmount;
	}

}
