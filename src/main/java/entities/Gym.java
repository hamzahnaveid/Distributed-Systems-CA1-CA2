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

@NamedQueries({
	@NamedQuery(name="Gym.findAll", query="select g from Gym g") , 
	@NamedQuery(name = "Gym.findById", query = "select g from Gym g where g.gymId=:id")
})

@Entity
public class Gym {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int gymId;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
