package resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import daos.GymDAO;
import daos.MemberDAO;
import daos.PaymentDAO;
import daos.PlanDAO;
import entities.Gym;
import entities.Member;
import entities.Payment;
import entities.Plan;

@Path("/app")
public class GymAppResource {
	
	@GET
	@Path("/members")
	@Produces("application/json")
	public List<Member> listMembersJSON() {
		MemberDAO memberDao = new MemberDAO();
		return memberDao.getAllMembers();
	}
	
	@GET
	@Path("/payments")
	@Produces("application/xml")
	public List<Payment> listPaymentsXML() {
		PaymentDAO paymentDao = new PaymentDAO();
		return paymentDao.getAllPayments();
	}
	
	@GET
	@Path("/plans")
	@Produces("application/json")
	public List<Plan> listPlansJSON() {
		PlanDAO planDao = new PlanDAO();
		return planDao.getAllPlans();
	}
	
	@GET
	@Path("/gym")
	@Produces("application/xml")
	public List<Gym> listGymXML() {
		GymDAO gymDao = new GymDAO();
		return gymDao.getAllGyms();
	}

}
