package resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
	
	// Endpoints for CRUD methods on Member objects
	@GET
	@Path("/members")
	@Produces("application/json")
	public List<Member> listMembersJSON() {
		MemberDAO memberDao = new MemberDAO();
		return memberDao.getAllMembers();
	}
	
	@GET
	@Path("/members/{memberId}")
	@Produces("application/xml")
	public Member getMemberById(@PathParam("memberId") int memberId) {
		MemberDAO memberDao = new MemberDAO();
		return memberDao.findMemberById(memberId);
	}
	
	@POST
	@Path("/createMember")
	@Consumes("application/json")
	@Produces("text/plain")
	public String addMemberToDbJSON(Member member) {
		MemberDAO memberDao = new MemberDAO();
		memberDao.persist(member);
		return "Member added to DB : " + member.getName();
	}
	
	@PUT
	@Path("/updateMemberAddress/{memberId}")
	@Produces("application/json")
	public Member updateMemberDetailsToDb(@PathParam("memberId") int memberId, @QueryParam("address") String address) {
		MemberDAO memberDao = new MemberDAO();
		Member member = memberDao.findMemberById(memberId);
		member.setAddress(address);
		return memberDao.merge(member);
	}
	
	@DELETE
	@Path("/deleteMember/{memberId}")
	@Produces("text/plain")
	public String deleteMemberFromDb(@PathParam("memberId") int memberId) {
		MemberDAO memberDao = new MemberDAO();
		Member member = memberDao.findMemberById(memberId);
		memberDao.remove(member);
		return "Member removed from DB : " + member.getName();
	}
	
	// Endpoints for CRUD methods on Payment objects
	@GET
	@Path("/payments")
	@Produces("application/json")
	public List<Payment> listPaymentsJSON() {
		PaymentDAO paymentDao = new PaymentDAO();
		return paymentDao.getAllPayments();
	}
	
	// Endpoints for CRUD methods on Plan objects
	@GET
	@Path("/plans")
	@Produces("application/json")
	public List<Plan> listPlansJSON() {
		PlanDAO planDao = new PlanDAO();
		return planDao.getAllPlans();
	}
	
	@POST
	@Path("/createPlan")
	@Produces("text/plain")
	public String addPlanToDbJSON(@QueryParam("description") String description, @QueryParam("totalCost") double totalCost) {
		Plan plan = new Plan(description, totalCost);
		PlanDAO planDao = new PlanDAO();
		planDao.persist(plan);
		return "Plan added to DB : " + plan.getDescription();
	}
	
	@PUT
	@Path("/updatePlanDescription/{planId}")
	@Produces("application/json")
	public Plan updatePlanDescriptionToDb(@PathParam("planId") int planId, @QueryParam("description") String description) {
		PlanDAO planDao = new PlanDAO();
		Plan plan = planDao.findPlanById(planId);
		plan.setDescription(description);
		return planDao.merge(plan);
	}
	
	@DELETE
	@Path("/deletePlan/{planId}")
	@Produces("text/plain")
	public String deletePlanFromDb(@PathParam("planId") int planId) {
		PlanDAO planDao = new PlanDAO();
		Plan plan = planDao.findPlanById(planId);
		planDao.remove(plan);
		return "Member removed from DB : " + plan.getDescription();
	}
	
	// Endpoints for CRUD methods on Gym objects
	@GET
	@Path("/gyms")
	@Produces("application/json")
	public List<Gym> listGymJSON() {
		GymDAO gymDao = new GymDAO();
		return gymDao.getAllGyms();
	}

}
