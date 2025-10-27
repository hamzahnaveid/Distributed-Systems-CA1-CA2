package entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

@NamedQuery(name="Gym.findAll", query="select g from Gym g") 

@Entity
public class Gym {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int gymId;
	
	@OneToMany
	private List<Member> members = new ArrayList<Member>();
	
	public Gym() {
		
	}
	
	public Gym(List<Member> members) {
		this.members = members;
	}

	public int getGymId() {
		return gymId;
	}

	public void setGymId(int gymId) {
		this.gymId = gymId;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}
	
	public void addMember(Member member) {
		this.members.add(member);
	}

}
