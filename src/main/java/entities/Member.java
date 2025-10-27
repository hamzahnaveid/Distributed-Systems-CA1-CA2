package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@NamedQueries({
	@NamedQuery(name="Member.findAll", query="select m from Member m"), 
	@NamedQuery(name = "Member.findById", query = "select m from Member m where m.memberId=:id")
})

@Entity
public class Member {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int memberId;
	
	private String name;
	private String phoneNumber;
	private String address;
	private String fitnessGoal;
	
	public Member() {
		
	}
	
	public Member(String name, String phoneNumber, String address, String fitnessGoal) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.fitnessGoal = fitnessGoal;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFitnessGoal() {
		return fitnessGoal;
	}

	public void setFitnessGoal(String fitnessGoal) {
		this.fitnessGoal = fitnessGoal;
	}
	
}
