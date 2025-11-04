package entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQueries({
	@NamedQuery(name="Member.findAll", query="select m from Member m"), 
	@NamedQuery(name = "Member.findById", query = "select m from Member m where m.memberId=:id")
})

@XmlRootElement
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int memberId;
	
	private String name;
	private String phoneNumber;
	private String address;
	private String fitnessGoal;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Plan plan;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Payment> payments = new ArrayList<Payment>();
	
	public Member() {
		
	}
	
	public Member(String name, String phoneNumber, String address, String fitnessGoal, Plan plan, List<Payment> payments) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.fitnessGoal = fitnessGoal;
		this.plan = plan;
		this.payments = payments;
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

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
	
	public void addPayment(Payment payment) {
		this.payments.add(payment);
	}

}
