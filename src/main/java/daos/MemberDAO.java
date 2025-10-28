package daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Member;


public class MemberDAO {
	
	protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaPU");
	
	public void persist(Member member) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(member);
		em.getTransaction().commit();
		em.close();
	}
	
	public void remove(Member member) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(member));
		em.getTransaction().commit();
		em.close();
	}
	
	public Member merge(Member member) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Member updatedMember = em.merge(member);
		em.getTransaction().commit();
		em.close();
		return updatedMember;
	}
	
	public List<Member> getAllMembers() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Member> membersFromDb = new ArrayList<Member>();
		membersFromDb = em.createNamedQuery("Member.findAll").getResultList();
		em.getTransaction().commit();
		em.close();
		return membersFromDb;
	}
	
	public Member findMemberById(int id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Member> membersWithId = new ArrayList<Member>();
		membersWithId = em.createNamedQuery("Member.findById").setParameter("id", id).getResultList();
		em.getTransaction().commit();
		em.close();
		Member member = null;
		for (Member m : membersWithId) {
			member = m;
		}
		return member;	
	}

}
